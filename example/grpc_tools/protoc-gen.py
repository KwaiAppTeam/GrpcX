#!/usr/bin/env python

import os
import argparse
import subprocess
import sys

def protoc_gen(output_type, proto_file, dart_output_dir, android_output_dir, ios_output_dir):

    proto_dir = os.path.dirname(proto_file)
    if proto_dir == '':
        proto_dir = '.'
    proto_name = os.path.basename(proto_file)
    print '------- build proto dir: ' + proto_dir

    if output_type == 'dart':
        _protoc_gen_dart(proto_dir, proto_name, dart_output_dir)
    elif output_type == 'android':
        _protoc_gen_android(proto_dir, proto_name, android_output_dir)
    elif output_type == 'ios':
        _protoc_gen_ios(proto_dir, proto_name, ios_output_dir)
    elif output_type == 'all':
        for file in os.listdir(proto_dir):
            if file.endswith(".proto"):
                proto_name = os.path.basename(file)
                print '----- build proto name: ' + proto_name
                _protoc_gen_dart(proto_dir, proto_name, dart_output_dir)
                _protoc_gen_android(proto_dir, proto_name, android_output_dir)
                _protoc_gen_ios(proto_dir, proto_name, ios_output_dir)
    else:
        print('unknown type: {}'.format(output_type))

def _protoc_gen_dart(proto_dir, proto_name, output_dir):
    subprocess.call(['protoc', '--plugin=protoc-gen-dart={}/.pub-cache/bin/protoc-gen-dart'.format(os.environ['HOME']), '--dart_out={}'.format(output_dir), '--proto_path={}'.format(proto_dir), proto_name])


def _protoc_gen_android(proto_dir, proto_file, output_dir):
    dirname = os.path.dirname(sys.argv[0])
    if dirname != '':
        dirname = '{}/'.format(dirname)

    subprocess.call(['protoc', '--plugin=protoc-gen-javalite={}protoc-gen-javalite'.format(dirname), '--javalite_out={}'.format(output_dir), '--proto_path={}'.format(proto_dir), proto_file])
    subprocess.call(['protoc', '--plugin=protoc-gen-grpc-java={}protoc-gen-grpc-java'.format(dirname), '--grpc-java_out=lite:{}'.format(output_dir), '--proto_path={}'.format(proto_dir), proto_file])

def _protoc_gen_ios(proto_dir, proto_file, output_dir):
    dirname = os.path.dirname(sys.argv[0])
    if dirname != '':
        dirname = '{}/'.format(dirname)

	subprocess.call(['protoc', '--objc_out={}'.format(output_dir), '--proto_path={}'.format(proto_dir), proto_file])
    subprocess.call(['protoc', '--plugin=protoc-gen-grpc=/usr/local/bin/grpc_objective_c_plugin'.format(dirname), '--grpc_out=lite:{}'.format(output_dir), '--proto_path={}'.format(proto_dir), proto_file])
    subprocess.call(['protoc', '--plugin=protoc-gen-objc_grpc={}ServiceObjcPlugin'.format(dirname), '--objc_grpc_out={}'.format(output_dir), '--proto_path={}'.format(proto_dir), proto_file])

if __name__ == '__main__':
    parser = argparse.ArgumentParser()
    parser.add_argument('-t', '--type', help='output type', required=True)
    parser.add_argument('-i', '--input', help='input protobuf file dir', required=True)
    parser.add_argument('-do', '--dart_output', help='dart output dir', required=True)
    parser.add_argument('-ao', '--android_output', help='android output dir', required=True)
    parser.add_argument('-io', '--ios_output', help='ios output dir', required=True)

    args = parser.parse_args()
    protoc_gen(args.type, args.input, args.dart_output, args.android_output, args.ios_output)