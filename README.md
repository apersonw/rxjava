#### 如果你觉得还行，请给个star，感谢你的支持🙏
## QQ交流群：828300414，加群答案：taro-jd

## rxjava项目说明

### 一、模块说明

- rxjava-apikit：Api生成器(包括生成java客户端代码，JavaScript客户端代码)
- rxjava-example：例子工程
- rxjava-gateway：网关项目pom
- rxjava-service：微服务项目pom
- rxjava-spring：对spring的一些整合

### 二、Api生成器说明

- rxjava-apikit-core：一些默认约定，如适配器
- rxjava-apikit-maven-plugin：maven插件，可直接执行命令生成api
- rxjava-apikit-tool：Api生成器实现代码

### 三、例子工程说明

- rxjava-gateway-example：主要示例了如何创建api网关，以及演示了如何使用token换取loginInfo，并注入到router的微服务请求参数中
- rxjava-service-example：主要示例了如何创建微服务，以及一些基础性的配置，并演示了如何手动创建api到test文件夹，并示例了k8s的一些基本配置
- rxjava-security-example：主要示例了如何使用security创建授权微服务，并演示如何认证和授权

### 四、网关项目pom

- pom暂时行只依赖了spring-cloud-gateway，此pom主要是将一些网关的依赖整合到一起

### 五、微服务项目pom

- pom主要是整合依赖，并且演示了如何配置api生成器maven插件，以及一些包的命名规范

### 六、rxjava-spring

- rxjava-spring-boot-core：主要是一些注解，常用实体类，异常处理，包装了mongo分页，以及一些帮助类
- rxjava-spring-boot-starter-service：整合了spring自动配置，时间格式统一处理，登陆请求拦截，异常统一处理
- rxjava-spring-cloud-starter-bus：做了一些消息总线的基础约定
- rxjava-spring-cloud-starter-gateway：未来将做一些网关的统一配置整合