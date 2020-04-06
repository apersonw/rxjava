package org.rxjava.common.core.exception;

import org.apache.commons.lang3.ArrayUtils;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.stream.Stream;

/**
 * 禁止异常
 */
public class ForbiddenException extends RuntimeException {
    private ErrorMessage errorMessage;

    public ForbiddenException(String message) {
        super(message);
    }

    private ForbiddenException(String[] codes, Object[] arguments, String defaultMessage, Throwable cause) {
        super(MessageFormat.format(
                "codes:{0},args:{1}, defaultMessage:{2}",
                ArrayUtils.toString(codes),
                ArrayUtils.toString(arguments),
                defaultMessage
        ), cause);
        this.errorMessage = new ErrorMessage(codes, arguments, defaultMessage);
    }

    public static ForbiddenException of(String message) {
        return new ForbiddenException(message);
    }

    private static ForbiddenException parseCode(String code, String... argsCode) {
        return new ForbiddenException(code, argsCode);
    }

    private ForbiddenException(String code, String... argsCodes) {
        this(new String[]{code}, Stream.of(argsCodes).map(DefaultError::new).toArray(DefaultError[]::new), null, null);
    }

    public static <T> Mono<T> mono(String code) {
        return Mono.error(parseCode(code));
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
