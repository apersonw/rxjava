package org.rxjava.starter.bus.amqp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author happy 2019-06-04 10:28
 * 总线发射
 */
@Slf4j
public class BusEmitter {

    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public Mono<Boolean> emit(BusEventType type, Consumer<ObjectNode> consumer) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        consumer.accept(objectNode);
        return emit(type, objectNode);
    }

    public Mono<Boolean> emit(BusEventType busEventType, JsonNode data) {
        BusEvent busEvent = BusEvent.builder()
                .type(busEventType)
                .data(data)
                .timestamp(System.currentTimeMillis())
                .service(applicationName)
                .id(UUID.randomUUID().toString().replace("-", ""))
                .build();
        return Mono
                .fromCallable(() -> {
                    try {
                        log.info("busEmitter:{}", busEvent);
                        amqpTemplate.convertAndSend(
                                BusAmqpConfiguration.FANOUT_EXCHANGE,
                                "",
                                objectMapper.writeValueAsString(busEvent)
                        );
                        return true;
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .publishOn(Schedulers.elastic());
    }

    /**
     * 延迟发射
     */
    public Mono<Boolean> delayEmit(BusEventType type, Consumer<ObjectNode> consumer, int secend) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        consumer.accept(objectNode);
        return delayEmit(type, objectNode, secend);
    }

    /**
     * 延迟发射
     */
    public Mono<Boolean> delayEmit(BusEventType busEventType, JsonNode data, int secend) {
        BusEvent busEvent = BusEvent.builder()
                .type(busEventType)
                .data(data)
                .timestamp(System.currentTimeMillis())
                .service(applicationName)
                .id(UUID.randomUUID().toString().replace("-", ""))
                .build();
        return Mono
                .fromCallable(() -> {
                    try {
                        log.info("busEmitter:{}", busEvent);
                        amqpTemplate.convertAndSend(
                                BusAmqpConfiguration.QUEUE_NAME_DELAY_PREFIX + applicationName,
                                objectMapper.writeValueAsString(busEvent),
                                message -> {
                                    MessageProperties messageProperties = message.getMessageProperties();
                                    if (secend > 0) {
                                        int expiratime = secend * 1000;
                                        messageProperties.setExpiration(String.valueOf(expiratime));
                                    }
                                    return message;
                                }
                        );
                        return true;
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .publishOn(Schedulers.elastic());
    }
}
