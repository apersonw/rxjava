package org.rxjava.projectkit.plugin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.type.TypeFactory;
import lombok.extern.slf4j.Slf4j;
import org.rxjava.projectkit.plugin.bean.ProjectTask;

/**
 * @author happy
 */
@Slf4j
public class GenerateUtils {
    public static <T> T deserialize(String json, Class<T> valueType) {
        try {
            ObjectMapper objectMapper = new ObjectMapper().enableDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
            TypeFactory tf = TypeFactory.defaultInstance()
                    .withClassLoader(GenerateUtils.class.getClassLoader());
            objectMapper.setTypeFactory(tf);
            return objectMapper.readValue(json, valueType);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static void generate(String groupJson, String sourcePath, String[] srcPaths) {
        generate(sourcePath, srcPaths);
    }

    public static void generate(String javaSourcePath, String[] srcPaths) {

    }

    //根据任务类型创建生成器
    private static ProjectGenerator createGenerator(ProjectTask task) {
        return new ProjectGenerator();
    }
}
