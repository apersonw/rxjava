package org.rxjava.webflux.service.starter.boot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.mapping.context.MappingContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MongoConverter;
import org.springframework.data.mongodb.core.index.IndexOperations;
import org.springframework.data.mongodb.core.index.MongoPersistentEntityIndexResolver;
import org.springframework.data.mongodb.core.mapping.*;

/**
 * @author happy
 */
@Slf4j
public class CustomMongoConfiguration {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private MongoConverter mongoConverter;

    /**
     * 初始化索引在应用启动后
     */
    @EventListener(ApplicationReadyEvent.class)
    public void initIndicesAfterStartup() {
        log.info("Mongo initIndicesAfterStartup init");
        long init = System.currentTimeMillis();
        MappingContext<? extends MongoPersistentEntity<?>, MongoPersistentProperty> mappingContext = mongoConverter.getMappingContext();
        if (mappingContext instanceof MongoMappingContext) {
            MongoMappingContext mongoMappingContext = (MongoMappingContext) mappingContext;
            for (BasicMongoPersistentEntity<?> persistentEntity : mongoMappingContext.getPersistentEntities()) {
                Class<?> clazz = persistentEntity.getType();
                if (clazz.isAnnotationPresent(Document.class)) {
                    MongoPersistentEntityIndexResolver resolver = new MongoPersistentEntityIndexResolver(mongoMappingContext);
                    IndexOperations indexOperations = mongoTemplate.indexOps(clazz);
                    resolver.resolveIndexFor(clazz).forEach(indexOperations::ensureIndex);
                }
            }
        }
        log.info("Mongo initIndicesAfterStartup take: {}", (System.currentTimeMillis() - init));
    }
}
