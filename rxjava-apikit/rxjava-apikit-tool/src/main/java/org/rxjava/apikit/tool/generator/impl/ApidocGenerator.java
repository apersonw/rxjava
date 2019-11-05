package org.rxjava.apikit.tool.generator.impl;

import org.rxjava.apikit.tool.generator.ApiContext;
import org.rxjava.apikit.tool.utils.HttlUtils;
import org.rxjava.apikit.tool.utils.LocalPathUtils;

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
                .map(controllerInfo -> {
                    return controllerInfo.getSimpleName();
                })
                .collect(Collectors.toList());

        Map<String, Object> params = new HashMap<>();
        params.put("nameList", nameList);
        File file = LocalPathUtils.packToPath(javaOutApiPath, "test", "api.java");
        try {
            HttlUtils.renderFile(file, params, getTemplateFile("test.httl"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String getTemplateFile(String name) {
        return "/org/rxjava/apikit/tool/generator/apidoc/" + name;
    }
}
