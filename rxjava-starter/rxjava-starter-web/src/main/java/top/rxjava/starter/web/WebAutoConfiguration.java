package top.rxjava.starter.web;

import org.bson.types.ObjectId;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.Ordered;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import top.rxjava.starter.jpa.configuration.CustomAuditorAware;
import top.rxjava.starter.web.configuration.RxjavaWebConfigurer;

@Configuration
@Import({
        RxjavaWebConfigurer.class
})
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@EnableJpaAuditing
public class WebAutoConfiguration {

    @Bean
    public AuditorAware<ObjectId> customAuditorAware() {
        return new CustomAuditorAware();
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
