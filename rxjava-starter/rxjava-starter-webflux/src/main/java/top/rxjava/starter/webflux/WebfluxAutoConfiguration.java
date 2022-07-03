 package top.rxjava.starter.webflux;

 import com.fasterxml.jackson.databind.ObjectMapper;
 import org.springframework.boot.autoconfigure.AutoConfigureOrder;
 import org.springframework.context.annotation.Bean;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.context.annotation.Import;
 import org.springframework.core.Ordered;
 import top.rxjava.common.utils.JsonUtils;
 import top.rxjava.starter.webflux.configuration.ReactiveTokenInfoFilter;
 import top.rxjava.starter.webflux.configuration.RxjavaWebFluxConfigurer;
 import top.rxjava.starter.webflux.configuration.ServiceDelegatingWebFluxConfiguration;

 /**
  * @author happy
  */
 @Configuration
 @Import({
         ServiceDelegatingWebFluxConfiguration.class,
         RxjavaWebFluxConfigurer.class,
         ReactiveTokenInfoFilter.class
 })
 @AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
 public class WebfluxAutoConfiguration {
     @Bean
     public ObjectMapper objectMapper() {
         return JsonUtils.DEFAULT_MAPPER;
     }
 }
