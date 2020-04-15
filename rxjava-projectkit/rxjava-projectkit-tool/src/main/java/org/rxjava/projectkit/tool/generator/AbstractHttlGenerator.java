package org.rxjava.projectkit.tool.generator;

import lombok.extern.slf4j.Slf4j;
import org.rxjava.projectkit.tool.utils.HttlUtils;
import org.rxjava.projectkit.tool.wrapper.ProjectWrapper;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @author happy
 * 抽象类：Httl模板引擎生成器
 */
@Slf4j
abstract class AbstractHttlGenerator {
    /**
     * 执行模块文件生成
     */
    void executeModule(ProjectWrapper wrapper, String templPath, File file) throws Exception {
        log.info("开始生成文件:{}, templ:{}", file.getAbsolutePath(), templPath);
        Map<String, Object> params = new HashMap<>();
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
