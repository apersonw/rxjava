package org.rxjava.third.tencent.weixin.miniapp.bean.express.request;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 包裹信息对象
 *
 * @author <a href="https://github.com/mr-xiaoyu">xiaoyu</a>
 * @since 2019-11-26
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxMaExpressOrderCargo implements Serializable {
    private static final long serialVersionUID = 6642536671375396150L;

    /**
     * 包裹数量
     * <p>
     * 是否必填： 是
     * 描述： 需要和detail_list size保持一致
     */
    private Integer count;
    /**
     * 包裹总重量
     * <p>
     * 是否必填： 是
     * 描述： 单位是千克(kg)
     */
    private Integer weight;

    /**
     * 包裹长度
     * <p>
     * 是否必填： 是
     * 描述： 单位是厘米(cm)
     */
    @SerializedName("space_x")
    private Integer spaceLength;

    /**
     * 包裹宽度
     * <p>
     * 是否必填： 是
     * 描述： 单位是厘米(cm)
     */
    @SerializedName("space_y")
    private Integer spaceWidth;

    /**
     * 包裹高度
     * <p>
     * 是否必填： 是
     * 描述： 单位是厘米(cm)
     */
    @SerializedName("space_z")
    private Integer spaceHeight;

    /**
     * 包裹中商品详情列表
     * <p>
     * 是否必填： 是
     */
    @SerializedName("detail_list")
    private List<WxMaExpressOrderCargoDetail> detailList;
}
