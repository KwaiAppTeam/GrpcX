//
//  KideaGrpcxServerHandler.m
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

#import "KideaGrpcxServerHandler.h"
#import <ProtoRPC/ProtoService.h>
#import <objc/runtime.h>
#import <CocoaLumberjack/CocoaLumberjack.h>
#import "GPBMessage.h"
#import "FlutterGrpcService.h"
#import "KideaGrpcxPlugin.h"

static const int ddLogLevel = DDLogLevelVerbose;

@interface KideaGrpcxServerHandler ()

@property (nonatomic, copy) FlutterMethodCallHandler methodCallHandler;
@property (nonatomic, strong) NSMutableDictionary *rpcServices;

@end

@implementation KideaGrpcxServerHandler

+ (instancetype)sharedInstance {
    static KideaGrpcxServerHandler *kObj = nil;
    static dispatch_once_t  onceToken;

    dispatch_once(&onceToken, ^{
        kObj = [[self alloc] init];
    });
    return kObj;
}

- (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar{
    FlutterMethodChannel* channel = [FlutterMethodChannel
                                     methodChannelWithName:kGRPCServerChannel
          binaryMessenger:[registrar messenger]];
    [channel setMethodCallHandler:_methodCallHandler];
}

- (instancetype)init {
    if (self = [super init]) {
        _rpcServices = [[NSMutableDictionary alloc] init];
        
        __weak typeof(self) ws = self;
        _methodCallHandler = ^(FlutterMethodCall* call, FlutterResult result) {
            NSString *method = call.method;
            if ([call.method isEqualToString:@"listen"]){
                // 后续完善
            }
            DDLogInfo(@"flutter method call: %@", method);
            NSArray *compoments = [method componentsSeparatedByString:@"/"];
            if (compoments.count != 2) {
                NSString *message = [NSString stringWithFormat:@"Illegal parameters, method name is %@",method];
                if (result) {
                    FlutterError *error = [FlutterError errorWithCode:@"400" message:message details:@""];
                    result(error);
                }
                DDLogError(@"%@",message);
                return;
            }
            
            __strong typeof(ws) ss = ws;
            NSObject *service = [ss.rpcServices objectForKey:compoments[0]];
            if (!service) {
                NSString *message = [NSString stringWithFormat:@"serviceDefinition not find, method name is %@",method];
                if (result) {
                    FlutterError *error = [FlutterError errorWithCode:@"403" message:message details:@""];
                    result(error);
                }
                DDLogError(@"%@",message);
                return;
            }
            
            NSString *methodName = compoments[1];
            SEL sel = NSSelectorFromString([NSString stringWithFormat:@"%@WithRequest:handler:", methodName]);
            SEL requestSel = NSSelectorFromString(@"createRequest:");
            if (![service respondsToSelector:sel] || ![service respondsToSelector:requestSel]) {
                NSString *message = [NSString stringWithFormat:@"methodDefinition not find, method name is %@",method];
                if (result) {
                    FlutterError *error = [FlutterError errorWithCode:@"404" message:message details:@""];
                    result(error);
                }
                DDLogError(@"%@",message);
                return;
            }
            
            DDLogInfo(@"flutter method call excuting");
            NSMethodSignature *requestSig = [service methodSignatureForSelector:requestSel];
            NSInvocation *requestInv = [NSInvocation invocationWithMethodSignature:requestSig];
            requestInv.target = service;
            requestInv.selector = requestSel;
            [requestInv setArgument:&methodName atIndex:2];
            [requestInv invoke];
            void *ret;
            [requestInv getReturnValue:&ret];
            GPBMessage *retObj = (__bridge GPBMessage *)(ret);
            if (ret && [call.arguments isKindOfClass:[FlutterStandardTypedData class]]) {
                FlutterStandardTypedData *requestData = (FlutterStandardTypedData *)call.arguments;
                [retObj mergeFromData:requestData.data extensionRegistry:nil];
                
                void(^handle)(id response, NSError * error) = ^(id response, NSError * error) {
                    if (error) {
                        DDLogError(@"flutter method call excute error: %@", error);
                        NSString *code = @(error.code).stringValue;
                        FlutterError *flutterError = [FlutterError errorWithCode:code message:[NSString stringWithFormat:@"%@",error] details:@""];
                        result(flutterError);
                    } else {
                        DDLogInfo(@"flutter method call excute done");
                        result([response data]);
                    }
                };
                NSMethodSignature *sig = [service methodSignatureForSelector:sel];
                NSInvocation *inv = [NSInvocation invocationWithMethodSignature:sig];
                inv.target = service;
                inv.selector = sel;
                [inv setArgument:&retObj atIndex:2];
                [inv setArgument:&handle atIndex:3];
                [inv invoke];
            } else {
                NSString *message = [NSString stringWithFormat:@"serviceDefinition not find, method name is %@",method];
                if (result) {
                    FlutterError *error = [FlutterError errorWithCode:@"403" message:message details:@""];
                    result(error);
                }
                DDLogError(@"%@",message);
            }
        };
    }
    return self;
}

- (void)addService:(FlutterGrpcService *)service {
    if (!service) {
        return;
    }
    [self.rpcServices setObject:service forKey:service.serviceName];
}


@end
