#import "FlutterApi.pbobjc.h"

#import <FlutterGrpcService.h>

@class KWYFlutterEmpty;
@class KWYFlutterStringValue;

NS_ASSUME_NONNULL_BEGIN

@protocol KWYFlutterApiService <NSObject>

- (void)getFlutterVersionWithRequest:(KWYFlutterEmpty*)request handler:(void(^)(KWYFlutterStringValue*_Nullable response, NSError *_Nullable error))handler;

@end


@interface KWYFlutterApiService : FlutterGrpcService

- (GPBMessage *)createRequest:(NSString *)method;

@end

NS_ASSUME_NONNULL_END

