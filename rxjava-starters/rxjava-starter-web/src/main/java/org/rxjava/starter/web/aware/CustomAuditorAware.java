package org.rxjava.starter.web.aware;

import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class CustomAuditorAware implements AuditorAware<ObjectId> {
    @NotNull
    @Override
    public Optional<ObjectId> getCurrentAuditor() {
        return Optional.empty();
    }
}
