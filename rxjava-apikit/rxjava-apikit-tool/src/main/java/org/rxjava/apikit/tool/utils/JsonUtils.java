package org.rxjava.apikit.tool.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.guava.GuavaModule;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @author happy
 */
public class JsonUtils {
    public static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.registerModule(new GuavaModule());
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        SimpleDateFormat dateFormat = new SimpleDateFormat(DateTimeUtils.FORMAT);
        dateFormat.setLenient(false);
        MAPPER.setDateFormat(dateFormat);
    }

    public static ObjectMapper getObjectMapper() {
        return MAPPER;
    }

    public static final JsonFactory JSON_FACTORY = new JsonFactory();

    /**
     * 不能处理复杂情况,和继承情况
     */
    public static String serialize(Object o) {
        try {
            if (o instanceof List) {
                return MAPPER.writeValueAsString(((List<?>) o).toArray());
            }
            return MAPPER.writeValueAsString(o);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 不能处理复杂情况,和继承情况
     */
    public static <T> T deserialize(String json, Class<T> valueType) {
        try {
            return MAPPER.readValue(json, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static <T> T deserialize(String json, Class<?> parentClass, Class<?>... elementClasses) {
        try {
            JavaType javaType = MAPPER.getTypeFactory().constructParametricType(
                    parentClass, elementClasses
            );
            return MAPPER.readValue(json, javaType);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 不能处理复杂情况,和继承情况
     * 对付一般的List,Class方式足够了
     */
    public static <T> T deserialize(String json, TypeReference<T> valueTypeRef) {
        try {
            return MAPPER.readValue(json, valueTypeRef);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static JsonNode deserialize(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public static ObjectMapper create(boolean isNoDefault) {
        return create(isNoDefault, DateTimeUtils.FORMAT);
    }

    public static ObjectMapper create(boolean isNoDefault, String dateFormat) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, true);
        if (isNoDefault) {
            mapper.setSerializationInclusion(JsonInclude.Include.NON_DEFAULT);
        }
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dateFormat);
        simpleDateFormat.setLenient(false);
        mapper.setDateFormat(simpleDateFormat);
        return mapper;
    }


}
