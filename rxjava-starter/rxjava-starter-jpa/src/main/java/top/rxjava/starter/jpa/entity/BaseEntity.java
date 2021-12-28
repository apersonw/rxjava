package top.rxjava.starter.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import top.rxjava.starter.jpa.status.EntityStatus;

import java.time.LocalDateTime;

/**
 * @author happy
 */
@Getter
@Setter
@ToString
public class BaseEntity {
    /**
     * 实体Id
     */
    private ObjectId id;
    /**
     * 乐观锁
     */
    private long version;
    /**
     * 实体状态
     */
    private EntityStatus entityStatus = EntityStatus.NORMAL;
    /**
     * 创建日期
     */
    private LocalDateTime createDate;
    /**
     * 更新日期
     */
    private LocalDateTime updateDate;
}
