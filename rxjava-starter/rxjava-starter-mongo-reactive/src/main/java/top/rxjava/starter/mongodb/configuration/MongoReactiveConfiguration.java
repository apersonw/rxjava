package top.rxjava.starter.mongodb.configuration;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.ReactiveAuditorAware;
import org.springframework.data.mongodb.config.EnableReactiveMongoAuditing;
import reactor.core.publisher.Mono;
import reactor.util.context.ContextView;

import java.util.function.Function;

/**
 * @author happy
 */
@EnableReactiveMongoAuditing
public class MongoReactiveConfiguration {
    private static final String TOKEN_USERID_INFO = "tokenUserId";

    @Bean
    ReactiveAuditorAware<ObjectId> reactiveAuditorAware() {
        return () -> Mono.deferContextual((Function<ContextView, Mono<ObjectId>>) contextView -> Mono.justOrEmpty(contextView.getOrEmpty(TOKEN_USERID_INFO)));
    }
}
