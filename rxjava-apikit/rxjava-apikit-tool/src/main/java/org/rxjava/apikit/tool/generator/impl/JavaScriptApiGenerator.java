package org.rxjava.apikit.tool.generator.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.apikit.tool.info.ApiClassInfo;
import org.rxjava.apikit.tool.info.EnumParamClassInfo;
import org.rxjava.apikit.tool.info.ParamClassInfo;
import org.rxjava.apikit.tool.utils.JsonUtils;
import org.rxjava.apikit.tool.utils.LocalPathUtils;
import org.rxjava.apikit.tool.wrapper.BuilderWrapper;
import org.rxjava.apikit.tool.wrapper.JavaEnumParamClassWrapper;
import org.rxjava.apikit.tool.wrapper.JavaScriptApiWrapper;
import org.rxjava.apikit.tool.wrapper.JavaScriptParamClassWrapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author happy 2019-05-09 17:46
 */
public class JavaScriptApiGenerator extends AbstractCommonGenerator {


    /**
     * 生成Api类文件
     */
    @Override
    public void generateApiFile(ApiClassInfo apiInfo) throws Exception {
        JavaScriptApiWrapper wrapper = new JavaScriptApiWrapper(context, apiInfo, outRootPackage, apiNameMaper, serviceId);
        File jsFile = createApiFile(wrapper, "ts");
        //File dFile = createApiFile(wrapper, "d.ts");
        executeModule(
                wrapper,
                getTemplateFile("Api.httl"),
                jsFile
        );
        //executeModule(
        //        wrapper,
        //        getTemplateFile("Api.d.httl"),
        //        dFile
        //);
    }

    /**
     * 生成参数类文件
     */
    @Override
    public void generateParamFile(BuilderWrapper<ParamClassInfo> wrapper) throws Exception {
        File jsFile = createParamClassFile(wrapper, "ts");
        //File dFile = createParamClassFile(wrapper, "d.ts");
        executeModule(
                wrapper,
                getTemplateFile("ParamClass.httl"),
                jsFile
        );
        //executeModule(
        //        wrapper,
        //        getTemplateFile("ParamClass.d.httl"),
        //        dFile
        //);
    }

    @Override
    public void generateEnumParamFile(BuilderWrapper<EnumParamClassInfo> builderWrapper) throws Exception {
        //todo:
    }

    /**
     * 创建参数类包装器
     */
    @Override
    protected BuilderWrapper<ParamClassInfo> createParamClassWarpper(ParamClassInfo paramClassInfo, String distPack, String distName) {
        JavaScriptParamClassWrapper javaScriptParamClassWrapper = new JavaScriptParamClassWrapper(context, paramClassInfo, outRootPackage);
        javaScriptParamClassWrapper.setDistFolder(distPack);
        return javaScriptParamClassWrapper;
    }

    @Override
    protected BuilderWrapper<EnumParamClassInfo> createEnumParamClassWarpper(EnumParamClassInfo enumParamClassInfo, String distPack, String distName) {
        JavaEnumParamClassWrapper javaEnumParamClassWrapper = new JavaEnumParamClassWrapper(context, enumParamClassInfo, outRootPackage);
        javaEnumParamClassWrapper.setDistFolder(distPack);
        return javaEnumParamClassWrapper;
    }

    private String getTemplateFile(String name) {
        return "/org/rxjava/apikit/tool/generator/tsc/" + name;
    }

    /**
     * 生成基本文件
     */
    @Override
    public void generateBaseFile() throws Exception {
        generateIndexFile();
        generatePackageFile();
    }

    /**
     * 生成index文件
     */
    private void generateIndexFile() throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        List<Map.Entry<?, ?>> apis = context.getApis().getValues().stream().map(apiInfo -> {
            JavaScriptApiWrapper wrapper = new JavaScriptApiWrapper(context, apiInfo, outRootPackage, apiNameMaper, serviceId);
            String value = wrapper.getDistPackage().replace(".", "/") + '/' + wrapper.getDistClassName();
            return new AbstractMap.SimpleImmutableEntry<>(wrapper.getDistClassName(), value);
        }).collect(Collectors.toList());

        parameters.put("apis", apis);

        File jsFile = LocalPathUtils.packToPath(outPath, "", "index", ".ts");
        //File dFile = LocalPathUtils.packToPath(outPath, "", "index", ".d.ts");

        execute(
                parameters,
                getTemplateFile("index.httl"),
                jsFile
        );
        //execute(
        //        parameters,
        //        getTemplateFile("index.d.httl"),
        //        dFile
        //);
    }

    /**
     * 生成package文件
     */
    private void generatePackageFile() throws IOException {
        File packageFile = LocalPathUtils.packToPath(outPath, "", "package", ".json");
        ObjectNode packageJson;
        if (packageFile.exists()) {
            packageJson = (ObjectNode) JsonUtils.DEFAULT_MAPPER.readTree(packageFile);
        } else {
            try (InputStream inputStream = JavaScriptApiGenerator.class.getResourceAsStream(getTemplateFile("package.json"))) {
                packageJson = (ObjectNode) JsonUtils.DEFAULT_MAPPER.readTree(inputStream);
            }
        }

        String apiType = "";
        if (StringUtils.isNotEmpty(this.apiType)) {
            apiType = "-" + this.apiType;
        }
        packageJson.put("name", "rxjava-apis-" + serviceId + apiType);
        if (this.version != null) {
            String prevVersionText = packageJson.get("version").asText();
            if (prevVersionText != null) {
                prevVersionText = prevVersionText.replaceAll("([^.]+)\\.([^.]+)\\.([^.]+)", "$1.$2." + version);
            } else {
                prevVersionText = "1.0." + version;
            }
            packageJson.put("version", prevVersionText);
        }
        JsonUtils.DEFAULT_MAPPER.writerWithDefaultPrettyPrinter().writeValue(packageFile, packageJson);
    }
}