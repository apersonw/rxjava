package top.rxjava.starter.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import top.rxjava.common.utils.JsonUtils;
import top.rxjava.starter.web.exception.RxjavaWebConfigurer;

@Configuration
@Import({
        RxjavaWebConfigurer.class
})
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
public class WebAutoConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
        return JsonUtils.DEFAULT_MAPPER;
    }

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
}
