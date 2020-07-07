package org.rxjava.third.weixin.miniapp.bean.cloud;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 * 云开发数据库迁移状态查询结果.
 */
@Data
public class WxCloudCloudDatabaseMigrateQueryInfoResult implements Serializable {
    private static final long serialVersionUID = 2014197503355968243L;

    /**
     * status	string	导出状态
     */
    private String status;

    /**
     * record_success	number	导出成功记录数
     */
    @SerializedName("record_success")
    private Integer recordSuccess;

    /**
     * record_fail	number	导出失败记录数
     */
    @SerializedName("record_fail")
    private Integer recordFail;

    /**
     * err_msg	string	导出错误信息
     */
    @SerializedName("err_msg")
    private String errMsg;

    /**
     * file_url	string	导出文件下载地址
     */
    @SerializedName("file_url")
    private String fileUrl;

}
