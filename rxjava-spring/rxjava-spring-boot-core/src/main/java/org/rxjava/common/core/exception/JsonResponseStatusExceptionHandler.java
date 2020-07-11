package org.rxjava.common.core.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.rxjava.common.core.utils.ErrorMessageUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author happy 2019-04-18 00:47
 * Json响应状态异常处理器
 */
public class JsonResponseStatusExceptionHandler extends WebFluxResponseStatusExceptionHandler implements ErrorWebExceptionHandler, InitializingBean, MessageSourceAware {
    private static final Logger log = LogManager.getLogger();
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();
    /**
     * 设置视图解析器为空
     */
    private List<ViewResolver> viewResolvers = Collections.emptyList();
    private MessageSourceAccessor messageAccessor;

    /**
     * 解析并处理异常消息
     */
    @NotNull
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, @NotNull Throwable throwable) {
        //检查响应是否待发
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(throwable);
        }

        ErrorMessage errorMessage = toErrorMessage(exchange, throwable);

        return ServerResponse.status(errorMessage.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(errorMessage))
                .flatMap(response -> write(exchange, response));
    }

    private Mono<Void> write(ServerWebExchange exchange,
                             ServerResponse response) {
        exchange.getResponse().getHeaders()
                .setContentType(response.headers().getContentType());
        return response.writeTo(exchange, serverResponseContext);
    }

    /**
     * 将异常转为ErrorMessage包装
     */
    private ErrorMessage toErrorMessage(ServerWebExchange exchange, Throwable throwable) {
        HttpStatus status;
        ErrorMessage errorMessage;
        ServerHttpRequest request = exchange.getRequest();

        //参数异常错误
        if (throwable instanceof WebExchangeBindException) {
            WebExchangeBindException webExchangeBindException = (WebExchangeBindException) throwable;
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorMessage = transform(webExchangeBindException.getBindingResult());

            log.info("WebExchangeBindException:", throwable);
        } else if (throwable instanceof ErrorMessageException) {
            ErrorMessageException errorMessageException = (ErrorMessageException) throwable;
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorMessage = errorMessageException.getErrorMessage();

            log.info("ErrorMessageException:", throwable);
        } else if (throwable instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) throwable;
            status = responseStatusException.getStatus();
            errorMessage = new ErrorMessage(responseStatusException.getReason());

            RequestPath path = request.getPath();
            log.info("http status:{},reason:{},path:{},method:{}", status, responseStatusException.getReason(), path, request.getMethodValue());
            log.debug("http status:{},reason:{}", status, responseStatusException.getReason(), throwable);
        } else if (throwable instanceof UnauthorizedException) {
            //未登陆错误
            status = HttpStatus.UNAUTHORIZED;
            errorMessage = new ErrorMessage("unauthorized");
            log.info("http status:{},reason:{}", status, "需要登陆");
            log.debug("http status:{},reason:{}", status, "需要登陆", throwable);
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            log.info("Error:", throwable);
            errorMessage = new ErrorMessage("server.error", (Object) throwable.getMessage());
        }
        errorMessage.setStatus(status.value());
        errorMessage.setTimestamp(LocalDateTime.now());

        errorMessage.setPath(request.getPath().pathWithinApplication().value());

        //处理消息国际化
        ErrorMessageUtils.handlerI18n(errorMessage, messageAccessor);
        return errorMessage;
    }

    private static ErrorMessage transform(BindingResult bindingResult) {
        ErrorMessage errorMessage = new ErrorMessage("server.validator");

        for (ObjectError error : bindingResult.getAllErrors()) {
            String key = (error instanceof FieldError
                    ? ((FieldError) error).getField()
                    : error.getObjectName());
            errorMessage.addFieldObjs(key, error.getCodes(), error.getArguments());
        }
        return errorMessage;
    }

    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }

    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    private final ServerResponse.Context serverResponseContext = new ServerResponse.Context() {
        @NotNull
        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return messageWriters;
        }

        @NotNull
        @Override
        public List<ViewResolver> viewResolvers() {
            return viewResolvers;
        }
    };

    @Override
    public void afterPropertiesSet() throws Exception {
        if (CollectionUtils.isEmpty(this.messageWriters)) {
            throw new IllegalArgumentException("Property 'messageWriters' is required");
        }
    }

    @Override
    public void setMessageSource(@NotNull MessageSource messageSource) {
        this.messageAccessor = new MessageSourceAccessor(messageSource, Locale.CHINA);
    }
}
