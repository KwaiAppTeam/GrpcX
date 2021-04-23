///
//  Generated code. Do not modify.
//  source: flutter_api.proto
//
// @dart = 2.3
// ignore_for_file: camel_case_types,non_constant_identifier_names,library_prefixes,unused_import,unused_shown_name,return_of_invalid_type

import 'dart:async' as $async;
import 'dart:core' as $core;

import 'package:protobuf/protobuf.dart' as $pb;

class FlutterEmpty extends $pb.GeneratedMessage {
  static final $pb.BuilderInfo _i = $pb.BuilderInfo('FlutterEmpty', package: const $pb.PackageName('flutter'), createEmptyInstance: create)
    ..hasRequiredFields = false
  ;

  FlutterEmpty._() : super();
  factory FlutterEmpty() => create();
  factory FlutterEmpty.fromBuffer($core.List<$core.int> i, [$pb.ExtensionRegistry r = $pb.ExtensionRegistry.EMPTY]) => create()..mergeFromBuffer(i, r);
  factory FlutterEmpty.fromJson($core.String i, [$pb.ExtensionRegistry r = $pb.ExtensionRegistry.EMPTY]) => create()..mergeFromJson(i, r);
  FlutterEmpty clone() => FlutterEmpty()..mergeFromMessage(this);
  FlutterEmpty copyWith(void Function(FlutterEmpty) updates) => super.copyWith((message) => updates(message as FlutterEmpty));
  $pb.BuilderInfo get info_ => _i;
  @$core.pragma('dart2js:noInline')
  static FlutterEmpty create() => FlutterEmpty._();
  FlutterEmpty createEmptyInstance() => create();
  static $pb.PbList<FlutterEmpty> createRepeated() => $pb.PbList<FlutterEmpty>();
  @$core.pragma('dart2js:noInline')
  static FlutterEmpty getDefault() => _defaultInstance ??= $pb.GeneratedMessage.$_defaultFor<FlutterEmpty>(create);
  static FlutterEmpty _defaultInstance;
}

class FlutterStringValue extends $pb.GeneratedMessage {
  static final $pb.BuilderInfo _i = $pb.BuilderInfo('FlutterStringValue', package: const $pb.PackageName('flutter'), createEmptyInstance: create)
    ..aOS(1, 'value')
    ..hasRequiredFields = false
  ;

  FlutterStringValue._() : super();
  factory FlutterStringValue() => create();
  factory FlutterStringValue.fromBuffer($core.List<$core.int> i, [$pb.ExtensionRegistry r = $pb.ExtensionRegistry.EMPTY]) => create()..mergeFromBuffer(i, r);
  factory FlutterStringValue.fromJson($core.String i, [$pb.ExtensionRegistry r = $pb.ExtensionRegistry.EMPTY]) => create()..mergeFromJson(i, r);
  FlutterStringValue clone() => FlutterStringValue()..mergeFromMessage(this);
  FlutterStringValue copyWith(void Function(FlutterStringValue) updates) => super.copyWith((message) => updates(message as FlutterStringValue));
  $pb.BuilderInfo get info_ => _i;
  @$core.pragma('dart2js:noInline')
  static FlutterStringValue create() => FlutterStringValue._();
  FlutterStringValue createEmptyInstance() => create();
  static $pb.PbList<FlutterStringValue> createRepeated() => $pb.PbList<FlutterStringValue>();
  @$core.pragma('dart2js:noInline')
  static FlutterStringValue getDefault() => _defaultInstance ??= $pb.GeneratedMessage.$_defaultFor<FlutterStringValue>(create);
  static FlutterStringValue _defaultInstance;

  @$pb.TagNumber(1)
  $core.String get value => $_getSZ(0);
  @$pb.TagNumber(1)
  set value($core.String v) { $_setString(0, v); }
  @$pb.TagNumber(1)
  $core.bool hasValue() => $_has(0);
  @$pb.TagNumber(1)
  void clearValue() => clearField(1);
}

class FlutterApiApi {
  $pb.RpcClient _client;
  FlutterApiApi(this._client);

  $async.Future<FlutterStringValue> getFlutterVersion($pb.ClientContext ctx, FlutterEmpty request) {
    var emptyResponse = FlutterStringValue();
    return _client.invoke<FlutterStringValue>(ctx, 'FlutterApi', 'getFlutterVersion', request, emptyResponse);
  }
}

