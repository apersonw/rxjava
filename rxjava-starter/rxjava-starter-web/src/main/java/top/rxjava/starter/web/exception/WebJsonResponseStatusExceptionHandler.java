package top.rxjava.starter.web.exception;

import org.springframework.beans.TypeMismatchException;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import top.rxjava.common.core.exception.ErrorMessage;
import top.rxjava.common.core.exception.ErrorMessageException;
import top.rxjava.common.core.exception.TokenException;
import top.rxjava.common.core.exception.UnauthorizedException;
import top.rxjava.common.utils.ErrorMessageUtils;
import top.rxjava.common.utils.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Locale;

public class WebJsonResponseStatusExceptionHandler implements HandlerExceptionResolver, MessageSourceAware {
    private MessageSourceAccessor messageAccessor;

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        response.setContentType("application/json;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            response.getWriter().print(JsonUtils.serialize(toErrorMessage(request, response, handler, ex)));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将异常转为ErrorMessage包装
     */
    private ErrorMessage toErrorMessage(HttpServletRequest request, HttpServletResponse response, Object handler, Exception exception) {
        HttpStatus status;
        ErrorMessage errorMessage = null;

        //参数异常错误
        if (exception instanceof WebExchangeBindException) {
            WebExchangeBindException webExchangeBindException = (WebExchangeBindException) exception;
            status = HttpStatus.UNPROCESSABLE_ENTITY;
        } else if (exception instanceof ErrorMessageException) {
            ErrorMessageException errorMessageException = (ErrorMessageException) exception;
            status = HttpStatus.UNPROCESSABLE_ENTITY;
            errorMessage = errorMessageException.getErrorMessage();
        } else if (exception instanceof ResponseStatusException) {
            ResponseStatusException responseStatusException = (ResponseStatusException) exception;
            status = responseStatusException.getStatus();
            String errorMessageStr;

            if (exception.getCause() instanceof TypeMismatchException) {
                TypeMismatchException typeMismatchException = (TypeMismatchException) exception.getCause();
                errorMessageStr = typeMismatchException.getLocalizedMessage();
            } else if (exception.getCause() instanceof DecodingException) {
                DecodingException decodingException = (DecodingException) exception.getCause();
                errorMessageStr = decodingException.getMessage();
            } else {
                errorMessageStr = responseStatusException.getReason();
            }
            errorMessage = new ErrorMessage(errorMessageStr);
            String path = request.getPathInfo();
        } else if (exception instanceof TokenException) {
            //Token过期异常
            status = HttpStatus.UNAUTHORIZED;
        } else if (exception instanceof UnauthorizedException) {
            //未登陆错误
            status = HttpStatus.UNAUTHORIZED;
            errorMessage = new ErrorMessage("unauthorized");
        } else {
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            errorMessage = new ErrorMessage("internal_server_error");
        }
        errorMessage.setStatus(status.value());
        errorMessage.setTimestamp(LocalDateTime.now());
        errorMessage.setPath(request.getPathInfo());
        //处理消息国际化
        ErrorMessageUtils.handlerI18n(errorMessage, messageAccessor);
        return errorMessage;
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageAccessor = new MessageSourceAccessor(messageSource, Locale.CHINA);
    }
}
