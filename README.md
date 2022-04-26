## rxjava项目说明
```text
版本说明：
jdk：17
spring-boot: 2.6.7
```

### 微服务包说明
```text
entity：数据库实体对象
property：属性实体对象
form：入参对象
model：出参对象
repository：数据库操作层
service：服务层
provider：接口层
```

### 一、模块说明

- [docker]：项目本地运行所需要的中间件
- [rxjava-apikit]：Api生成器(包括生成java客户端代码，JavaScript客户端代码，api文档)
- [rxjava-common]：所有项目的通用部分
- [rxjava-service-parent]：微服务父项目pom
- [rxjava-starter]：spring-boot的各种starter

### 二、Api生成器说明

- [rxjava-apikit-core]：一些默认约定，如适配器
- [rxjava-apikit-httl]：因为httl模板引擎不支持jdk17，下载了源码，然后自己编译修改为可以支持jdk17的版本
- [rxjava-apikit-maven-plugin]：maven插件，可直接执行命令生成api
- [rxjava-apikit-tool]：Api生成器实现代码，不支持jdk15+
- [rxjava-apikit-tool-next]：下一代Api生成器实现代码，支持了jdk17

- 注：此处需要配置环境变量MAVEN_OPTS="--add-opens java.base/java.lang=ALL-UNNAMED"
- api插件maven生成器命令：进入微服务目录，执行命令 mvn clean rxjava-apikit:apis

#### ssh方式有时候会出现Auth fail问题，建议使用https方式

### 三、rxjava-starter

- rxjava-starter-bus-amqp：整合了rabbitmq
- rxjava-starter-jpa：整合了jpa
- rxjava-starter-kafka：整合了kafka
- rxjava-starter-mongo-reactive：整合了响应式的mongo
- rxjava-starter-redis：整合了redis
- rxjava-starter-redis-reactive：整合了响应式redis
- rxjava-starter-skywalking：整合了skywalking分布式链路追踪
- rxjava-starter-test-reactive：整合了junit测试
- rxjava-starter-web：整合了web
- rxjava-starter-webflux：整合了spring自动配置，时间格式统一处理，登陆请求拦截，异常统一处理

### 四、如何本地搭建mongodb副本集数据库
```text
docker-compose.yml版本：https://docs.docker.com/compose/compose-file/compose-versioning/
```

一、创建docker-compose.yml文件

```yaml
version: '3.8'
services:
  master-mongo:
    image: mongo
    container_name: master-mongo
    ports:
    - "27017:27017"
    volumes:
    - ./data/master:/data/db
    command: mongod --dbpath /data/db --replSet testSet --oplogSize 128
  secondary-mongo:
    image: mongo
    container_name: secondary-mongo
    ports:
    - "27018:27017"
    volumes:
    - ./data/secondary:/data/db
    command: mongod --dbpath /data/db --replSet testSet --oplogSize 128
  arbiter-mongo:
    image: mongo
    container_name: arbiter-mongo
    ports:
    - "27019:27017"
    volumes:
    - ./data/arbiter:/data/db
    command: mongod --dbpath /data/db --replSet testSet --oplogSize 128
```

二、进入master节点终端bash

```shell script
#1、输入mongo命令，进入mongo命令行
#2、输入以下副本集加入配置(10.22.33.44是宿主机的ip地址)
config={
  _id:"testSet",
  members:[
    {_id:0,host:"10.22.33.44:27017"},
    {_id:1,host:"10.22.33.44:27018"},
    {_id:2,host:"10.22.33.44:27019"}
  ]
}
#3、根据config初始化，初始化成功会返回ok
rs.initiate(config)
#4、查看状态命令
rs.status()
```

三、在@configuration注解下添加响应式事务管理器

```text
@Bean
ReactiveMongoTransactionManager reactiveTransactionManager(ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory) {
    return new ReactiveMongoTransactionManager(reactiveMongoDatabaseFactory);
}
```

四、在需要使用事务的地方添加注解即可

```text
@Transactional
```

```text
统一的异常处理
```

