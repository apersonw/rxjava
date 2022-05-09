package top.rxjava.apikit.tool.generator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.rxjava.apikit.tool.generator.AbstractGenerator;
import top.rxjava.apikit.tool.info.ApiClassInfo;
import top.rxjava.apikit.tool.info.ApiMethodInfo;
import top.rxjava.apikit.tool.utils.HttlUtils;
import top.rxjava.apikit.tool.wrapper.ApidocApiWrapper;
import top.rxjava.apikit.tool.wrapper.BuilderWrapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author happy
 * 抽象类：Httl模板引擎生成器
 */
abstract class AbstractHttlGenerator extends AbstractGenerator {
    private static final Logger log = LoggerFactory.getLogger(AbstractHttlGenerator.class);

    /**
     * 执行模块文件生成
     */
    void executeModule(BuilderWrapper<?> wrapper, String httlPath, File file) throws Exception {
        log.info("开始生成文件:{}, httlPath:{}", file.getAbsolutePath(), httlPath);
        Map<String, Object> params = new HashMap<>(0);
        params.put("classInfo", wrapper.getClassInfo());
        params.put("wrapper", wrapper);
        HttlUtils.renderFile(file, params, httlPath);
        log.info("结束生成文件:{}, httlPath:{}", file.getAbsolutePath(), httlPath);
    }

    /**
     * 执行Apidoc文件生成
     */
    String executeApidocContent(ApidocApiWrapper wrapper, ApiMethodInfo apiMethodInfo, String httlPath) throws Exception {
        log.info("开始生成字符串, httlPath:{}", httlPath);
        Map<String, Object> params = new HashMap<>(0);
        params.put("classInfo", wrapper.getClassInfo());
        params.put("wrapper", wrapper);
        params.put("method", apiMethodInfo);
        String content = HttlUtils.renderToString(params, httlPath);
        log.info("结束生成字符串:{}, httlPath:{}", content, httlPath);
        return content;
    }

    void execute(Map<String, Object> parameters, String httlPath, File file) throws Exception {
        log.info("开始生成文件:{}, httlPath:{}", file.getAbsolutePath(), httlPath);
        HttlUtils.renderFile(file, parameters, httlPath);
        log.info("结束生成文件:{}, httlPath:{}", file.getAbsolutePath(), httlPath);
    }
}
