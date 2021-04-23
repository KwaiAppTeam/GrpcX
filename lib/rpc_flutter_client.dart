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
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:protobuf/protobuf.dart';

/// 用于flutter作为client向native发起方法调用
class RpcFlutterClient extends RpcClient {
  final MethodChannel _channel = const MethodChannel('Flutter2Native');
  final String protoPackageName;

  RpcFlutterClient(this.protoPackageName);

  @override
  Future<T> invoke<T extends GeneratedMessage>(
      ClientContext ctx,
      String serviceName,
      String methodName,
      GeneratedMessage request,
      T emptyResponse) {
    return _channel
        .invokeMethod("$protoPackageName.$serviceName/$methodName",
            request.writeToBuffer())
        .then((response) {
      emptyResponse.mergeFromBuffer(response);
      return emptyResponse;
    });
  }
}
