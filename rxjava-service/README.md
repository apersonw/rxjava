### 一、模块说明

rxjava-apikit-maven-plugin

主要是整合了api基本的生成配置

子项目可进行一下的一些配置

```xml
<properties>
  <git.url>${项目api生成后git推送的地址路径}</git.url>
  <module.name>${模块名称，纯英文，首字母小写}</module.name>
  <module.Name>${模块名称，同module.name，首字母大写}</module.Name>
  <module.type>${模块类型，默认为service，可根据需要进行修改，首字母小写}</module.type>
  <module.Type>${模块类型，默认为Service，可根据需要进行修改，首字母大写}</module.Type>
</properties>
```
