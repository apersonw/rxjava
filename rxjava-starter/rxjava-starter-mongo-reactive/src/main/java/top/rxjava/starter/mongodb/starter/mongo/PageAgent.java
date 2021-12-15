package top.rxjava.starter.mongodb.starter.mongo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Mono分页
 * @author happy
 */
public class PageAgent<T> {
    private final ReactiveMongoTemplate reactiveMongoTemplate;
    private final Class<T> clazz;

    public PageAgent(ReactiveMongoTemplate reactiveMongoTemplate, Class<T> clazz) {
        this.reactiveMongoTemplate = reactiveMongoTemplate;
        this.clazz = clazz;
    }

    public Mono<Page<T>> findPage(Query query, Pageable pageable) {
        return reactiveMongoTemplate.count(query, clazz).flatMap(num -> {
            if (0 == num) {
                return Mono.just(new ArrayList<T>()).map(getPageFunction(pageable, num));
            }
            return reactiveMongoTemplate
                    .find(query.with(pageable), clazz)
                    .collectList()
                    .map(getPageFunction(pageable, num));
        });
    }

    private Function<List<T>, PageImpl<T>> getPageFunction(Pageable pageable, Long num) {
        return list -> new PageImpl<>(list, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), num);
    }
}
