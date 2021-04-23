package com.kwai.creativity.kidea_grpcx_example;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.kwai.creative.flutter.channel.Empty;
import com.kwai.creative.flutter.channel.FlutterApiGrpc;
import com.kwai.creative.flutter.channel.FlutterEmpty;
import com.kwai.creative.flutter.channel.FlutterStringValue;
import com.kwai.creative.flutter.channel.PlatformApiChannelGrpc;
import com.kwai.creative.flutter.channel.StringValue;
import com.kwai.creativity.kidea_grpcx.KideaGrpcxPlugin;
import com.kwai.creativity.kidea_grpcx_example.server.PlatformApiServer;

import java.time.Duration;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.grpc.stub.StreamObserver;

public class MainActivity extends FlutterActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        KideaGrpcxPlugin.addService(PlatformApiChannelGrpc.SERVICE_NAME, new PlatformApiServer());

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FlutterApiGrpc.FlutterApiStub flutterApiStub = FlutterApiGrpc.newStub(KideaGrpcxPlugin.loadClientHandler());
                FlutterEmpty.Builder builder = FlutterEmpty.newBuilder();
                flutterApiStub.getFlutterVersion(builder.build(), new StreamObserver<FlutterStringValue>() {
                    @Override
                    public void onNext(FlutterStringValue value) {
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
            }
        }, 5000);
    }
}
