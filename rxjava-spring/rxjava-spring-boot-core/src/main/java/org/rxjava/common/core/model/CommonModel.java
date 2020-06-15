package org.rxjava.common.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

/**
 * @author happy 2019/10/20 02:10
 */
@Getter
@Setter
@ToString
public class CommonModel {
    /**
     * 实体Id
     */
    private ObjectId id;
    /**
     * 创建日期
     */
    private LocalDateTime createDate;
}
