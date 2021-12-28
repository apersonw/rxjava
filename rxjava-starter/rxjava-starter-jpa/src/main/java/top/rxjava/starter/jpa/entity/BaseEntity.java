package top.rxjava.starter.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

/**
 * @author happy
 */
@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GenericGenerator(name = "objectId", strategy = "top.rxjava.starter.jpa.configuration.ObjectIdGenerator")
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
    private LocalDateTime createDateTime;

    @LastModifiedDate
    private LocalDateTime lastModifiedDateTime;
}
