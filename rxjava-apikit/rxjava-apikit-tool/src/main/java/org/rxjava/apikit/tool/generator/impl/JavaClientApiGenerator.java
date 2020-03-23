package org.rxjava.apikit.tool.generator.impl;

import lombok.Getter;
import lombok.Setter;
import org.rxjava.apikit.tool.info.ApiClassInfo;
import org.rxjava.apikit.tool.info.EnumParamClassInfo;
import org.rxjava.apikit.tool.info.ParamClassInfo;
import org.rxjava.apikit.tool.wrapper.BuilderWrapper;
import org.rxjava.apikit.tool.wrapper.JavaApiWrapper;
import org.rxjava.apikit.tool.wrapper.JavaEnumParamClassWrapper;
import org.rxjava.apikit.tool.wrapper.JavaParamClassWrapper;

import java.io.File;

/**
 * @author happy
 * Java客户端api生成器
 */
@Setter
@Getter
public class JavaClientApiGenerator extends AbstractCommonGenerator {

    public JavaClientApiGenerator(String rootPackage,String outPath) {
        setOutRootPackage(rootPackage);
        setOutPath(outPath);
    }

    public JavaClientApiGenerator() {
    }

    @Override
    public void generateBaseFile() {

    }

    /**
     * 生成java客户端Api文件
     */
    @Override
    public void generateApiFile(ApiClassInfo apiInfo) throws Exception {
        JavaApiWrapper wrapper = new JavaApiWrapper(
                context, apiInfo, outRootPackage, apiNameMaper
        );
        File file = createApiFile(wrapper, "java");
        executeModule(wrapper, "/org/rxjava/apikit/tool/generator/java/ApiClass.httl", file);
    }

    /**
     * 生成参数文件
     */
    @Override
    public void generateParamFile(BuilderWrapper<ParamClassInfo> wrapper) throws Exception {
        File file = createParamClassFile(wrapper, "java");
        executeModule(
                wrapper,
                "/org/rxjava/apikit/tool/generator/java/ParamClass.httl",
                file
        );
    }

    @Override
    public void generateEnumParamFile(BuilderWrapper<EnumParamClassInfo> wrapper) throws Exception {
        File file = createParamClassFile(wrapper, "java");
        executeModule(
                wrapper,
                "/org/rxjava/apikit/tool/generator/java/EnumParamClass.httl",
                file
        );
    }

    /**
     * 创建Java类包装器
     * @param paramClassInfo 参数类信息
     * @param distPack 发布的包文件夹名
     * @param fileName 文件名
     */
    @Override
    protected JavaParamClassWrapper createParamClassWarpper(ParamClassInfo paramClassInfo, String distPack, String fileName) {
        JavaParamClassWrapper javaClassWrapper = new JavaParamClassWrapper(context, paramClassInfo, outRootPackage);
        javaClassWrapper.setDistFolder(distPack);
        return javaClassWrapper;
    }

    /**
     * 创建Java枚举类包装器
     * @param enumParamClassInfo 参数类信息
     * @param distPack 发布的包文件夹名
     * @param fileName 文件名
     */
    @Override
    protected JavaEnumParamClassWrapper createEnumParamClassWarpper(EnumParamClassInfo enumParamClassInfo, String distPack, String fileName) {
        JavaEnumParamClassWrapper javaEnumParamClassWrapper = new JavaEnumParamClassWrapper(context, enumParamClassInfo, outRootPackage);
        javaEnumParamClassWrapper.setDistFolder(distPack);
        return javaEnumParamClassWrapper;
    }
}
