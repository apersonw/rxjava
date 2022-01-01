package top.rxjava.starter.web.configuration;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.DelegatingWebMvcConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import top.rxjava.common.utils.JsonUtils;
import top.rxjava.starter.web.converter.LocalDateTimeToStringConverter;
import top.rxjava.starter.web.converter.StringToLocalDateTimeConverter;
import top.rxjava.starter.web.exception.WebJsonResponseStatusExceptionHandler;

import java.util.List;

/**
 * @author happy
 */
public class RxjavaWebConfigurer extends DelegatingWebMvcConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        return JsonUtils.DEFAULT_MAPPER;
    }

    /**
     * 异常处理器(排在所有异常处理器最前面)
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public HandlerExceptionResolver webJsonResponseStatusExceptionHandler(
    ) {
        return new WebJsonResponseStatusExceptionHandler();
    }

    /**
     * 配置视图解析器
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        MappingJackson2JsonView mappingJackson2JsonView = new MappingJackson2JsonView();
        mappingJackson2JsonView.setEncoding(JsonEncoding.UTF8);
        mappingJackson2JsonView.setObjectMapper(JsonUtils.DEFAULT_MAPPER);
        registry.enableContentNegotiation(mappingJackson2JsonView);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToLocalDateTimeConverter());
        registry.addConverter(new LocalDateTimeToStringConverter());
    }

    /**
     * 配置消息转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setObjectMapper(JsonUtils.DEFAULT_MAPPER);
        converters.add(mappingJackson2HttpMessageConverter);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInfoHandlerInterceptor());
        super.addInterceptors(registry);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }
}
