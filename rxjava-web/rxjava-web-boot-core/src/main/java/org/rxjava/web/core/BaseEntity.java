package org.rxjava.web.core;

import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.*;

import java.time.LocalDateTime;

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
    private LocalDateTime createDateTime;

    @LastModifiedDate
    private LocalDateTime lastModifiedDateTime;
}
