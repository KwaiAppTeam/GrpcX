# kidea_grpcx Plugin ReadMe
> 在膜拜了[gRPC成神之路](https://kstack.corp.kuaishou.com/tech/web/article/info/305)之后，了解了grpc的一些基础知识，对现有的kwai_grpc plugin进行了逻辑梳理，最后着手优化，开发了kidea_grpcx Plugin。Grpcx较之前有如下优势：
> 1.    统一server、client逻辑，无论是native，还是flutter作为server or client，对于业务方调用方式基本类似；
> 2.    简化通信方使用的channel，只有2个channel负责flutter和native的通信；
> 3.    重新封装Plugin核心逻辑，使服务更加稳定；

## kidea_grpcx前期准备
1.    本plugin的example路径下有grpc_tools文件夹，其中包含了必须依赖的可执行文件，请将这个文件夹复制到目标工程的根目录下；
2. 本plugin的example路径下有Makefile文件，其中定义了proto、setup等命令，请将此文件复制到目标工程的根目录下；
3. 1、2执行后，在工程根目录执行**make setup**来部署grpc需要的环境，用来生成三端代码；
4. 一切准备就绪后，在工程根目录下定义proto文件夹，用来存放xxx.proto文件，这些文件就是我们需要定义的flutter和native直接的相互访问的API；
5. 创建好xxx.proto后，执行**make proto**即可生成三端代码，自此跟grpc相关的前期准备就完毕了；
### 示例
> 为了方便大家理解，这里文件或者rpc server的命名都是跟扮演的角色有关联的，大家可以通过字面意思就晓得其在通信过程中扮演的角色。在日常开发过程中，也建议大家这么命名，方便理解；
-    native扮演server，flutter扮演client。如下是platform_api.proto（部分），定义了一个方法和2个参数
```
package platform;

message Empty {
}

message StringValue {
   string value = 1;
}

service PlatformApiChannel {
    rpc getPlatformVersion (Empty) returns (StringValue) {
    }
}
```
- flutter扮演server，native扮演client。如下是flutter_api.proto（部分），定义了一个方法，参数是improt上例的
```
package flutter;

import "platform_api.proto";

service FlutterApi {
    rpc getFlutterVersion (platform.Empty) returns (platform.StringValue) {
    }
}
```
以后的步骤都会和这两个文件定义的接口有关系。
## 服务部署
- native作为server，通过继承rpc生成的代码来完成服务的实现
```
public class PlatformApiServer extends PlatformApiChannelGrpc.PlatformApiChannelImplBase {

    @Override
    public void getPlatformVersion(Empty request, io.grpc.stub.StreamObserver<StringValue> responseObserver) {
        responseObserver.onNext(StringValue.getDefaultInstance().toBuilder().setValue("1.8.0").build());
        responseObserver.onCompleted();
    }
}
```
- 服务注册,在合适的时机执行如下代码（时机越早越好）
```
 KideaGrpcxPlugin.addService(PlatformApiChannelGrpc.SERVICE_NAME, new PlatformApiServer());
```
- - - - - 
- flutter作为server，通过继承rpc生成的代码来完成服务的实现。注意**需要增加with GeneratedServiceHelper**来辅助获取serviceName
```
/// flutter 侧提供的服务
/// 需要实现GeneratedServiceHelper，用于服务名称的获取
/// 服务名称是proto 中定义的package.serviceName
class FlutterApi extends FlutterApiServiceBase with GeneratedServiceHelper {
  @override
  String loadServiceName() {
    return "flutter.FlutterApi";
  }

  @override
  Future<StringValue> getFlutterVersion(
      ServerContext ctx, Empty request) async {
    StringValue stringValue = StringValue();
    stringValue.value = "1.22.5";
    return stringValue;
  }
}
```
- 服务注册
```
///创建server实例，并注册,然后待native调用
    FlutterApi api = FlutterApi();
    KideaGrpcx.addService(api.loadServiceName(), api);
```

上述小节介绍了服务部署的内容，可以看出，无论是哪一端作为服务提供者，业务方调用的逻辑基本一致。
## 服务调用
- flutter作为client发起调用
```
String _platformVersion = 'Unknown';
PlatformApiChannelApi platformApiChannelApi =
        PlatformApiChannelApi(KideaGrpcx.createRpcClient("platform"));
// Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      StringValue value =
          await platformApiChannelApi.getPlatformVersion(null, Empty());
      platformVersion = value.value;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }
```
在创建PlatformApiChannelApi时传入了**platform**，这个和xxx.proto中的**package**保持一致。
- - - - - 
- native作为client发起访问
```
FlutterApiGrpc.FlutterApiStub flutterApiStub = FlutterApiGrpc.newStub(KideaGrpcxPlugin.loadClientHandler());
                Empty.Builder builder = Empty.newBuilder();

                flutterApiStub.getFlutterVersion(builder.build(), new StreamObserver<StringValue>() {
                    @Override
                    public void onNext(StringValue value) {
                        Log.e("hello", value.getValue());
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.e("hello", "错误了");
                    }

                    @Override
                    public void onCompleted() {
                        Log.e("hello", "结束了");
                    }
                });
```
在client访问方面，flutter作为client和native作为client还是有些许区别，这是grpc生成的代码导致的，不过实现成本还是很低。这里没有传入类似上例中**platform**的字段，是因为grpc生成方法时已经包含了该参数，本例为**flutter**。
## 总结
作为一个程序猿，我们总想着能让机器干的事情就放手让机器去干。我想kwai_grpc设计的初衷也是想在开发channel时避免没必要的code，只写核心代码。类似的库还有ks_channel_manager，谷歌官方的pigeon（我改进了下批量生成，详见[kidea_pigeon](https://git.corp.kuaishou.com/kidea/kidea_pigeon)）。每个库都有自己独特的一方面，目前我们使用最多的还是grpc，因为其生成的中间代码更加符合server/client模式。