#!/bin/sh

# install protoc if needed
which protoc > /dev/null || brew install protobuf
# install grpc if needed
which grpc_objective_c_plugin > /dev/null || brew install grpc

# install protoc-gen-javalite
[ -f protoc-gen-javalite ] || curl https://repo1.maven.org/maven2/com/google/protobuf/protoc-gen-javalite/3.0.0/protoc-gen-javalite-3.0.0-osx-x86_64.exe -o protoc-gen-javalite
chmod a+x protoc-gen-javalite
 
# install protoc-gen-grpc-java
[ -f protoc-gen-grpc-java ] || curl https://repo1.maven.org/maven2/io/grpc/protoc-gen-grpc-java/1.18.0/protoc-gen-grpc-java-1.18.0-osx-x86_64.exe -o protoc-gen-grpc-java
chmod a+x protoc-gen-grpc-java
 
# install protoc-gen-dart if needed
[ -f ~/.pub-cache/bin/protoc-gen-dart ] || pub global activate protoc_plugin 19.0.1

chmod a+x ServiceObjcPlugin