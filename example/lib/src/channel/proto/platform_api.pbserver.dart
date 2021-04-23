///
//  Generated code. Do not modify.
//  source: platform_api.proto
//
// @dart = 2.3
// ignore_for_file: camel_case_types,non_constant_identifier_names,library_prefixes,unused_import,unused_shown_name,return_of_invalid_type

import 'dart:async' as $async;

import 'package:protobuf/protobuf.dart' as $pb;

import 'dart:core' as $core;
import 'platform_api.pb.dart' as $0;
import 'platform_api.pbjson.dart';

export 'platform_api.pb.dart';

abstract class PlatformApiChannelServiceBase extends $pb.GeneratedService {
  $async.Future<$0.StringValue> getPlatformVersion($pb.ServerContext ctx, $0.Empty request);

  $pb.GeneratedMessage createRequest($core.String method) {
    switch (method) {
      case 'getPlatformVersion': return $0.Empty();
      default: throw $core.ArgumentError('Unknown method: $method');
    }
  }

  $async.Future<$pb.GeneratedMessage> handleCall($pb.ServerContext ctx, $core.String method, $pb.GeneratedMessage request) {
    switch (method) {
      case 'getPlatformVersion': return this.getPlatformVersion(ctx, request);
      default: throw $core.ArgumentError('Unknown method: $method');
    }
  }

  $core.Map<$core.String, $core.dynamic> get $json => PlatformApiChannelServiceBase$json;
  $core.Map<$core.String, $core.Map<$core.String, $core.dynamic>> get $messageJson => PlatformApiChannelServiceBase$messageJson;
}

