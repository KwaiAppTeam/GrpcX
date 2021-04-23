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

import android.os.Handler;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;

import java.nio.ByteBuffer;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.StandardMethodCodec;

/**
 * native 作为client发起请求
 */
class NativeClientMessageCenter {

    private final BinaryMessenger binaryMessenger;
    private final StandardMethodCodec codec = StandardMethodCodec.INSTANCE;
    private volatile static NativeClientMessageCenter singleInstance;
    private final String channelName;
    private final Handler mainHandler;

    public static NativeClientMessageCenter newInstance(@NonNull BinaryMessenger binaryMessenger, @NonNull String channelName) {
        if (singleInstance == null) {
            synchronized (NativeClientMessageCenter.class) {
                if (singleInstance == null) {
                    singleInstance = new NativeClientMessageCenter(binaryMessenger, channelName);
                }
            }
        }
        return singleInstance;
    }


    private NativeClientMessageCenter(BinaryMessenger binaryMessenger, String channelName) {
        this.binaryMessenger = binaryMessenger;
        this.channelName = channelName;
        this.mainHandler = new Handler(Looper.getMainLooper());
    }

    public void sendMessage(final Object msg, final ResultHandleCallback callback) {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            binaryMessenger.send(channelName, codec.encodeSuccessEnvelope(msg), new BinaryMessenger.BinaryReply() {

                @Override
                public void reply(@Nullable ByteBuffer reply) {
                    //得到返回结果后，执行处理逻辑
                    if (reply != null) {
                        callback.handleResult((byte[]) codec.decodeEnvelope(reply));
                    } else {
                        callback.handleResult(null);
                    }
                }
            });
        } else {
            mainHandler.post(new Runnable() {
                @Override
                public void run() {
                    binaryMessenger.send(channelName, codec.encodeSuccessEnvelope(msg), new BinaryMessenger.BinaryReply() {

                        @Override
                        public void reply(@Nullable ByteBuffer reply) {
                            //得到返回结果后，执行处理逻辑
                            if (reply != null) {
                                callback.handleResult((byte[]) codec.decodeEnvelope(reply));
                            } else {
                                callback.handleResult(null);
                            }
                        }
                    });
                }
            });
        }
    }
}

interface ResultHandleCallback {
    /**
     * 返回值处理
     *
     * @param result 解码之后的返回结果
     */
    void handleResult(@Nullable byte[] result);
}
