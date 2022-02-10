package top.rxjava.apikit.tool.generator.impl;

import top.rxjava.apikit.tool.form.UploadToShowDocForm;
import top.rxjava.apikit.tool.info.ApiClassInfo;
import top.rxjava.apikit.tool.info.ApiMethodInfo;
import top.rxjava.apikit.tool.info.EnumParamClassInfo;
import top.rxjava.apikit.tool.info.ParamClassInfo;
import top.rxjava.apikit.tool.wrapper.ApidocApiWrapper;
import top.rxjava.apikit.tool.wrapper.ApidocParamClassWrapper;
import top.rxjava.apikit.tool.wrapper.BuilderWrapper;
import top.rxjava.common.utils.JsonUtils;

/**
 * @author happy 2019/9/29 03:28
 */
public class ApidocApiGenerator extends AbstractCommonGenerator {
    @Override
    public void generateBaseFile() {
    }

    @Override
    public void generateApiFile(ApiClassInfo apiInfo) {
        ApidocApiWrapper wrapper = new ApidocApiWrapper(context, apiInfo, outRootPackage, apiNameMaper, serviceId);

        ApiClassInfo classInfo = wrapper.getClassInfo();
        for (ApiMethodInfo m : classInfo.getApiMethodList()) {
            try {
                String pageContent = executeApidocContent(wrapper, m, getTemplateFile("ApidocMethod.httl"));

                UploadToShowDocForm form = new UploadToShowDocForm();
                form.setCatName(classInfo.getJavaDocInfo().getFirstRow());
                form.setPageContent(pageContent);
                form.setPageTitle(m.getJavaDocInfo().getFirstRow());
                System.out.println(JsonUtils.serialize(form));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
    public void generateParamFile(BuilderWrapper<ParamClassInfo> wrapper) {
    }

    @Override
    public void generateEnumParamFile(BuilderWrapper<EnumParamClassInfo> builderWrapper) {
    }
}
