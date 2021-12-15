package top.rxjava.starter.webflux.utils;

import top.rxjava.starter.webflux.exception.ErrorMessage;
import top.rxjava.starter.webflux.exception.FieldError;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;

/**
 * 错误消息帮助类
 */
public class ErrorMessageUtils implements Serializable {
    public static void handlerI18n(ErrorMessage errorMessage, MessageSourceAccessor messageSourceAccessor) {
        if (errorMessage.getMessage() == null) {
            errorMessage.setMessage(messageSourceAccessor.getMessage(errorMessage));
        }
        if (!CollectionUtils.isEmpty(errorMessage.getErrors())) {
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<errorMessage.getErrors().size();i++){
                FieldError fieldError = errorMessage.getErrors().get(i);
                if (fieldError.getMessage() == null) {
                    fieldError.setMessage(messageSourceAccessor.getMessage(fieldError));
                }
                sb.append(fieldError.getMessage());
                if(i< errorMessage.getErrors().size()-1){
                    sb.append(",");
                }
            }
            errorMessage.setMessage(sb.toString());
//            errorMessage.getErrors().forEach(fieldError -> {
//                if (fieldError.getMessage() == null) {
//                    fieldError.setMessage(messageSourceAccessor.getMessage(fieldError));
//                }
//            });
        }
    }

    private ErrorMessageUtils() {
        throw new RuntimeException("禁止反射破坏单例");
    }

    public static ErrorMessageUtils getInstance() {
        return LazyHolder.lazy();
    }

    /**
     * 懒加载
     */
    private static class LazyHolder {
        private static ErrorMessageUtils lazy() {
            return new ErrorMessageUtils();
        }
    }

    /**
     * 禁止序列化破坏单例
     */
    private Object readResolve() {
        return LazyHolder.lazy();
    }
}
