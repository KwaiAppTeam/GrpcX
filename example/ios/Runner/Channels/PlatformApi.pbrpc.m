#if !defined(GPB_GRPC_PROTOCOL_ONLY) || !GPB_GRPC_PROTOCOL_ONLY
#import "PlatformApi.pbrpc.h"
#import "PlatformApi.pbobjc.h"
#import <ProtoRPC/ProtoRPCLegacy.h>
#import <RxLibrary/GRXWriter+Immediate.h>


@implementation KWYPlatformApiChannel

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wobjc-designated-initializers"

// Designated initializer
- (instancetype)initWithHost:(NSString *)host callOptions:(GRPCCallOptions *_Nullable)callOptions {
  return [super initWithHost:host
                 packageName:@"platform"
                 serviceName:@"PlatformApiChannel"
                 callOptions:callOptions];
}

- (instancetype)initWithHost:(NSString *)host {
  return [super initWithHost:host
                 packageName:@"platform"
                 serviceName:@"PlatformApiChannel"];
}

#pragma clang diagnostic pop

// Override superclass initializer to disallow different package and service names.
- (instancetype)initWithHost:(NSString *)host
                 packageName:(NSString *)packageName
                 serviceName:(NSString *)serviceName {
  return [self initWithHost:host];
}

- (instancetype)initWithHost:(NSString *)host
                 packageName:(NSString *)packageName
                 serviceName:(NSString *)serviceName
                 callOptions:(GRPCCallOptions *)callOptions {
  return [self initWithHost:host callOptions:callOptions];
}

#pragma mark - Class Methods

+ (instancetype)serviceWithHost:(NSString *)host {
  return [[self alloc] initWithHost:host];
}

+ (instancetype)serviceWithHost:(NSString *)host callOptions:(GRPCCallOptions *_Nullable)callOptions {
  return [[self alloc] initWithHost:host callOptions:callOptions];
}

#pragma mark - Method Implementations

#pragma mark getPlatformVersion(Empty) returns (StringValue)

- (void)getPlatformVersionWithRequest:(KWYEmpty *)request handler:(void(^)(KWYStringValue *_Nullable response, NSError *_Nullable error))handler{
  [[self RPCTogetPlatformVersionWithRequest:request handler:handler] start];
}
// Returns a not-yet-started RPC object.
- (GRPCProtoCall *)RPCTogetPlatformVersionWithRequest:(KWYEmpty *)request handler:(void(^)(KWYStringValue *_Nullable response, NSError *_Nullable error))handler{
  return [self RPCToMethod:@"getPlatformVersion"
            requestsWriter:[GRXWriter writerWithValue:request]
             responseClass:[KWYStringValue class]
        responsesWriteable:[GRXWriteable writeableWithSingleHandler:handler]];
}
- (GRPCUnaryProtoCall *)getPlatformVersionWithMessage:(KWYEmpty *)message responseHandler:(id<GRPCProtoResponseHandler>)handler callOptions:(GRPCCallOptions *_Nullable)callOptions {
  return [self RPCToMethod:@"getPlatformVersion"
                   message:message
           responseHandler:handler
               callOptions:callOptions
             responseClass:[KWYStringValue class]];
}

@end
#endif
