package org.rxjava.common.core.exception;

import org.apache.commons.lang3.ArrayUtils;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.stream.Stream;

/**
 * @author happy 2019-04-18 01:22
 * 登录信息异常错误
 */
public class LoginInfoException extends RuntimeException {

    private ErrorMessage errorMessage;

    public LoginInfoException(String message) {
        super(message);
    }

    private LoginInfoException(String[] codes, Object[] arguments, String defaultMessage, Throwable cause) {
        super(MessageFormat.format(
                "codes:{0},args:{1}, defaultMessage:{2}",
                ArrayUtils.toString(codes),
                ArrayUtils.toString(arguments),
                defaultMessage
        ), cause);
        this.errorMessage = new ErrorMessage(codes, arguments, defaultMessage);
    }

    public static LoginInfoException of(String message) {
        return new LoginInfoException(message);
    }

    private static LoginInfoException parseCode(String code, String... argsCode) {
        return new LoginInfoException(code, argsCode);
    }

    private LoginInfoException(String code, String... argsCodes) {
        this(new String[]{code}, Stream.of(argsCodes).map(DefaultError::new).toArray(DefaultError[]::new), null, null);
    }

    public static <T> Mono<T> mono(String code) {
        return Mono.error(parseCode(code));
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
