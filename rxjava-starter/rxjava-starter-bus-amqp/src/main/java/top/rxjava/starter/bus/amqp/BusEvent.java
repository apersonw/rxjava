package top.rxjava.starter.bus.amqp;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author happy
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusEvent {
    /**
     * 事件唯一Id，保证幂等
     */
    private String id;
    /**
     * 事件类型
     */
    private BusEventType type;
    /**
     * 时间戳
     */
    private long timestamp;
    /**
     * 微服务名称
     */
    private String serviceName;
    /**
     * 数据
     */
    private JsonNode data;
}
