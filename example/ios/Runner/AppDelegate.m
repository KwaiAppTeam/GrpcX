#import "AppDelegate.h"
#import "GeneratedPluginRegistrant.h"
#import <kidea_grpcx/KideaGrpcxPlugin.h>
#import "GRPCProtoService+Flutter.h"
#import "KIPlatformApiChannelServiceChannel.h"
#import "FlutterApi.pbrpc.h"

@implementation AppDelegate

- (BOOL)application:(UIApplication *)application
    didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    NSObject<FlutterPluginRegistrar>* obj = [self registrarForPlugin:@"KwaiGrpcPlugin"];
    [KideaGrpcxPlugin highPriorityRegisterWithRegistrar:obj];
//    [KideaGrpcxClientHandler sharedInstance]
  [GeneratedPluginRegistrant registerWithRegistry:self];
    
    
    KIPlatformApiChannelServiceChannel *platformChannel = KIPlatformApiChannelServiceChannel.new;
    [KideaGrpcxPlugin addService:platformChannel];
  // Override point for customization after application launch.
    
    dispatch_after(dispatch_time(DISPATCH_TIME_NOW, (int64_t)(2.0 * NSEC_PER_SEC)), dispatch_get_main_queue(), ^{
        
        KWYFlutterApi *api = [[KWYFlutterApi alloc] initWithHost:@"kidea"];
        api.messenger = obj.messenger;
        [api getFlutterVersionWithRequest:[KWYFlutterEmpty message] handler:^(KWYFlutterStringValue * _Nullable response, NSError * _Nullable error) {
            NSLog(@"flutter version %@ ",response.value);
        }];
        
    });
    
  return [super application:application didFinishLaunchingWithOptions:launchOptions];
}

@end
