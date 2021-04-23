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
package com.kwai.creativity.kidea_grpcx;

import androidx.annotation.NonNull;

import com.kwai.creativity.kidea_grpcx.client.RpcNativeClientHandler;
import com.kwai.creativity.kidea_grpcx.server.RpcNativeServerHandler;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import io.grpc.BindableService;

/**
 * KideaGrpcxPlugin
 */
public class KideaGrpcxPlugin implements FlutterPlugin {
    /// The MethodChannel that will the communication between Flutter and native Android
    ///
    /// This local reference serves to register the plugin with the Flutter Engine and unregister it
    /// when the Flutter Engine is detached from the Activity
    private static RpcNativeServerHandler serverHandler;
    private static RpcNativeClientHandler clientHandler;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        serverHandler = RpcNativeServerHandler.newInstance(flutterPluginBinding.getBinaryMessenger(), "Flutter2Native");
        clientHandler = RpcNativeClientHandler.newInstance(flutterPluginBinding.getBinaryMessenger(), "Native2Flutter");
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        serverHandler.onDetachedFromEngine();
    }

    public static void addService(String serviceName, BindableService service) {
        if (serverHandler != null) {
            serverHandler.addService(serviceName, service);
        }
    }

    public static RpcNativeClientHandler loadClientHandler() {
        return clientHandler;
    }
}
