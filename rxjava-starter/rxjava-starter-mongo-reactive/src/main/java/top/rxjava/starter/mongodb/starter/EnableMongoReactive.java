package top.rxjava.starter.mongodb.starter;

import top.rxjava.starter.mongodb.starter.configuration.MongoReactiveConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author happy
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({MongoReactiveConfiguration.class})
public @interface EnableMongoReactive {
}
