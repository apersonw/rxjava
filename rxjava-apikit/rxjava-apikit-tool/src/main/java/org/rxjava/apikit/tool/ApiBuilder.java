package org.rxjava.apikit.tool;

import org.rxjava.apikit.tool.analyse.impl.ApiAnalyse;
import org.rxjava.apikit.tool.generator.ApiContext;
import org.rxjava.apikit.tool.generator.impl.ApidocGenerator;

public class ApiBuilder {
    private ApiContext apiContext = new ApiContext();
    /**
     * 分析包
     */
    private String analysePackage;
    /**
     * java输出api路径
     */
    private String javaOutApiPath;

    public void build(String analysePackage,String javaOutApiPath) {
        ApiAnalyse apiAnalyse = new ApiAnalyse();
        apiAnalyse.analyse(analysePackage, apiContext);
        ApidocGenerator apidocGenerator = new ApidocGenerator();
        apidocGenerator.generator(javaOutApiPath,apiContext);
    }
}
