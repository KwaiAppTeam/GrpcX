#import "PlatformApi.pbsrpc.h"

@implementation KWYPlatformApiChannelService

- (NSString *)serviceName {
    return @"platform.PlatformApiChannel";
}

- (GPBMessage *)createRequest:(NSString *)method {
    if ([method isEqualToString:@"getPlatformVersion"]) {
        return [KWYEmpty message];
    }
    return nil;
}

@end
