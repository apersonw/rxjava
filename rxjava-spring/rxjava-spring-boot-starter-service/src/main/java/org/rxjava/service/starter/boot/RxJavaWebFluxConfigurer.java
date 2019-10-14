package org.rxjava.service.starter.boot;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.rxjava.common.core.utils.JavaTimeModuleUtils;
import org.rxjava.common.core.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.text.SimpleDateFormat;

/**
 * @author happy 2019-05-13 01:30
 * WebFluxConfigurer
 */
public class RxJavaWebFluxConfigurer implements WebFluxConfigurer {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Redis Bean
     */
    @Bean
    @Primary
    ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisTemplate<>(factory, RedisSerializationContext.string());
    }

    /**
     * 配置ObjectMapper
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = JsonUtils.create();
        //字段值为null则不输出
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //添加json日期时间序列化和反序列化格式支持
        JavaTimeModuleUtils.addAllFormatter();

        //注册模块
        objectMapper
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(JavaTimeModuleUtils.getJavaTimeModule());

        //配置SimpleDateFormat格式为日期时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(JavaTimeModuleUtils.getDATE_TIME_FORMAT());
        simpleDateFormat.setLenient(false);
        objectMapper.setDateFormat(simpleDateFormat);

        return objectMapper;
    }

    /**
     * 配置json序列化和反序列化
     */
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
        configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
    }
}
