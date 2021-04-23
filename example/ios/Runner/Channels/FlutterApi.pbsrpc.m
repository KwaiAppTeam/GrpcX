#import "FlutterApi.pbsrpc.h"

@implementation KWYFlutterApiService

- (NSString *)serviceName {
    return @"flutter.FlutterApi";
}

- (GPBMessage *)createRequest:(NSString *)method {
    if ([method isEqualToString:@"getFlutterVersion"]) {
        return [KWYFlutterEmpty message];
    }
    return nil;
}

@end
