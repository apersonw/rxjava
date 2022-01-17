package top.rxjava.apikit.tool.generator.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import top.rxjava.apikit.tool.info.JavaScriptMethodInfo;
import org.apache.commons.lang3.StringUtils;
import top.rxjava.apikit.tool.info.ApiClassInfo;
import top.rxjava.apikit.tool.info.EnumParamClassInfo;
import top.rxjava.apikit.tool.info.ParamClassInfo;
import top.rxjava.apikit.tool.utils.JsonUtils;
import top.rxjava.apikit.tool.utils.LocalPathUtils;
import top.rxjava.apikit.tool.wrapper.BuilderWrapper;
import top.rxjava.apikit.tool.wrapper.JavaEnumParamClassWrapper;
import top.rxjava.apikit.tool.wrapper.JavaScriptApiWrapper;
import top.rxjava.apikit.tool.wrapper.JavaScriptParamClassWrapper;

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
                getTemplateFile("ApiClass.httl"),
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
    public void generateEnumParamFile(BuilderWrapper<EnumParamClassInfo> wrapper) throws Exception {
        File tsFile = createParamClassFile(wrapper, "ts");
        //File dFile = createParamClassFile(wrapper, "d.ts");
        executeModule(
                wrapper,
                getTemplateFile("EnumParamClass.httl"),
                tsFile
        );
        //executeModule(
        //        wrapper,
        //        getTemplateFile("EnumParamClass.d.httl"),
        //        dFile
        //);
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
        return "/top/rxjava/apikit/tool/generator/tsc/" + name;
    }

    /**
     * 生成基本文件
     */
    @Override
    public void generateBaseFile() throws Exception {
        generateIndexFile();
        generatePackageFile();
        generateEslintignoreFile();
        generateEslintrcFile();
        generateGitignoreFile();
        generateNpmignoreFile();
        generateBabelConfigFile();
        generateTsconfigFile();
    }

    /**
     * 生成index文件
     */
    private void generateIndexFile() throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        List<Map.Entry<?, ?>> apis = context.getApis().getValues().stream().map(apiInfo -> {
            JavaScriptApiWrapper wrapper = new JavaScriptApiWrapper(context, apiInfo, outRootPackage, apiNameMaper, serviceId);
            String value = wrapper.getDistPackage().replace(".", "/") + '/' + wrapper.getDistClassName();
            String comment = wrapper.comment(" * ");
            JavaScriptMethodInfo javaScriptMethodInfo = new JavaScriptMethodInfo();
            javaScriptMethodInfo.setApiName(value);
            javaScriptMethodInfo.setComment(comment);
            return new AbstractMap.SimpleImmutableEntry<>(wrapper.getDistClassName(), javaScriptMethodInfo);
        }).collect(Collectors.toList());

        parameters.put("apis", apis);

        File jsFile = LocalPathUtils.packToPath(outPath, "src", "index", ".ts");
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
        packageJson.put("name", serviceId + "-api" + apiType);
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

    /**
     * 生成Eslintignore文件
     */
    private void generateEslintignoreFile() throws Exception {
        File jsFile = LocalPathUtils.packToPath(outPath, "", ".eslintignore", "");
        execute(
                new HashMap<>(0),
                getTemplateFile("eslintignore.httl"),
                jsFile
        );
    }

    /**
     * 生成Eslintrc文件
     */
    private void generateEslintrcFile() throws Exception {
        File jsFile = LocalPathUtils.packToPath(outPath, "", ".eslintrc", "");
        execute(
                new HashMap<>(0),
                getTemplateFile("eslintrc.httl"),
                jsFile
        );
    }

    /**
     * 生成Gitignore文件
     */
    private void generateGitignoreFile() throws Exception {
        File jsFile = LocalPathUtils.packToPath(outPath, "", ".gitignore", "");
        execute(
                new HashMap<>(0),
                getTemplateFile("gitignore.httl"),
                jsFile
        );
    }

    /**
     * 生成Npmignore文件
     */
    private void generateNpmignoreFile() throws Exception {
        File jsFile = LocalPathUtils.packToPath(outPath, "", ".npmignore", "");
        execute(
                new HashMap<>(0),
                getTemplateFile("npmignore.httl"),
                jsFile
        );
    }

    /**
     * 生成BabelConfig文件
     */
    private void generateBabelConfigFile() throws Exception {
        File jsFile = LocalPathUtils.packToPath(outPath, "", "babel.config", ".js");
        execute(
                new HashMap<>(0),
                getTemplateFile("babel.config.httl"),
                jsFile
        );
    }

    /**
     * 生成BabelConfig文件
     */
    private void generateTsconfigFile() throws Exception {
        File jsFile = LocalPathUtils.packToPath(outPath, "", "tsconfig", ".json");
        execute(
                new HashMap<>(0),
                getTemplateFile("tsconfig.httl"),
                jsFile
        );
    }
}