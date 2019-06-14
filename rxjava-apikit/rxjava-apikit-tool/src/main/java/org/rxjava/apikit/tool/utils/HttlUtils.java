package org.rxjava.apikit.tool.utils;

import httl.Engine;
import httl.Template;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.output.StringBuilderWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;
import java.util.Properties;

/**
 * Httl模版引擎帮助类
 */
public class HttlUtils {

    private static final Logger log = LoggerFactory.getLogger(HttlUtils.class);
    /**
     * 文件编码
     */
    private static final String FILE_ENCODING = "utf8";

    private static Engine getEngine() {
        return Inner.ENGINE;
    }


    private static class Inner {
        private static final Engine ENGINE;

        static {
            Properties configProperties = new Properties();
            try {
                configProperties.load(HttlUtils.class.getResourceAsStream("/org/rxjava/apikit/tool/httl.properties"));
                ENGINE = Engine.getEngine(configProperties);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 根据模板生成文件
     */
    public static void renderFile(File file, Map<String, Object> params, String templPath) throws IOException, ParseException {
        if (!file.getParentFile().exists()) {
            synchronized (HttlUtils.class) {
                if (!file.getParentFile().exists()) {
                    if (!file.getParentFile().mkdirs()) {
                        throw new RuntimeException("怎么。创建路径失败");
                    }
                }
            }
        }

        String code = renderToString(params, templPath);
        if (file.getName().endsWith(".java")) {
            String formatCode = JavaFileFormat.formatCode(code);
            writeStringToFile(file, formatCode);
            log.info("生成一个文件{}", file.getAbsolutePath());
        } else {
            writeStringToFile(file, code);
            log.info("生成一个文件{}", file.getAbsolutePath());
        }
    }

    private static void writeStringToFile(File file, String data) throws IOException {
        data = data.replaceAll("(\r\n|\r|\n|\n\r)", "\r\n");
        FileUtils.writeStringToFile(file, data, HttlUtils.FILE_ENCODING);
    }

    private static String renderToString(Map<String, Object> params, String templPath) throws IOException, ParseException {
        StringBuilderWriter writer = new StringBuilderWriter();
        Template template = getEngine().getTemplate(templPath);
        template.render(params, writer);
        log.info("生成一个文件到字符串");
        return writer.toString();
    }
}
