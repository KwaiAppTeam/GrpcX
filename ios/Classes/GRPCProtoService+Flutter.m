//
//  GRPCProtoService+Flutter.m
//  kidea_grpcx
//
//  Created by zhengjiajun on 2021/1/25.
//
//  Copyright 2021 Kwai,Inc. All rights reserved.
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//  http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.

#import "GRPCProtoService+Flutter.h"
#import <RxLibrary/GRXWriter+Transformations.h>
#import <objc/runtime.h>
#import <Flutter/FlutterCodecs.h>
#import "GPBMessage.h"
#import "KideaGrpcxPlugin.h"

@interface GRPCProtoService ()

@property (nonatomic, copy) FlutterEventSink sink;

@end

@implementation GRPCProtoService (Flutter)

+ (void)load {
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        psf_ClassMethodSwizzle([self class], @selector(RPCToMethod:requestsWriter:responseClass:responsesWriteable:), @selector(psf_RPCToMethod:requestsWriter:responseClass:responsesWriteable:));
    });
}

- (GRPCProtoCall *)psf_RPCToMethod:(NSString *)method
                requestsWriter:(GRXWriter *)requestsWriter
                 responseClass:(Class)responseClass
            responsesWriteable:(id<GRXWriteable>)responsesWriteable {
    // 后续完善listen处理
//    Class class = [[KwaiGrpcNTFManager sharedInstance] classWithName:self.name];
//    if (!class){
//        return nil;
//    }xx
    
    if (!self.messenger) {
        return nil;
    }

    NSData *requestData = [[requestsWriter valueForKey:@"_value"] data];
    FlutterMethodCall *call = [FlutterMethodCall methodCallWithMethodName:[NSString stringWithFormat:@"%@/%@", self.name, method] arguments:requestData];
    NSData *data = [[FlutterStandardMethodCodec sharedInstance] encodeMethodCall:call];
    [self.messenger sendOnChannel:kGRPCClientChannel message:[[FlutterStandardMethodCodec sharedInstance] encodeSuccessEnvelope:data] binaryReply:^(NSData * _Nullable reply) {
        GRXValueHandler _valueHandler = [(NSObject *)responsesWriteable valueForKey:@"_valueHandler"];
        GRXCompletionHandler _completionHandler = [(NSObject *)responsesWriteable valueForKey:@"_completionHandler"];
        if (_valueHandler) {
            // 解码 Data
            FlutterStandardTypedData *call = [[FlutterStandardMethodCodec sharedInstance] decodeEnvelope:reply];
            NSError *error = nil;
            // 还原回调参数类
            id parsed = [responseClass parseFromData:call.data error:&error];
            if (error == nil) {
                _valueHandler(parsed);
            } else {
                if (_completionHandler) {
                    _completionHandler(error);
                }
            }
        }
    }];
    return nil;
}

- (void)setMessenger:(NSObject<FlutterBinaryMessenger> *)messenger {
    __weak NSObject<FlutterBinaryMessenger> *weakObject = messenger;
    id (^block)(void) = ^{ return weakObject; };
    objc_setAssociatedObject(self, "flutter_messenger", block, OBJC_ASSOCIATION_COPY);
}

- (NSObject<FlutterBinaryMessenger> *)messenger {
    id (^block)(void) = objc_getAssociatedObject(self, "flutter_messenger");
    return block ? block() : nil;
}

- (NSString *)name {
    NSString *packageName = [self valueForKey:@"_packageName"];
    NSString *serviceName = [self valueForKey:@"_serviceName"];
    if (packageName && serviceName) {
        return [NSString stringWithFormat:@"%@.%@", packageName, serviceName];
    }
    return nil;
}

#pragma mark - private method

BOOL psf_ClassMethodSwizzle(Class aClass, SEL originalSelector, SEL swizzleSelector) {
    Method originalMethod = class_getInstanceMethod(aClass, originalSelector);
    Method swizzleMethod = class_getInstanceMethod(aClass, swizzleSelector);
    BOOL didAddMethod =
    class_addMethod(aClass,
                    originalSelector,
                    method_getImplementation(swizzleMethod),
                    method_getTypeEncoding(swizzleMethod));
    if (didAddMethod) {
        class_replaceMethod(aClass,
                            swizzleSelector,
                            method_getImplementation(originalMethod),
                            method_getTypeEncoding(originalMethod));
    } else {
        method_exchangeImplementations(originalMethod, swizzleMethod);
    }
    return YES;
}
@end
