package org.rxjava.third.weixin.miniapp.bean.cloud;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * 云开发数据库查询记录接口请求结果.
 */
@Data
public class WxCloudDatabaseQueryResult implements Serializable {
    private static final long serialVersionUID = 8291820029137872536L;

    /**
     * 分页信息
     */
    private Pager pager;

    /**
     * 查询结果
     */
    private String[] data;

    @Data
    public static class Pager implements Serializable {
        private static final long serialVersionUID = 8556239063823985674L;

        /**
         * Offset	number	偏移
         */
        @SerializedName("Offset")
        private Long offset;

        /**
         * Limit	number	单次查询限制
         */
        @SerializedName("Limit")
        private Long limit;

        /**
         * Total	number	符合查询条件的记录总数
         */
        @SerializedName("Total")
        private Long total;

    }
}
