package top.rxjava.apikit.tool.generator.impl;

import top.rxjava.apikit.tool.generator.ApiContext;
import top.rxjava.apikit.tool.info.ClassBaseInfo;
import top.rxjava.apikit.tool.utils.HttlUtils;
import top.rxjava.apikit.tool.utils.LocalPathUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ApidocGenerator {
    public void generator(String javaOutApiPath, ApiContext apiContext) {
        List<String> nameList = apiContext.getControllerInfos().stream()
                .map(ClassBaseInfo::getSimpleName)
                .collect(Collectors.toList());

        Map<String, Object> params = new HashMap<>();
        params.put("nameList", nameList);
        File file = LocalPathUtils.packToPath(javaOutApiPath, "test", "api.java");
        try {
            HttlUtils.renderFile(file, params, getTemplateFile("test.httl"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private String getTemplateFile(String name) {
        return "/top/rxjava/apikit/tool/generator/apidoc/" + name;
    }
}
