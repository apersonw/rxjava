package top.rxjava.common.utils;

import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.util.CollectionUtils;
import top.rxjava.common.core.exception.ErrorMessage;
import top.rxjava.common.core.exception.FieldError;

/**
 * @author happy
 * 错误消息帮助类
 */
public class ErrorMessageUtils {
    public static void handlerI18n(ErrorMessage errorMessage, MessageSourceAccessor messageSourceAccessor) {
        if (errorMessage.getMessage() == null) {
            errorMessage.setMessage(messageSourceAccessor.getMessage(errorMessage));
        }
        if (!CollectionUtils.isEmpty(errorMessage.getFieldErrors())) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < errorMessage.getFieldErrors().size(); i++) {
                FieldError fieldError = errorMessage.getFieldErrors().get(i);
                if (fieldError.getMessage() == null) {
                    fieldError.setMessage(messageSourceAccessor.getMessage(fieldError));
                }
                sb.append(fieldError.getMessage());
                if (i < errorMessage.getFieldErrors().size() - 1) {
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
}
