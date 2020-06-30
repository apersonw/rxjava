package org.rxjava.service.starter.boot;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.rxjava.common.core.http.WebHttp;
import org.rxjava.common.core.utils.JavaTimeModuleUtils;
import org.rxjava.common.core.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.ReactiveMongoDatabaseFactory;
import org.springframework.data.mongodb.ReactiveMongoTransactionManager;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author happy 2019-05-13 01:30
 * WebFluxConfigurer
 */
public class RxJavaWebFluxConfigurer implements WebFluxConfigurer {

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 响应式mongo事务管理
     */
    @Bean
    ReactiveMongoTransactionManager reactiveTransactionManager(ReactiveMongoDatabaseFactory reactiveMongoDatabaseFactory) {
        return new ReactiveMongoTransactionManager(reactiveMongoDatabaseFactory);
    }

    /**
     * Redis Bean
     */
    @Bean
    @Primary
    ReactiveRedisTemplate<String, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        return new ReactiveRedisTemplate<>(factory, RedisSerializationContext.string());
    }

    /**
     * WebClient增加负载均衡
     */
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    /**
     * WebHttp Bean
     */
    @Bean
    public WebHttp webHttp(WebClient.Builder webClientBuilder) {
        return WebHttp.Builder.buildWebFlux(webClientBuilder);
    }

    /**
     * 使用指定的资源访问指定的名称，异常消息国际化处理
     */
    @Bean
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames(
                "classpath:exceptions/exception",
                "classpath:defaultExceptions/exception"
        );
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setCacheSeconds(99999999);
        return messageSource;
    }

    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        return JsonUtils.create();
    }

    @Bean
    public FormattingConversionService formattingConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);
        conversionService.addFormatterForFieldAnnotation(new NumberFormatAnnotationFormatterFactory());
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern(JavaTimeModuleUtils.getDATE_FORMAT()));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(JavaTimeModuleUtils.getDATE_TIME_FORMAT()));
        registrar.registerFormatters(conversionService);
        return conversionService;
    }

    /**
     * Model格式转换
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(StringToLocalDateTimeConverter.LOCALDATETIME);
    }

    public enum StringToLocalDateTimeConverter implements Converter<String, LocalDateTime> {
        /**
         * @see LocalDateTime
         */
        LOCALDATETIME;

        @Override
        public LocalDateTime convert(@NotNull String text) {
            return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(JavaTimeModuleUtils.getDATE_TIME_FORMAT()).withZone(ZoneId.systemDefault()));
        }
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
