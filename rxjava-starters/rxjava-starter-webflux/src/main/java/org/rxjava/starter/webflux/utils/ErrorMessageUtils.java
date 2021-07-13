package org.rxjava.starter.webflux.utils;

import org.rxjava.starter.webflux.exception.ErrorMessage;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.CollectionUtils;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author happy 2019-04-18 01:25
 * 错误消息帮助类
 */
public class ErrorMessageUtils implements Serializable {
    public static void handlerI18n(ErrorMessage errorMessage, MessageSourceAccessor messageSourceAccessor) {
        if (errorMessage.getMessage() == null) {
            errorMessage.setMessage(messageSourceAccessor.getMessage(errorMessage));
        }

        if (!CollectionUtils.isEmpty(errorMessage.getErrors())) {
            errorMessage.getErrors().forEach(fieldError -> {
                if (fieldError.getMessage() == null) {
                    fieldError.setMessage(messageSourceAccessor.getMessage(fieldError));
                }
            });
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
    @Serial
    private Object readResolve() {
        return LazyHolder.lazy();
    }
}
