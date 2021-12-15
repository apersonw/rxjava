package top.rxjava.common.utils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.Getter;

import java.io.IOException;
import java.io.Serializable;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间模块帮助类
 *
 * @author happy
 */
public class JavaTimeModuleUtils implements Serializable {
    @Getter
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    @Getter
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    @Getter
    private static final String TIME_FORMAT = "HH:mm:ss.SSS";
    @Getter
    private static final JavaTimeModule JAVA_TIME_MODULE = new JavaTimeModule();

    private JavaTimeModuleUtils() {
        throw new RuntimeException("禁止反射破坏单例");
    }

    public static JavaTimeModuleUtils getInstance() {
        return LazyHolder.lazy();
    }

    /**
     * 懒加载
     */
    private static class LazyHolder {
        private static JavaTimeModuleUtils lazy() {
            return new JavaTimeModuleUtils();
        }
    }

    /**
     * 禁止序列化破坏单例
     */
    private Object readResolve() {
        return LazyHolder.lazy();
    }

    /**
     * 添加日期时间格式解析支持
     */
    public static void addAllFormatter() {
        addTimeFormatter();
        addInstantFormatter();
        addDateTimeFormatter();
        addDateFormatter();
    }

    /**
     * 添加日期时间格式支持
     */
    private static void addDateTimeFormatter() {
        DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withZone(ZoneId.systemDefault());
        JAVA_TIME_MODULE.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dataTimeFormatter));
        JAVA_TIME_MODULE.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dataTimeFormatter));
    }

    /**
     * 添加日期格式支持
     */
    private static void addDateFormatter() {
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZoneId.systemDefault());
        JAVA_TIME_MODULE.addDeserializer(LocalDate.class, new LocalDateDeserializer(dataFormatter));
        JAVA_TIME_MODULE.addSerializer(LocalDate.class, new LocalDateSerializer(dataFormatter));
    }

    /**
     * 添加时间格式支持
     */
    private static void addTimeFormatter() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT).withZone(ZoneId.systemDefault());
        JAVA_TIME_MODULE.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        JAVA_TIME_MODULE.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
    }

    /**
     * 添加Instant格式支持
     */
    private static void addInstantFormatter() {
        DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withZone(ZoneId.systemDefault());
        JAVA_TIME_MODULE.addDeserializer(Instant.class, new JsonDeserializer<>() {
            @Override
            public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return Instant.from(dataTimeFormatter.parse(jsonParser.getText()));
            }
        });
        JAVA_TIME_MODULE.addSerializer(Instant.class, new JsonSerializer<>() {
            @Override
            public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                String str = dataTimeFormatter.format(value);
                gen.writeString(str);
            }
        });
    }
}
