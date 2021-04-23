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
    comments = "Source: flutter_api.proto")
public final class FlutterApiGrpc {

  private FlutterApiGrpc() {}

  public static final String SERVICE_NAME = "flutter.FlutterApi";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<com.kwai.creative.flutter.channel.FlutterEmpty,
      com.kwai.creative.flutter.channel.FlutterStringValue> getGetFlutterVersionMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "getFlutterVersion",
      requestType = com.kwai.creative.flutter.channel.FlutterEmpty.class,
      responseType = com.kwai.creative.flutter.channel.FlutterStringValue.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<com.kwai.creative.flutter.channel.FlutterEmpty,
      com.kwai.creative.flutter.channel.FlutterStringValue> getGetFlutterVersionMethod() {
    io.grpc.MethodDescriptor<com.kwai.creative.flutter.channel.FlutterEmpty, com.kwai.creative.flutter.channel.FlutterStringValue> getGetFlutterVersionMethod;
    if ((getGetFlutterVersionMethod = FlutterApiGrpc.getGetFlutterVersionMethod) == null) {
      synchronized (FlutterApiGrpc.class) {
        if ((getGetFlutterVersionMethod = FlutterApiGrpc.getGetFlutterVersionMethod) == null) {
          FlutterApiGrpc.getGetFlutterVersionMethod = getGetFlutterVersionMethod = 
              io.grpc.MethodDescriptor.<com.kwai.creative.flutter.channel.FlutterEmpty, com.kwai.creative.flutter.channel.FlutterStringValue>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(
                  "flutter.FlutterApi", "getFlutterVersion"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.kwai.creative.flutter.channel.FlutterEmpty.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.lite.ProtoLiteUtils.marshaller(
                  com.kwai.creative.flutter.channel.FlutterStringValue.getDefaultInstance()))
                  .build();
          }
        }
     }
     return getGetFlutterVersionMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static FlutterApiStub newStub(io.grpc.Channel channel) {
    return new FlutterApiStub(channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static FlutterApiBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    return new FlutterApiBlockingStub(channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static FlutterApiFutureStub newFutureStub(
      io.grpc.Channel channel) {
    return new FlutterApiFutureStub(channel);
  }

  /**
   */
  public static abstract class FlutterApiImplBase implements io.grpc.BindableService {

    /**
     */
    public void getFlutterVersion(com.kwai.creative.flutter.channel.FlutterEmpty request,
        io.grpc.stub.StreamObserver<com.kwai.creative.flutter.channel.FlutterStringValue> responseObserver) {
      asyncUnimplementedUnaryCall(getGetFlutterVersionMethod(), responseObserver);
    }

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
          .addMethod(
            getGetFlutterVersionMethod(),
            asyncUnaryCall(
              new MethodHandlers<
                com.kwai.creative.flutter.channel.FlutterEmpty,
                com.kwai.creative.flutter.channel.FlutterStringValue>(
                  this, METHODID_GET_FLUTTER_VERSION)))
          .build();
    }
  }

  /**
   */
  public static final class FlutterApiStub extends io.grpc.stub.AbstractStub<FlutterApiStub> {
    private FlutterApiStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FlutterApiStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FlutterApiStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FlutterApiStub(channel, callOptions);
    }

    /**
     */
    public void getFlutterVersion(com.kwai.creative.flutter.channel.FlutterEmpty request,
        io.grpc.stub.StreamObserver<com.kwai.creative.flutter.channel.FlutterStringValue> responseObserver) {
      asyncUnaryCall(
          getChannel().newCall(getGetFlutterVersionMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   */
  public static final class FlutterApiBlockingStub extends io.grpc.stub.AbstractStub<FlutterApiBlockingStub> {
    private FlutterApiBlockingStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FlutterApiBlockingStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FlutterApiBlockingStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FlutterApiBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.kwai.creative.flutter.channel.FlutterStringValue getFlutterVersion(com.kwai.creative.flutter.channel.FlutterEmpty request) {
      return blockingUnaryCall(
          getChannel(), getGetFlutterVersionMethod(), getCallOptions(), request);
    }
  }

  /**
   */
  public static final class FlutterApiFutureStub extends io.grpc.stub.AbstractStub<FlutterApiFutureStub> {
    private FlutterApiFutureStub(io.grpc.Channel channel) {
      super(channel);
    }

    private FlutterApiFutureStub(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected FlutterApiFutureStub build(io.grpc.Channel channel,
        io.grpc.CallOptions callOptions) {
      return new FlutterApiFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.kwai.creative.flutter.channel.FlutterStringValue> getFlutterVersion(
        com.kwai.creative.flutter.channel.FlutterEmpty request) {
      return futureUnaryCall(
          getChannel().newCall(getGetFlutterVersionMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_GET_FLUTTER_VERSION = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final FlutterApiImplBase serviceImpl;
    private final int methodId;

    MethodHandlers(FlutterApiImplBase serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_GET_FLUTTER_VERSION:
          serviceImpl.getFlutterVersion((com.kwai.creative.flutter.channel.FlutterEmpty) request,
              (io.grpc.stub.StreamObserver<com.kwai.creative.flutter.channel.FlutterStringValue>) responseObserver);
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
      synchronized (FlutterApiGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .addMethod(getGetFlutterVersionMethod())
              .build();
        }
      }
    }
    return result;
  }
}
