package top.rxjava.starter.mongodb.entity;

import org.springframework.data.annotation.*;
import top.rxjava.starter.mongodb.status.EntityStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.index.IndexDirection.DESCENDING;

/**
 * @author happy
 */
@Getter
@Setter
@ToString
@Document
public class BaseEntity {
    /**
     * 实体Id
     */
    @Id
    private ObjectId id;
    /**
     * 乐观锁
     */
    @Version
    private long version;
    /**
     * 实体状态
     */
    private EntityStatus entityStatus = EntityStatus.NORMAL;
    /**
     * 创建者用户Id
     */
    @CreatedBy
    private ObjectId createBy;
    /**
     * 最后修改者用户Id
     */
    @LastModifiedBy
    private ObjectId lastModifiedBy;
    /**
     * 创建日期
     */
    @CreatedDate
    @Indexed(direction = DESCENDING)
    private LocalDateTime createDate;
    /**
     * 更新日期
     */
    @LastModifiedDate
    private LocalDateTime updateDate;
}
