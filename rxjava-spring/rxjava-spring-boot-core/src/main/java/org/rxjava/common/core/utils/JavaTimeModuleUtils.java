package org.rxjava.common.core.utils;

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
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * 日期时间模块帮助类
 */
public class JavaTimeModuleUtils {
    @Getter
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";
    @Getter
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    @Getter
    private static final String TIME_FORMAT = "HH:mm:ss.SSS";
    @Getter
    private static JavaTimeModule javaTimeModule = new JavaTimeModule();

    /**
     * 禁止实例化
     */
    private JavaTimeModuleUtils() {
    }

    /**
     * 添加日期时间格式支持
     */
    public static void addDateTimeFormatter() {
        DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withZone(ZoneId.systemDefault());
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(dataTimeFormatter));
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(dataTimeFormatter));
    }

    /**
     * 添加日期格式支持
     */
    public static void addDateFormatter() {
        DateTimeFormatter dataFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT).withZone(ZoneId.systemDefault());
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(dataFormatter));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(dataFormatter));
    }

    /**
     * 添加时间格式支持
     */
    public static void addTimeFormatter() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(TIME_FORMAT).withZone(ZoneId.systemDefault());
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(timeFormatter));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(timeFormatter));
    }

    /**
     * 添加Instant格式支持
     */
    public static void addInstantFormatter(){
        DateTimeFormatter dataTimeFormatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT).withZone(ZoneId.systemDefault());
        javaTimeModule.addDeserializer(Instant.class, new JsonDeserializer<Instant>() {
            @Override
            public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                return Instant.from(dataTimeFormatter.parse(jsonParser.getText()));
            }
        });
        javaTimeModule.addSerializer(Instant.class, new JsonSerializer<Instant>() {
            @Override
            public void serialize(Instant value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                String str = dataTimeFormatter.format(value);
                gen.writeString(str);
            }
        });
    }
}
