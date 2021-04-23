//
//  KIPlatformApiChannelServiceChannel.m
//  Runner
//
//  Created by zhengjiajun on 2021/1/25.
//

#import "KIPlatformApiChannelServiceChannel.h"
#import <Flutter/Flutter.h>

@implementation KIPlatformApiChannelServiceChannel

- (void)getPlatformVersionWithRequest:(nonnull KWYEmpty *)request handler:(nonnull void (^)(KWYStringValue * _Nullable, NSError * _Nullable))handler {
    NSString *result = [@"iOS " stringByAppendingString:[[UIDevice currentDevice] systemVersion]];
    KWYStringValue *value = [KWYStringValue message];
    value.value = result;
    handler(value,nil);
}

@end
