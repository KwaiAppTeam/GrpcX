#define proto
PROTO_DART_OUTPUT=lib/src/channel/proto
PROTO_TOOLS_DIR=./grpc_tools
PROTO_ANDROID_OUTPUT=android/app/src/main/channel/
PROTO_IOS_OUTPUT=ios/Runner/Channels
PROTO_SOURCE_DIR=./proto/

all:

# 切换分支时，yaml 改变且要 run
crun:  FORCE
	flutter clean
	flutter packages get
	flutter run --flavor arm64

# 执行 pub get 先 clean
get:	FORCE
	flutter clean
	flutter packages get

run:	FORCE
	dart ./script/check_flutter_version.dart

fvm:	FORCE
	dart ./script/build_fvm_env.dart

# 与生成代码相关 source gen
build:	FORCE
	flutter pub pub run build_runner build --delete-conflicting-outputs

# 清理 build_runner 产物
build_clean:  FORCE
	flutter pub pub run build_runner clean

watch:	FORCE
	flutter pub pub run build_runner watch --delete-conflicting-outputs

# 运行中挂载，断点调试用
attach:	FORCE
	flutter attach

# 生成三方通信文件 pb
proto: FORCE
	rm -rf ${PROTO_DART_OUTPUT}
	rm -rf ${PROTO_ANDROID_OUTPUT}
	rm -rf ${PROTO_IOS_OUTPUT}
	mkdir -p ${PROTO_DART_OUTPUT}
	mkdir -p ${PROTO_ANDROID_OUTPUT}
	mkdir -p ${PROTO_IOS_OUTPUT}
	${PROTO_TOOLS_DIR}/protoc-gen.py -t all -i ${PROTO_SOURCE_DIR} -do ${PROTO_DART_OUTPUT} -ao ${PROTO_ANDROID_OUTPUT} -io ${PROTO_IOS_OUTPUT}

setup:	FORCE
	@cd ${PROTO_TOOLS_DIR} && ./setup.sh

.PHONY: FORCE

