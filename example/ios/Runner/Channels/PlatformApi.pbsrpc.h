#import "PlatformApi.pbobjc.h"

#import <FlutterGrpcService.h>

@class KWYEmpty;
@class KWYStringValue;

NS_ASSUME_NONNULL_BEGIN

@protocol KWYPlatformApiChannelService <NSObject>

- (void)getPlatformVersionWithRequest:(KWYEmpty*)request handler:(void(^)(KWYStringValue*_Nullable response, NSError *_Nullable error))handler;

@end


@interface KWYPlatformApiChannelService : FlutterGrpcService

- (GPBMessage *)createRequest:(NSString *)method;

@end

NS_ASSUME_NONNULL_END

