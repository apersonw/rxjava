package top.rxjava.starter.webflux.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import top.rxjava.common.utils.JavaTimeModuleUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.reactive.config.WebFluxConfigurer;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * WebFluxConfigurer
 *
 * @author happy
 */
public class RxjavaWebFluxConfigurer implements WebFluxConfigurer {

    private final ObjectMapper objectMapper;

    public RxjavaWebFluxConfigurer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
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

    /**
     * 参数格式转换服务
     */
    @Bean
    public FormattingConversionService formattingConversionService() {
        DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService(false);
        conversionService.addFormatterForFieldAnnotation(new NumberFormatAnnotationFormatterFactory());

        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern(JavaTimeModuleUtils.getDATE_FORMAT()));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern(JavaTimeModuleUtils.getDATE_TIME_FORMAT()));
        registrar.setTimeFormatter(DateTimeFormatter.ofPattern(JavaTimeModuleUtils.getTIME_FORMAT()));
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
        public LocalDateTime convert(String text) {
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
