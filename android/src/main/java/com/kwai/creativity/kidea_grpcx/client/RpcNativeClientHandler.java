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
package com.kwai.creativity.kidea_grpcx.client;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.protobuf.GeneratedMessageLite;

import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;

import javax.annotation.Nullable;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.StandardMethodCodec;
import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.ClientCall;
import io.grpc.Metadata;
import io.grpc.MethodDescriptor;
import io.grpc.Status;

/**
 * 承载native侧向flutter发起请求
 */
public class RpcNativeClientHandler extends Channel {
    private volatile static RpcNativeClientHandler clientHandlerInstance;
    private static String clientChannelName = "Native2Flutter";
    private final BinaryMessenger binaryMessenger;

    private RpcNativeClientHandler(@NonNull BinaryMessenger binaryMessenger) {
        this.binaryMessenger = binaryMessenger;
    }

    public static RpcNativeClientHandler newInstance(@NonNull BinaryMessenger binaryMessenger, @Nullable String channelName) {
        if (!TextUtils.isEmpty(channelName)) {
            clientChannelName = channelName;
        }
        if (clientHandlerInstance == null) {
            synchronized (RpcNativeClientHandler.class) {
                if (clientHandlerInstance == null) {
                    clientHandlerInstance = new RpcNativeClientHandler(binaryMessenger);
                }
            }
        }

        return clientHandlerInstance;
    }

    @Override
    public <RequestT, ResponseT> ClientCall<RequestT, ResponseT> newCall(MethodDescriptor<RequestT, ResponseT> methodDescriptor, CallOptions callOptions) {
        return new RpcNativeClient<RequestT, ResponseT>(binaryMessenger, methodDescriptor);
    }

    @Override
    public String authority() {
        return "";
    }

    private static class RpcNativeClient<RequestT, ResponseT> extends ClientCall<RequestT, ResponseT> {
        private final StandardMethodCodec codec = StandardMethodCodec.INSTANCE;
        private final BinaryMessenger binaryMessenger;
        private final MethodDescriptor<RequestT, ResponseT> methodDescriptor;
        private Listener<ResponseT> responseListener = null;

        public RpcNativeClient(BinaryMessenger binaryMessenger, MethodDescriptor<RequestT, ResponseT> methodDescriptor) {
            this.binaryMessenger = binaryMessenger;
            this.methodDescriptor = methodDescriptor;
        }

        @Override
        public void start(Listener<ResponseT> responseListener, Metadata headers) {
            this.responseListener = responseListener;
            if (this.responseListener != null) {
                responseListener.onReady();
            }
        }

        @Override
        public void request(int numMessages) {
            Log.d(clientChannelName, "RpcNativeClient request");
        }

        @Override
        public void cancel(@Nullable String message, @Nullable Throwable cause) {
            Log.d(clientChannelName, "RpcNativeClient cancel");
            if (responseListener != null) {
                responseListener.onClose(Status.INVALID_ARGUMENT, null);
            }
        }

        @Override
        public void halfClose() {
            Log.d(clientChannelName, "RpcNativeClient halfClose");
        }

        /**
         * GRPC流程中client发起请求的方法
         */
        @Override
        public void sendMessage(RequestT message) {
            if (methodDescriptor == null) {
                Log.e(clientChannelName, "methodDescriptor is null");
                if (responseListener != null) {
                    responseListener.onClose(Status.INVALID_ARGUMENT, null);
                }
                return;
            }

            if (binaryMessenger == null) {
                Log.e(clientChannelName, "binaryMessenger is null");
                if (responseListener != null) {
                    responseListener.onClose(Status.INVALID_ARGUMENT, null);
                }
                return;
            }

            NativeClientMessageCenter clientMessageHandler = NativeClientMessageCenter.newInstance(binaryMessenger, clientChannelName);
            final byte[] requestData = ((GeneratedMessageLite) message).toByteArray();
            ByteBuffer buffer = codec.encodeMethodCall(new MethodCall(methodDescriptor.getFullMethodName(), requestData));
            buffer.position(0);
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            clientMessageHandler.sendMessage(bytes, new ResultHandleCallback() {
                @Override
                public void handleResult(byte[] result) {
                    if (responseListener != null) {
                        if (result != null) {
                            responseListener.onMessage(methodDescriptor.getResponseMarshaller().parse(new ByteArrayInputStream(result)));
                        }
                        responseListener.onClose(Status.OK, null);
                    }
                }
            });
        }
    }
}