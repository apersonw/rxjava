package top.rxjava.common.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * JSON帮助类
 *
 * @author happy
 */
public class JsonUtils {
    public static final ObjectMapper DEFAULT_MAPPER = JsonUtils.create();

    private JsonUtils() {
        throw new RuntimeException("禁止反射破坏单例");
    }

    private static ObjectMapper create() {
        ObjectMapper objectMapper = new ObjectMapper();
        //字段值为null则不输出
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //配置多余字段错误不提示
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        //添加json日期时间序列化和反序列化格式支持
        JavaTimeModuleUtils.addAllFormatter();

        //注册模块
        objectMapper
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(JavaTimeModuleUtils.getJAVA_TIME_MODULE());

        //配置SimpleDateFormat格式为日期时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(JavaTimeModuleUtils.getDATE_TIME_FORMAT());
        simpleDateFormat.setLenient(false);
        objectMapper.setDateFormat(simpleDateFormat);

        SimpleModule module = new SimpleModule("CustomModel", Version.unknownVersion());

        module.addDeserializer(ObjectId.class, new JsonDeserializer<>() {
            @Override
            public ObjectId deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return new ObjectId(jsonParser.getText());
            }
        });

        module.addSerializer(ObjectId.class, new JsonSerializer<>() {
            @Override
            public void serialize(ObjectId value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(value.toHexString());
            }
        });

        objectMapper.registerModule(module);
        return objectMapper;
    }

    /**
     * 反序列化Json字符串
     */
    public static <T> T deserialize(String json, Class<T> valueType) {
        try {
            return DEFAULT_MAPPER.readValue(json, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static String serialize(Object o) {
        try {
            if (o instanceof List) {
                return DEFAULT_MAPPER.writeValueAsString(((List<?>) o).toArray());
            }
            return DEFAULT_MAPPER.writeValueAsString(o);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static List<Object> readList(String json) {
        try {

            return DEFAULT_MAPPER.readValue(json, new TypeReference<>() {
            });

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> List<T> parseArray(String content, Class<T> clazz) {
        if (StringUtils.isEmpty(content)) {
            return new ArrayList<>();
        }
        try {
            return DEFAULT_MAPPER.readValue(content, DEFAULT_MAPPER.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static JsonNode readTree(String content) throws JsonProcessingException {
        return DEFAULT_MAPPER.readTree(content);
    }

    public static Object read(String json, Class<?> clazz) {
        try {
            return DEFAULT_MAPPER.readValue(json, clazz);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
