import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:kidea_grpcx/kidea_grpcx.dart';
import 'package:kidea_grpcx/rpc_flutter_server.dart';
import 'package:kidea_grpcx_example/src/channel/proto/flutter_api.pbserver.dart';
import 'package:kidea_grpcx_example/src/channel/proto/platform_api.pb.dart';
import 'package:protobuf/protobuf.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

/// flutter 侧提供的服务
/// 需要实现GeneratedServiceHelper，用于服务名称的获取
/// 服务名称是proto 中定义的package.serviceName
class FlutterApi extends FlutterApiServiceBase with GeneratedServiceHelper {
  @override
  String loadServiceName() {
    return "flutter.FlutterApi";
  }

  @override
  Future<FlutterStringValue> getFlutterVersion(
      ServerContext ctx, FlutterEmpty request) async {
    FlutterStringValue stringValue = FlutterStringValue();
    stringValue.value = "1.22.5";
    return stringValue;
  }
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  PlatformApiChannelApi platformApiChannelApi;

  @override
  void initState() {
    super.initState();
    platformApiChannelApi =
        PlatformApiChannelApi(KideaGrpcx.createRpcClient("platform"));
    initPlatformState();

    ///创建server实例，并注册,然后待native调用
    FlutterApi api = FlutterApi();
    KideaGrpcx.addService(api.loadServiceName(), api);
  }

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

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on: $_platformVersion\n'),
        ),
      ),
    );
  }
}
