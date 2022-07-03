package top.rxjava.starter.jpa.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.Version;
import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import top.rxjava.common.core.status.EntityStatus;

import javax.persistence.Id;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author happy
 */
@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseEntity {
    /**
     * 实体Id
     */
    @Id
    @GenericGenerator(name = "objectId", strategy = "top.rxjava.starter.jpa.configuration.ObjectIdGenerator")
    @GeneratedValue(generator = "objectId")
    @Column(length = 64)
    private String id;

    /**
     * 乐观锁
     */
    @Version
    private long version;

    /**
     * 实体状态
     */
    private EntityStatus entityStatus = EntityStatus.NORMAL;
    @CreatedBy
    @Column(length = 64)
    private String createdBy;

    @LastModifiedBy
    @Column(length = 64)
    private String lastModifiedBy;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDateTime;
}
