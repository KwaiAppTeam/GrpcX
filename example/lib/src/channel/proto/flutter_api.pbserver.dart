///
//  Generated code. Do not modify.
//  source: flutter_api.proto
//
// @dart = 2.3
// ignore_for_file: camel_case_types,non_constant_identifier_names,library_prefixes,unused_import,unused_shown_name,return_of_invalid_type

import 'dart:async' as $async;

import 'package:protobuf/protobuf.dart' as $pb;

import 'dart:core' as $core;
import 'flutter_api.pb.dart' as $0;
import 'flutter_api.pbjson.dart';

export 'flutter_api.pb.dart';

abstract class FlutterApiServiceBase extends $pb.GeneratedService {
  $async.Future<$0.FlutterStringValue> getFlutterVersion($pb.ServerContext ctx, $0.FlutterEmpty request);

  $pb.GeneratedMessage createRequest($core.String method) {
    switch (method) {
      case 'getFlutterVersion': return $0.FlutterEmpty();
      default: throw $core.ArgumentError('Unknown method: $method');
    }
  }

  $async.Future<$pb.GeneratedMessage> handleCall($pb.ServerContext ctx, $core.String method, $pb.GeneratedMessage request) {
    switch (method) {
      case 'getFlutterVersion': return this.getFlutterVersion(ctx, request);
      default: throw $core.ArgumentError('Unknown method: $method');
    }
  }

  $core.Map<$core.String, $core.dynamic> get $json => FlutterApiServiceBase$json;
  $core.Map<$core.String, $core.Map<$core.String, $core.dynamic>> get $messageJson => FlutterApiServiceBase$messageJson;
}

