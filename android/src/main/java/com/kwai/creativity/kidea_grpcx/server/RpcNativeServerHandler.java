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
package com.kwai.creativity.kidea_grpcx.server;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.protobuf.GeneratedMessageLite;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.grpc.BindableService;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerMethodDefinition;
import io.grpc.ServerServiceDefinition;
import io.grpc.Status;

/**
 * 响应flutter侧的方法调用
 */
public class RpcNativeServerHandler implements MethodChannel.MethodCallHandler {
    private static String serverChannelName = "Flutter2Native";
    private volatile static RpcNativeServerHandler serverHandlerInstance;
    private final MethodChannel channel;
    private final HashMap<String, ServerServiceDefinition> services = new HashMap<>();

    private RpcNativeServerHandler(@NonNull BinaryMessenger binaryMessenger, @NonNull String channelName) {
        channel = new MethodChannel(binaryMessenger, channelName);
        channel.setMethodCallHandler(this);
    }

    public static RpcNativeServerHandler newInstance(@NonNull BinaryMessenger binaryMessenger, @Nullable String channelName) {
        if (!TextUtils.isEmpty(channelName)) {
            serverChannelName = channelName;
        }
        if (serverHandlerInstance == null) {
            synchronized (RpcNativeServerHandler.class) {
                if (serverHandlerInstance == null) {
                    serverHandlerInstance = new RpcNativeServerHandler(binaryMessenger, serverChannelName);
                }
            }
        }

        return serverHandlerInstance;
    }

    public void addService(String serviceName, BindableService bindableService) {
        services.put(serviceName, bindableService.bindService());
    }

    public void onDetachedFromEngine() {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        String methodName = call.method;
        String[] fragments = methodName.split("/");
        if (fragments.length != 2) {
            result.error("400", "Illegal parameters, method name is" + methodName, "");
            return;
        }

        String serviceName = fragments[0];
        ServerServiceDefinition serviceDefinition = services.get(serviceName);
        if (serviceDefinition == null) {
            result.error("403", "serviceDefinition not find, method name is" + methodName, "");
            return;
        }

        ServerMethodDefinition<?, ?> methodDefinition = serviceDefinition.getMethod(methodName);
        if (methodDefinition == null) {
            result.error("404", "methodDefinition not find, method name is" + methodName, "");
            return;
        }

        MethodDescriptor<?, ?> methodDescriptor = methodDefinition.getMethodDescriptor();
        ServerCallHandler<?, ?> serverCallHandler = methodDefinition.getServerCallHandler();
        ServerCall.Listener<Object> serverListener = serverCallHandler.startCall(new NativeServerCall(methodDescriptor, result), null);
        serverListener.onReady();
        Object request = methodDescriptor.parseRequest(new ByteArrayInputStream((byte[]) call.arguments));
        serverListener.onMessage(request);
        serverListener.onHalfClose();//server开始响应client的请求
        serverListener.onComplete();
    }

    private static class NativeServerCall<ReqT, RespT> extends ServerCall<ReqT, RespT> {

        private final MethodDescriptor<ReqT, RespT> methodDescriptor;
        private final MethodChannel.Result result;
        private final AtomicBoolean done = new AtomicBoolean(false);

        public NativeServerCall(MethodDescriptor<ReqT, RespT> methodDescriptor, MethodChannel.Result result) {
            this.methodDescriptor = methodDescriptor;
            this.result = result;
        }

        @Override
        public void request(int numMessages) {
            Log.e(serverChannelName, "receive " + numMessages + "times request");
        }

        @Override
        public void sendHeaders(Metadata headers) {
            //向client发送header,此处为null
            Log.e(serverChannelName, "send headers to flutter");
        }

        @Override
        public void sendMessage(RespT message) {
            if (done.getAndSet(true)) {
                Log.e(serverChannelName, "already send result to flutter: method=" + methodDescriptor.getFullMethodName());
                return;
            }

            //通过channel将处理结果发送给flutter,实现结果回传
            result.success(((GeneratedMessageLite<?, ?>) message).toByteArray());
        }

        @Override
        public void close(Status status, Metadata trailers) {
            if (status.isOk()) {
                return;
            }
            String cause = "";
            if (status.getCode() == Status.Code.UNKNOWN) {
                if (status.getCause() == null || TextUtils.isEmpty(status.getCause().toString())) {
                    cause = "UNKNOWN";
                } else {
                    cause = status.getCause().toString();
                }
            }

            if (done.getAndSet(true)) {
                Log.e(serverChannelName, "already send result to flutter: method=" + methodDescriptor.getFullMethodName() + ". error_code=" + status.getCode() + " cause=" + cause);
                return;
            }

            result.error(status.getCode().toString(), status.getDescription(), cause);
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public MethodDescriptor<ReqT, RespT> getMethodDescriptor() {
            return this.methodDescriptor;
        }
    }
}






