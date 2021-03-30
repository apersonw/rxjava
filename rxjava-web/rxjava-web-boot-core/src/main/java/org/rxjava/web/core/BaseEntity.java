package org.rxjava.web.core;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.persistence.*;
import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.index.IndexDirection.DESCENDING;

/**
 * @author happy
 */
@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GenericGenerator(name = "objectId", strategy = "org.rxjava.web.core.config.ObjectIdGenerator")
    @GeneratedValue(generator = "objectId")
    @Column(length = 64)
    private String id;

    @Version
    private Long version;
    @CreatedBy
    @Column(length = 64)
    private String createUserId;

    @LastModifiedBy
    @Column(length = 64)
    private String lastModifyUserId;

    @CreatedDate
    @Indexed(direction = DESCENDING)
    private LocalDateTime createDateTime;

    @LastModifiedDate
    private LocalDateTime lastModifiedDateTime;
}
