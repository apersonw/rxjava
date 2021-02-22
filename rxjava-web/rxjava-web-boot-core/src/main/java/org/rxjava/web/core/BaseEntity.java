package org.rxjava.web.core;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.index.IndexDirection.DESCENDING;

/**
 * @author happy
 */
@Data
public abstract class BaseEntity {
    @Id
    private ObjectId id;

    @Version
    private long version;
    @CreatedBy
    private ObjectId createUserId;

    @LastModifiedBy
    private ObjectId lastModifyUserId;

    @CreatedDate
    @Indexed(direction = DESCENDING)
    private LocalDateTime createDateTime;

    @LastModifiedDate
    private LocalDateTime lastModifiedDateTime;
}
