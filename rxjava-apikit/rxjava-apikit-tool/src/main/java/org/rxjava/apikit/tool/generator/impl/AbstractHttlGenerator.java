package org.rxjava.apikit.tool.generator.impl;

import org.rxjava.apikit.tool.generator.AbstractGenerator;
import org.rxjava.apikit.tool.utils.HttlUtils;
import org.rxjava.apikit.tool.wrapper.BuilderWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    void executeModule(BuilderWrapper wrapper, String templPath, File file) throws Exception {
        log.info("开始生成文件:{}, templ:{}", file.getAbsolutePath(), templPath);
        Map<String, Object> params = new HashMap<>();
        params.put("classInfo", wrapper.getClassInfo());
        params.put("wrapper", wrapper);
        HttlUtils.renderFile(file, params, templPath);
        log.info("结束生成文件:{}, templ:{}", file.getAbsolutePath(), templPath);
    }

    void execute(Map<String, Object> parameters, String templPath, File file) throws Exception {

        log.info("开始生成文件:{}, templ:{}", file.getAbsolutePath(), templPath);
        HttlUtils.renderFile(file, parameters, templPath);
        log.info("结束生成文件:{}, templ:{}", file.getAbsolutePath(), templPath);
    }
}
