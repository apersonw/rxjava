package top.rxjava.common.core.exception;

import lombok.Data;
import org.springframework.context.MessageSourceResolvable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author happy
 */
@Data
public class ErrorMessage implements MessageSourceResolvable {
    /**
     * 错误码
     */
    private String[] codes;
    /**
     * 消息解析参数数组
     */
    private Object[] arguments;
    /**
     * 默认消息
     */
    private String defaultMessage;
    /**
     * 请求路径
     */
    private String path;
    /**
     * 消息
     */
    private String message;
    /**
     * 状态码
     */
    private int status;
    /**
     * 发生时间
     */
    private LocalDateTime occurTime;
    /**
     * 字段错误
     */
    private List<FieldError> fieldErrors;
}
