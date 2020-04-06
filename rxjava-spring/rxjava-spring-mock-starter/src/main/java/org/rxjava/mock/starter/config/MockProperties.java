package org.rxjava.mock.starter.config;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(
        prefix = "mock"
)
@Getter
@Setter
public class MockProperties {
    private ObjectId userId;
}
