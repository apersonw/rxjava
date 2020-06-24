package org.rxjava.third.tencent.weixin.open.bean.message;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.rxjava.third.tencent.weixin.open.bean.ma.WxOpenMaPreviewInfo;
import org.rxjava.third.tencent.weixin.open.bean.ma.WxOpenMaSubmitAudit;

import java.io.Serializable;
import java.util.List;

/**
 * 微信小程序代码包提交审核(仅供第三方开发者代小程序调用)
 */
@Data
public class WxOpenMaSubmitAuditMessage implements Serializable {

    /**
     * 提交审核项的一个列表（至少填写1项，至多填写5项）
     */
    @SerializedName("item_list")
    private List<WxOpenMaSubmitAudit> itemList;

    /**
     * 预览信息（小程序页面截图和操作录屏）
     */
    @SerializedName("preview_info")
    private List<WxOpenMaPreviewInfo> previewInfo;

    /**
     * 小程序版本说明和功能解释
     */
    @SerializedName("version_desc")
    private String versionDesc;

    /**
     * 反馈内容，不超过200字
     */
    @SerializedName("feedback_info")
    private String feedbackInfo;

    /**
     * 图片media_id列表，中间用“丨”分割，xx丨yy丨zz，不超过5张图片, 其中 media_id 可以通过新增临时素材接口上传而得到
     */
    @SerializedName("feedback_stuff")
    private String feedbackStuff;
}
