package org.rxjava.apikit.tool.generator.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.apikit.tool.info.ApiClassInfo;
import org.rxjava.apikit.tool.info.EnumParamClassInfo;
import org.rxjava.apikit.tool.info.ParamClassInfo;
import org.rxjava.apikit.tool.info.ServiceInfo;
import org.rxjava.apikit.tool.utils.JsonUtils;
import org.rxjava.apikit.tool.utils.LocalPathUtils;
import org.rxjava.apikit.tool.wrapper.*;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author happy
 * Java客户端api生成器
 */
@Setter
@Getter
public class JavaClientApiGenerator extends AbstractCommonGenerator {
    private String artifactId;
    private String parentVersion;

    public JavaClientApiGenerator(String rootPackage, String outPath) {
        setOutRootPackage(rootPackage);
        setOutPath(outPath);
    }

    public JavaClientApiGenerator() {
    }

    @Override
    public void generateBaseFile() throws Exception {
        generatePomFile();
        generateIgnoreFile();
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
        executeModule(wrapper, getTemplateFile("ApiClass.httl"), file);
    }

    /**
     * 生成参数文件
     */
    @Override
    public void generateParamFile(BuilderWrapper<ParamClassInfo> wrapper) throws Exception {
        File file = createParamClassFile(wrapper, "java");
        executeModule(
                wrapper,
                getTemplateFile("ParamClass.httl"),
                file
        );
    }

    @Override
    public void generateEnumParamFile(BuilderWrapper<EnumParamClassInfo> wrapper) throws Exception {
        File file = createParamClassFile(wrapper, "java");
        executeModule(
                wrapper,
                getTemplateFile("EnumParamClass.httl"),
                file
        );
    }

    /**
     * 创建Java类包装器
     *
     * @param paramClassInfo 参数类信息
     * @param distPack       发布的包文件夹名
     * @param fileName       文件名
     */
    @Override
    protected JavaParamClassWrapper createParamClassWarpper(ParamClassInfo paramClassInfo, String distPack, String fileName) {
        JavaParamClassWrapper javaClassWrapper = new JavaParamClassWrapper(context, paramClassInfo, outRootPackage);
        javaClassWrapper.setDistFolder(distPack);
        return javaClassWrapper;
    }

    /**
     * 创建Java枚举类包装器
     *
     * @param enumParamClassInfo 参数类信息
     * @param distPack           发布的包文件夹名
     * @param fileName           文件名
     */
    @Override
    protected JavaEnumParamClassWrapper createEnumParamClassWarpper(EnumParamClassInfo enumParamClassInfo, String distPack, String fileName) {
        JavaEnumParamClassWrapper javaEnumParamClassWrapper = new JavaEnumParamClassWrapper(context, enumParamClassInfo, outRootPackage);
        javaEnumParamClassWrapper.setDistFolder(distPack);
        return javaEnumParamClassWrapper;
    }

    private String getTemplateFile(String name) {
        return "/org/rxjava/apikit/tool/generator/java/" + name;
    }

    /**
     * 生成pom文件
     */
    private void generatePomFile() throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        ServiceInfo serviceInfo = new ServiceInfo();
        serviceInfo.setArtifactId(artifactId);
        serviceInfo.setParentVersion(parentVersion);

        parameters.put("serviceInfo", serviceInfo);

        //项目路径
        String outProjectPath = outPath.replace("/src/main/java", "");
        File indexFile = LocalPathUtils.packToPath(outProjectPath, "", "pom", ".xml");
        execute(
                parameters,
                getTemplateFile("pom.httl"),
                indexFile
        );
    }

    /**
     * 生成忽略文件
     */
    private void generateIgnoreFile() throws Exception {

        Map<String, Object> parameters = new HashMap<>();

        //项目路径
        String outProjectPath = outPath.replace("/src/main/java", "");
        File outFile = new File(outProjectPath.replace(".", File.separator) + "/.gitignore");
        execute(
                parameters,
                getTemplateFile("gitignore.httl"),
                outFile
        );
    }
}
