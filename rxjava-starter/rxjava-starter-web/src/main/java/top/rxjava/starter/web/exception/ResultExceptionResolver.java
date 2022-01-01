package top.rxjava.starter.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutionException;

/**
 * 异常结果解析器
 */
@Slf4j
public class ResultExceptionResolver extends DefaultHandlerExceptionResolver implements MessageSourceAware {
    private MessageSourceAccessor messageAccessor;

    public ResultExceptionResolver() {
        this.setOrder(-2147483648);
    }

    @Override
    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (handler instanceof HandlerMethod) {
            ModelAndView modelAndView = this.handlerJson(ex);
            log.error("处理错误结果:{}", modelAndView);
            return modelAndView;
        } else {
            if (ex instanceof HttpRequestMethodNotSupportedException) {
                log.error("处理错误:{},{}", ex.getMessage(), ex.getClass().getName());
            } else {
                log.error("处理错误:{},{}", ex.getMessage(), ex.getClass().getName(), ex);
            }
            return this.noJsonResolveException(request, response, handler, ex);
        }
    }

    private ModelAndView handlerJson(Throwable ex) {
        if (ex instanceof ExecutionException) {
            ex = ex.getCause();
        }

        if (ex instanceof BindException) {
            BindingResult bindingResult = ((BindException)ex).getBindingResult();
            //Result result = ResultUtils.transform(bindingResult, this.messageAccessor);
            ModelAndView modelAndView = new ModelAndView();
            //modelAndView.addObject("result", result);
            //modelAndView.addObject("bindingResult", bindingResult);
            return modelAndView;
        } else {
            ModelAndView modelAndView=new ModelAndView();
            //if (ex instanceof I18nValidationException) {
            //    I18nResult i18nResult = ((I18nValidationException)ex).getI18nResult();
            //    ResultUtils.handleI18n(i18nResult, this.messageAccessor);
            //    modelAndView = new ModelAndView();
            //    modelAndView.addObject("result", i18nResult);
            //    modelAndView.addObject("i18nResult", i18nResult);
            //    return modelAndView;
            //} else {
            //    String message;
            //    if (ex instanceof AccountRuntimeException) {
            //        message = this.messageAccessor.getMessage("server.error", new Object[]{ex.getMessage()});
            //        log.error(message);
            //        modelAndView = new ModelAndView();
            //        modelAndView.addObject("result", Result.createError(-1, message));
            //        return modelAndView;
            //    } else {
            //        message = this.messageAccessor.getMessage("server.error", new Object[]{ex.getMessage()});
            //        log.error(message, ex);
            //        modelAndView = new ModelAndView();
            //        modelAndView.addObject("result", Result.createError(2, message));
            //        return modelAndView;
            //    }
            //}
            modelAndView.addObject("result", "error");
            return modelAndView;
        }
    }

    protected ModelAndView noJsonResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        return super.doResolveException(request, response, handler, ex);
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messageAccessor = new MessageSourceAccessor(messageSource);
    }
}
