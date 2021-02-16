package org.rxjava.webflux.core.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import org.rxjava.webflux.core.status.EntityStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

import static org.springframework.data.mongodb.core.index.IndexDirection.DESCENDING;

/**
 * @author happy 2019/10/20 02:10
 */
@Getter
@Setter
@ToString
@Document
public class CommonEntity {
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
