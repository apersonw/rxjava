package top.rxjava.apikit.tool.generator.impl;

import top.rxjava.apikit.tool.info.ApiClassInfo;
import top.rxjava.apikit.tool.info.EnumParamClassInfo;
import top.rxjava.apikit.tool.info.ParamClassInfo;
import top.rxjava.apikit.tool.wrapper.ApidocApiWrapper;
import top.rxjava.apikit.tool.wrapper.ApidocParamClassWrapper;
import top.rxjava.apikit.tool.wrapper.BuilderWrapper;

import java.io.File;

/**
 * @author happy 2019/9/29 03:28
 */
public class ApidocApiGenerator extends AbstractCommonGenerator {
    @Override
    public void generateBaseFile() throws Exception {
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
        return null;
    }

    @Override
    public void generateParamFile(BuilderWrapper<ParamClassInfo> wrapper) throws Exception {
    }

    @Override
    public void generateEnumParamFile(BuilderWrapper<EnumParamClassInfo> builderWrapper) throws Exception {
    }
}
