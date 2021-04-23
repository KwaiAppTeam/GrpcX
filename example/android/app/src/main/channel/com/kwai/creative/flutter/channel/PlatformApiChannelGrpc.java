package com.kwai.creative.flutter.channel;

import static io.grpc.MethodDescriptor.generateFullMethodName;
import static io.grpc.stub.ClientCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ClientCalls.asyncClientStreamingCall;
import static io.grpc.stub.ClientCalls.asyncServerStreamingCall;
import static io.grpc.stub.ClientCalls.asyncUnaryCall;
import static io.grpc.stub.ClientCalls.blockingServerStreamingCall;
import static io.grpc.stub.ClientCalls.blockingUnaryCall;
import static io.grpc.stub.ClientCalls.futureUnaryCall;
import static io.grpc.stub.ServerCalls.asyncBidiStreamingCall;
import static io.grpc.stub.ServerCalls.asyncClientStreamingCall;
import static io.grpc.stub.ServerCalls.asyncServerStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnaryCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedStreamingCall;
import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.18.0)",
    comments = "Source: platform_api.proto")
public final class PlatformApiChannelGrpc {

  private PlatformApiChannelGrpc() {}

  public static final String SERVICE_NAME = "platform.PlatformApiChannel";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.kwai.creative.flutter.channel.Empty,
      com.kwai.creative.flutter.channel.StringValue> getGetPlatformVersionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getPlatformVersion",
      requestType = com.kwai.creative.flutter.channel.Empty.class,
      responseType = com.kwai.creative.flutter.channel.StringValue.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.kwai.creative.flutter.channel.Empty,
      com.kwai.creative.flutter.channel.StringValue> getGetPlatformVersionMethod() {
    io.grpc.MethodDescriptor<com.kwai.creative.flutter.channel.Empty, com.kwai.creative.flutter.channel.StringValue> getGetPlatformVersionMethod;
    if ((getGetPlatformVersionMethod = PlatformApiChannelGrpc.getGetPlatformVersionMethod) == null) {
      synchronized (PlatformApiChannelGrpc.class) {
        if ((getGetPlatformVersionMethod = PlatformApiChannelGrpc.getGetPlatformVersionMethod) == null) {
          PlatformApiChannelGrpc.getGetPlatformVersionMethod = getGetPlatformVersionMethod = 
              io.grpc.MethodDescriptor.<com.kwai.creative.flutter.channel.Empty, com.kwai.creative.flutter.channel.StringValue>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "platform.PlatformApiChannel", "getPlatformVersion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.kwai.creative.flutter.channel.Empty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.kwai.creative.flutter.channel.StringValue.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getGetPlatformVersionMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static PlatformApiChannelStub newStub(io.grpc.Channel channel) {
    return new PlatformApiChannelStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static PlatformApiChannelBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new PlatformApiChannelBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static PlatformApiChannelFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new PlatformApiChannelFutureStub(channel);
  }

  /**
   */
  public static abstract class PlatformApiChannelImplBase implements io.grpc.BindableService {

    /**
     */
    public void getPlatformVersion(com.kwai.creative.flutter.channel.Empty request,
        io.grpc.stub.StreamObserver<com.kwai.creative.flutter.channel.StringValue> responseObserver) {
      asyncUnimplementedUnaryCall(getGetPlatformVersionMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetPlatformVersionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.kwai.creative.flutter.channel.Empty,
                com.kwai.creative.flutter.channel.StringValue>(
                  this, METHODID_GET_PLATFORM_VERSION)))
          .build();
    }
  }

  /**
   */
  public static final class PlatformApiChannelStub extends io.grpc.stub.AbstractStub<PlatformApiChannelStub> {
    private PlatformApiChannelStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlatformApiChannelStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlatformApiChannelStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlatformApiChannelStub(channel, callOptions);
    }

    /**
     */
    public void getPlatformVersion(com.kwai.creative.flutter.channel.Empty request,
        io.grpc.stub.StreamObserver<com.kwai.creative.flutter.channel.StringValue> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetPlatformVersionMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class PlatformApiChannelBlockingStub extends io.grpc.stub.AbstractStub<PlatformApiChannelBlockingStub> {
    private PlatformApiChannelBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlatformApiChannelBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlatformApiChannelBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlatformApiChannelBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.kwai.creative.flutter.channel.StringValue getPlatformVersion(com.kwai.creative.flutter.channel.Empty request) {
      return blockingUnaryCall(
          getChannel(), getGetPlatformVersionMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class PlatformApiChannelFutureStub extends io.grpc.stub.AbstractStub<PlatformApiChannelFutureStub> {
    private PlatformApiChannelFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private PlatformApiChannelFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected PlatformApiChannelFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new PlatformApiChannelFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.kwai.creative.flutter.channel.StringValue> getPlatformVersion(
        com.kwai.creative.flutter.channel.Empty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetPlatformVersionMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_PLATFORM_VERSION = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final PlatformApiChannelImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(PlatformApiChannelImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_PLATFORM_VERSION:
          serviceImpl.getPlatformVersion((com.kwai.creative.flutter.channel.Empty) request,
              (io.grpc.stub.StreamObserver<com.kwai.creative.flutter.channel.StringValue>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (PlatformApiChannelGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getGetPlatformVersionMethod())
              .build();
        }
      }
    }
    return result;
  }
}
