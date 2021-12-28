package top.rxjava.starter.jpa.configuration;

import org.bson.types.ObjectId;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class CustomAuditorAware implements AuditorAware<ObjectId> {
    @Override
    public Optional<ObjectId> getCurrentAuditor() {
        return Optional.empty();
    }
}
