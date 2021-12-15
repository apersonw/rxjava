package top.rxjava.starter.webflux.exception;

import org.apache.commons.lang3.ArrayUtils;
import reactor.core.publisher.Mono;

import java.text.MessageFormat;
import java.util.stream.Stream;

/**
 * @author wugang
 */
public class JwtTokenParseException extends RuntimeException{
    private ErrorMessage errorMessage;

    public JwtTokenParseException(String message) {
        super(message);
    }

    private JwtTokenParseException(String[] codes, Object[] arguments, String defaultMessage, Throwable cause) {
        super(MessageFormat.format(
                "codes:{0},args:{1}, defaultMessage:{2}",
                ArrayUtils.toString(codes),
                ArrayUtils.toString(arguments),
                defaultMessage
        ), cause);
        this.errorMessage = new ErrorMessage(codes, arguments, defaultMessage);
    }

    public static JwtTokenParseException of(String message) {
        return parseCode(message);
    }

    private static JwtTokenParseException parseCode(String code, String... argsCode) {
        return new JwtTokenParseException(code, argsCode);
    }

    private JwtTokenParseException(String code, String... argsCodes) {
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
