package org.rxjava.common.core.exception;

import org.apache.commons.lang3.ArrayUtils;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.stream.Stream;

/**
 * @author happy 2019-04-18 01:22
 */
public class LoginRuntimeException extends RuntimeException {

    private ErrorMessage errorMessage;

    public LoginRuntimeException(String message) {
        super(message);
    }

    private LoginRuntimeException(String[] codes, Object[] arguments, String defaultMessage, Throwable cause) {
        super(MessageFormat.format(
                "codes:{0},args:{1}, defaultMessage:{2}",
                ArrayUtils.toString(codes),
                ArrayUtils.toString(arguments),
                defaultMessage
        ), cause);
        this.errorMessage = new ErrorMessage(codes, arguments, defaultMessage);
    }

    public static LoginRuntimeException of(String message) {
        return new LoginRuntimeException(message);
    }

    private static LoginRuntimeException parseCode(String code, String... argsCode) {
        return new LoginRuntimeException(code, argsCode);
    }

    private LoginRuntimeException(String code, String... argsCodes) {
        this(new String[]{code}, Stream.of(argsCodes).map(DefaultError::new).toArray(DefaultError[]::new), null, null);
    }

    public static <T> Mono<T> mono(String code) {
        return Mono.error(parseCode(code));
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
