package top.rxjava.common.core.exception;

import lombok.Data;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.util.Assert;
import reactor.util.annotation.Nullable;

/**
 * @author happy
 */
@Data
public class FieldError implements MessageSourceResolvable {
    private String[] codes;
    private Object[] arguments;
    private String defaultMessage;
    private String field;
    private String message;
    private FieldError(String[] codes, Object[] arguments, String defaultMessage) {
        this.codes = codes;
        this.arguments = arguments;
        this.defaultMessage = defaultMessage;
    }

    /**
     * Create a new instance of the ObjectError class.
     *
     * @param field          the name of the affected object
     * @param defaultMessage the default message to be used to resolve this message
     */
    public FieldError(String field, String defaultMessage) {
        this(field, null, null, defaultMessage);
    }

    /**
     * Create a new instance of the ObjectError class.
     *
     * @param field          the name of the affected object
     * @param codes          the codes to be used to resolve this message
     * @param arguments      the array of arguments to be used to resolve this message
     * @param defaultMessage the default message to be used to resolve this message
     */
    public FieldError(
            String field,
            @Nullable String[] codes,
            @Nullable Object[] arguments,
            @Nullable String defaultMessage) {
        this(codes, arguments, defaultMessage);
        Assert.notNull(field, "Object name must not be null");
        this.field = field;
    }

    public FieldError(
            String field,
            @Nullable String[] codes,
            @Nullable Object[] arguments
    ) {
        this(codes, arguments, null);
        Assert.notNull(field, "Object name must not be null");
        this.field = field;
    }
}
