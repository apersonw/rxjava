package top.rxjava.starter.bus.amqp;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 开启Bus
 * @author happy
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({BusAmqpConfiguration.class})
public @interface EnableBusAmqp {
}