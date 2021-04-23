// Copyright 2021 Kwai,Inc. All rights reserved.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
import 'dart:typed_data';

import 'package:flutter/services.dart';
import 'package:protobuf/protobuf.dart';

mixin GeneratedServiceHelper {
  String loadServiceName();
}

class RpcFlutterServer {
  static RpcFlutterServer _instance;

  static RpcFlutterServer get instance => getInstance();

  factory RpcFlutterServer() => getInstance();

  static RpcFlutterServer getInstance() {
    _instance ??= RpcFlutterServer._singleton();
    return _instance;
  }

  final Map<String, GeneratedService> _services = {};

  RpcFlutterServer._singleton() {
    ServicesBinding.instance.defaultBinaryMessenger
        .setMessageHandler("Native2Flutter", (message) async {
      if (message == null) {
        return null;
      }
      try {
        var codec = StandardMethodCodec();
        final unit8List = codec.decodeEnvelope(message) as Uint8List;
        final byteData = new ByteData.view(
            unit8List.buffer, unit8List.offsetInBytes, unit8List.length);
        final clientCall = codec.decodeMethodCall(byteData);
        final fragments = clientCall.method.split("/");
        if (fragments.length != 2) {
          throw Exception("invalid method format: ${clientCall.method}");
        }
        String serviceName = fragments[0];
        String methodName = fragments[1];
        GeneratedService service = _loadService(serviceName);
        if (service != null) {
          final request = service.createRequest(methodName);
          request.mergeFromBuffer(clientCall.arguments);
          return service.handleCall(null, methodName, request)?.then((value) {
            ///将flutter处理结果返回给native
            return codec.encodeSuccessEnvelope(value?.writeToBuffer());
          });
        }
      } on PlatformException catch (e) {
        print("Native2Flutter: ${e.message} error");
      } on Exception catch (e1) {
        print("Native2Flutter: exception");
      }
      return null;
    });
  }

  void addService(String serviceName, GeneratedService service) {
    if (_services.containsKey(serviceName)) {
      print("service exist");
      return;
    }
    _services[serviceName] = service;
  }

  GeneratedService _loadService(String serviceName) {
    return _services[serviceName];
  }
}
