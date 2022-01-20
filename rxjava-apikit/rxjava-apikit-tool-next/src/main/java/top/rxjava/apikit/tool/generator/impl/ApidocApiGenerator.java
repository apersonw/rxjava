package top.rxjava.apikit.tool.generator.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.StringUtils;
import top.rxjava.apikit.tool.info.ApiClassInfo;
import top.rxjava.apikit.tool.info.EnumParamClassInfo;
import top.rxjava.apikit.tool.info.ParamClassInfo;
import top.rxjava.apikit.tool.utils.JsonUtils;
import top.rxjava.apikit.tool.utils.LocalPathUtils;
import top.rxjava.apikit.tool.wrapper.ApidocApiWrapper;
import top.rxjava.apikit.tool.wrapper.ApidocParamClassWrapper;
import top.rxjava.apikit.tool.wrapper.BuilderWrapper;
import top.rxjava.apikit.tool.wrapper.JavaScriptApiWrapper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author happy 2019/9/29 03:28
 */
public class ApidocApiGenerator extends AbstractCommonGenerator {
    @Override
    public void generateBaseFile() throws Exception {
        generateIndexFile();
        generatePackageFile();
    }

    @Override
    public void generateApiFile(ApiClassInfo apiInfo) throws Exception {
        ApidocApiWrapper wrapper = new ApidocApiWrapper(context, apiInfo, outRootPackage, apiNameMaper, serviceId);
        File apiFile = createApiFile(wrapper, "js");
        executeModule(
                wrapper,
                getTemplateFile("api.httl"),
                apiFile
        );
    }

    private String getTemplateFile(String name) {
        return "/top/rxjava/apikit/tool/generator/apidoc/" + name;
    }

    @Override
    protected BuilderWrapper<ParamClassInfo> createParamClassWarpper(ParamClassInfo paramClassInfo, String distPack, String distName) {
        ApidocParamClassWrapper apidocParamClassWrapper = new ApidocParamClassWrapper(context, paramClassInfo, outRootPackage);
        apidocParamClassWrapper.setDistFolder(distPack);
        return apidocParamClassWrapper;
    }

    @Override
    protected BuilderWrapper<EnumParamClassInfo> createEnumParamClassWarpper(EnumParamClassInfo paramClassInfo, String distPack, String distName) {
        //todo:
        return null;
    }

    @Override
    public void generateParamFile(BuilderWrapper<ParamClassInfo> wrapper) throws Exception {
        File paramClassFile = createParamClassFile(wrapper, "js");
        executeModule(
                wrapper,
                getTemplateFile("paramClass.httl"),
                paramClassFile
        );
    }

    @Override
    public void generateEnumParamFile(BuilderWrapper<EnumParamClassInfo> builderWrapper) throws Exception {
        //todo:
    }

    /**
     * 生成index文件
     */
    private void generateIndexFile() throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        List<Map.Entry<?, ?>> apis = context.getApiClassInfoMultimap().values().stream().map(apiInfo -> {
            JavaScriptApiWrapper wrapper = new JavaScriptApiWrapper(context, apiInfo, outRootPackage, apiNameMaper, serviceId);
            String value = wrapper.getDistPackage().replace(".", "/") + '/' + wrapper.getDistClassName();
            return new AbstractMap.SimpleImmutableEntry<>(wrapper.getDistClassName(), value);
        }).collect(Collectors.toList());

        parameters.put("apis", apis);

        File indexFile = LocalPathUtils.packToPath(outPath, "", "index", ".html");

        execute(
                parameters,
                getTemplateFile("index.httl"),
                indexFile
        );
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
