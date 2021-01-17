### 项目已发布到maven中央仓库，当前最新正式版本号为1.0.8，快照版本1.0.9

## rxjava项目说明

### 一、模块说明

- [docker]：项目本地运行所需要的中间件
- [rxjava-apikit](https://mvnrepository.com/artifact/org.rxjava/rxjava-apikit)：Api生成器(包括生成java客户端代码，JavaScript客户端代码)
- [rxjava-example](https://mvnrepository.com/artifact/org.rxjava/rxjava-example)：例子工程
- [rxjava-gateway](https://mvnrepository.com/artifact/org.rxjava/rxjava-gateway)：网关项目pom
- [rxjava-service](https://mvnrepository.com/artifact/org.rxjava/rxjava-service)：微服务项目pom
- [rxjava-spring](https://mvnrepository.com/artifact/org.rxjava/rxjava-spring)：对spring的一些整合
- [rxjava-third-qiniu](https://mvnrepository.com/artifact/org.rxjava/rxjava-third-qiniu)：对七牛的一些整合
- [rxjava-third-weixin](https://mvnrepository.com/artifact/org.rxjava/rxjava-third-weixin)：对微信的一些整合
- [rxjava](https://mvnrepository.com/artifact/org.rxjava/rxjava)：依赖管理pom 

### 二、Api生成器说明

- [rxjava-apikit-core](https://mvnrepository.com/artifact/org.rxjava/rxjava-apikit-core)：一些默认约定，如适配器
- [rxjava-apikit-maven-plugin](https://mvnrepository.com/artifact/org.rxjava/rxjava-apikit-maven-plugin)：maven插件，可直接执行命令生成api
- [rxjava-apikit-tool](https://mvnrepository.com/artifact/org.rxjava/rxjava-apikit-tool)：Api生成器实现代码

- api插件maven生成器命令：进入微服务目录，执行命令 mvn clean rxjava-apikit:apis

#### ssh方式有时候会出现Auth fail问题，建议暂时使用https方式

### 三、例子工程说明

- rxjava-gateway-example：主要示例了如何创建api网关，以及演示了如何使用token换取loginInfo，并注入到router的微服务请求参数中
- rxjava-service-example：主要示例了如何创建微服务，以及一些基础性的配置，并演示了如何手动创建api到test文件夹，并示例了k8s的一些基本配置

### 四、网关项目pom

- pom暂时行只依赖了spring-cloud-gateway，此pom主要是将一些网关的依赖整合到一起

### 五、微服务项目pom

- pom主要是整合依赖，并且演示了如何配置api生成器maven插件，以及一些包的命名规范

### 六、rxjava-spring

- rxjava-spring-boot-core：主要是一些注解，常用实体类，异常处理，包装了mongo分页，以及一些帮助类
- rxjava-spring-boot-starter-service：整合了spring自动配置，时间格式统一处理，登陆请求拦截，异常统一处理
- rxjava-spring-cloud-starter-bus：做了一些消息总线的基础约定
- rxjava-spring-cloud-starter-gateway：做了一些网关的一些基础配置

### 七、rxjava-third-qiniu

- rxjava-third-qiniu-broadcast：主要是对直播的一系列api接口进行的封装
- rxjava-third-qiniu-file：主要是对文件的一系列api接口进行的封装

### 八、rxjava-third-weixin

- rxjava-third-weixin-common：主要是对微信通用类的一些封装
- rxjava-third-weixin-cp：主要是对企业微信/企业号的一系列api接口进行的封装
- rxjava-third-weixin-miniapp：主要是对微信小程序的一系列api接口进行的封装
- rxjava-third-weixin-open：主要是对微信开放平台的一系列api接口进行的封装
- rxjava-third-weixin-pay：主要是对微信支付的一系列api接口进行的封装

### 九、搭建mongodb副本集数据库

一、创建docker-compose.yml文件

```yaml
version: '3.7'
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