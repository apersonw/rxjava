package top.rxjava.common.core.exception;

import org.apache.commons.lang3.ArrayUtils;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.stream.Stream;

/**
 * 凭证异常
 */
public class TokenException extends RuntimeException{
    private ErrorMessage errorMessage;

    public TokenException(String message) {
        super(message);
    }

    private TokenException(String[] codes, Object[] arguments, String defaultMessage, Throwable cause) {
        super(MessageFormat.format(
                "codes:{0},args:{1}, defaultMessage:{2}",
                ArrayUtils.toString(codes),
                ArrayUtils.toString(arguments),
                defaultMessage
        ), cause);
        this.errorMessage = new ErrorMessage(codes, arguments, defaultMessage);
    }

    public static TokenException of(String message) {
        return new TokenException(message);
    }

    private static TokenException parseCode(String code, String... argsCode) {
        return new TokenException(code, argsCode);
    }

    private TokenException(String code, String... argsCodes) {
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
