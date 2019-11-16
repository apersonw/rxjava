package org.rxjava.common.bus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
public class BusEmitter {
    private static final Logger log = LogManager.getLogger();

    @Value("${spring.application.name}")
    private String applicationName;
    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    private Mono<Boolean> emit(BusEventType type, Consumer<ObjectNode> consumer) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        consumer.accept(objectNode);
        return emit(type, objectNode);
    }

    private Mono<Boolean> emit(BusEventType type, JsonNode data) {
        BusEvent busEvent = BusEvent.builder()
                .type(type)
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
                                RxBusConfiguration.EXCHANGE,
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
}
