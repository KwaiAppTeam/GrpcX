#if !defined(GPB_GRPC_PROTOCOL_ONLY) || !GPB_GRPC_PROTOCOL_ONLY
#import "FlutterApi.pbrpc.h"
#import "FlutterApi.pbobjc.h"
#import <ProtoRPC/ProtoRPCLegacy.h>
#import <RxLibrary/GRXWriter+Immediate.h>


@implementation KWYFlutterApi

#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wobjc-designated-initializers"

// Designated initializer
- (instancetype)initWithHost:(NSString *)host callOptions:(GRPCCallOptions *_Nullable)callOptions {
  return [super initWithHost:host
                 packageName:@"flutter"
                 serviceName:@"FlutterApi"
                 callOptions:callOptions];
}

- (instancetype)initWithHost:(NSString *)host {
  return [super initWithHost:host
                 packageName:@"flutter"
                 serviceName:@"FlutterApi"];
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

#pragma mark getFlutterVersion(FlutterEmpty) returns (FlutterStringValue)

- (void)getFlutterVersionWithRequest:(KWYFlutterEmpty *)request handler:(void(^)(KWYFlutterStringValue *_Nullable response, NSError *_Nullable error))handler{
  [[self RPCTogetFlutterVersionWithRequest:request handler:handler] start];
}
// Returns a not-yet-started RPC object.
- (GRPCProtoCall *)RPCTogetFlutterVersionWithRequest:(KWYFlutterEmpty *)request handler:(void(^)(KWYFlutterStringValue *_Nullable response, NSError *_Nullable error))handler{
  return [self RPCToMethod:@"getFlutterVersion"
            requestsWriter:[GRXWriter writerWithValue:request]
             responseClass:[KWYFlutterStringValue class]
        responsesWriteable:[GRXWriteable writeableWithSingleHandler:handler]];
}
- (GRPCUnaryProtoCall *)getFlutterVersionWithMessage:(KWYFlutterEmpty *)message responseHandler:(id<GRPCProtoResponseHandler>)handler callOptions:(GRPCCallOptions *_Nullable)callOptions {
  return [self RPCToMethod:@"getFlutterVersion"
                   message:message
           responseHandler:handler
               callOptions:callOptions
             responseClass:[KWYFlutterStringValue class]];
}

@end
#endif
