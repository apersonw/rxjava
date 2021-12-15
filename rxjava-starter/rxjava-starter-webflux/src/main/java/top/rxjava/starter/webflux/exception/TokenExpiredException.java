package top.rxjava.starter.webflux.exception;

import org.apache.commons.lang3.ArrayUtils;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.stream.Stream;

public class TokenExpiredException extends RuntimeException{
    private ErrorMessage errorMessage;

    public TokenExpiredException(String message) {
        super(message);
    }

    private TokenExpiredException(String[] codes, Object[] arguments, String defaultMessage, Throwable cause) {
        super(MessageFormat.format(
                "codes:{0},args:{1}, defaultMessage:{2}",
                ArrayUtils.toString(codes),
                ArrayUtils.toString(arguments),
                defaultMessage
        ), cause);
        this.errorMessage = new ErrorMessage(codes, arguments, defaultMessage);
    }

    public static TokenExpiredException of(String message) {
        return parseCode(message);
    }

    private static TokenExpiredException parseCode(String code, String... argsCode) {
        return new TokenExpiredException(code, argsCode);
    }

    private TokenExpiredException(String code, String... argsCodes) {
        this(new String[]{code}, Stream.of(argsCodes).map(DefaultError::new)
                .toArray(DefaultError[]::new), null, null);
    }

    public static <T> Mono<T> mono(String code) {
        return Mono.error(parseCode(code));
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }
}
