#!/usr/bin/env bash
# 构建
mvn clean install -Prelease

# 推送构建的docker镜像到阿里云服务器
rxjava_service_example_tag=registry.cn-shanghai.aliyuncs.com/taro-mall/rxjava-service-example:latest

docker build ./target/docker-bin/ -t ${rxjava_service_example_tag}

docker push ${rxjava_service_example_tag}