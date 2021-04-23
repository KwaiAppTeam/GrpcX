package com.kwai.creativity.kidea_grpcx_example.server;

import com.kwai.creative.flutter.channel.Empty;
import com.kwai.creative.flutter.channel.PlatformApiChannelGrpc;
import com.kwai.creative.flutter.channel.StringValue;

public class PlatformApiServer extends PlatformApiChannelGrpc.PlatformApiChannelImplBase {

    @Override
    public void getPlatformVersion(Empty request, io.grpc.stub.StreamObserver<StringValue> responseObserver) {
        responseObserver.onNext(StringValue.getDefaultInstance().toBuilder().setValue("1.8.0").build());
        responseObserver.onCompleted();
    }
}
