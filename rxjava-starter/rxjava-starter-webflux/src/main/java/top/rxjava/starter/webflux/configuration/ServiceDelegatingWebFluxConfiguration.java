package top.rxjava.starter.webflux.configuration;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.DelegatingWebFluxConfiguration;
import org.springframework.web.reactive.result.method.annotation.ArgumentResolverConfigurer;
import org.springframework.web.reactive.result.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.WebExceptionHandler;
import top.rxjava.starter.webflux.exception.JsonResponseStatusExceptionHandler;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

/**
 * DelegatingWebFluxConfiguration
 * @author wugang
 */
public class ServiceDelegatingWebFluxConfiguration extends DelegatingWebFluxConfiguration {

    @NotNull
    @Override
    protected RequestMappingHandlerAdapter createRequestMappingHandlerAdapter() {
        return new SecurityRequestMappingHandlerAdapter();
    }

    /**
     * 异常处理器(排在所有异常处理器最前面)
     */
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public WebExceptionHandler jsonResponseStatusExceptionHandler(
            ObjectProvider<List<ViewResolver>> viewResolversProvider,
            ServerCodecConfigurer serverCodecConfigurer
    ) {
        JsonResponseStatusExceptionHandler handler = new JsonResponseStatusExceptionHandler();
        handler.setMessageWriters(serverCodecConfigurer.getWriters());
        handler.setViewResolvers(viewResolversProvider.getIfAvailable(Collections::emptyList));
        return handler;
    }

    /**
     * 自定义方法参数配置解析器
     */
    @Override
    public void configureArgumentResolvers(ArgumentResolverConfigurer configurer) {
        configurer.addCustomResolver(new LoginInfoArgumentResolver(webFluxAdapterRegistry()));
    }
}
