package org.rxjava.service.example.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 分组商品
 */
@Data
@Document
@CompoundIndexes(
        @CompoundIndex(
                unique = true,
                name = "groupId_goodsId",
                def = "{'groupId':1,'goodsId':1}"
        )
)
public class GroupGoods {
    @Id
    private String id;
    private String groupId;
    private String goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品封面图
     */
    private String coverImgUrl;
    /**
     * 售价
     */
    private long salePrice;
}
