package top.rxjava.third.weixin.mp.bean.card;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import top.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 微信会员卡基本信息更新
 */
@Data
public class BaseInfoUpdate implements Serializable {

    /**
     * 需要审核：卡券名,字数上限为9个汉字 (建议涵盖卡券属性、服务及金额).
     */
    @SerializedName("title")
    private String title;

    /**
     * 卡券的商户logo,建议像素为300*300.
     */
    @SerializedName("logo_url")
    private String logoUrl;

    /**
     * 卡券使用提醒,字数上限为16个汉字.
     */
    @SerializedName("notice")
    private String notice;

    /**
     * 需要审核：卡券使用说明,字数上限为1024个汉字.
     */
    @SerializedName("description")
    private String description;

    /**
     * 客服电话.
     */
    @SerializedName("service_phone")
    private String servicePhone;

    /**
     * 券颜色,按色彩规范标注填写Color010-Color100.
     */
    @SerializedName("color")
    private String color;

    /**
     * 门店位置ID,调用 POI门店管理接口 获取门店位置ID.
     */
    @SerializedName("location_id_list")
    private List<String> locationIdList;

    /**
     * 会员卡是否支持全部门店,填写后商户门店更新时会自动同步至卡券.
     */
    @SerializedName("use_all_locations")
    private Boolean useAllLocations;

    /**
     * 卡券中部居中的按钮,仅在卡券激活后且可用状态 时显示.
     */
    @SerializedName("center_title")
    private String centerTitle;

    /**
     * 显示在入口下方的提示语,仅在卡券激活后且可用状态时显示.
     */
    @SerializedName("center_sub_title")
    private String centerSubTitle;

    /**
     * 顶部居中的url,仅在卡券激活后且可用状态时显示.
     */
    @SerializedName("center_url")
    private String centerUrl;

    /**
     * 自定义跳转外链的入口名字.
     */
    @SerializedName("custom_url_name")
    private String customUrlName;

    /**
     * 自定义跳转的URL.
     */
    @SerializedName("custom_url")
    private String customUrl;

    /**
     * 显示在入口右侧的提示语.
     */
    @SerializedName("custom_url_sub_title")
    private String customUrlSubTitle;

    /**
     * 营销场景的自定义入口名称.
     */
    @SerializedName("promotion_url_name")
    private String promotionUrlName;

    /**
     * 入口跳转外链的地址链接.
     */
    @SerializedName("promotion_url")
    private String promotionUrl;

    /**
     * 显示在营销入口右侧的提示语.
     */
    @SerializedName("promotion_url_sub_title")
    private String promotionUrlSubTitle;

    /**
     * Code展示类型.
     * "CODE_TYPE_TEXT" 文本 "CODE_TYPE_BARCODE" 一维码 "CODE_TYPE_QRCODE" 二维码 "CODE_TYPE_ONLY_QRCODE" 仅显示二维码 "CODE_TYPE_ONLY_BARCODE" 仅显示一维码 "CODE_TYPE_NONE" 不显示任何码型
     */
    @SerializedName("code_type")
    private String codeType;

    /**
     * 支付功能结构体,swipe_card结构.
     */
    @SerializedName("pay_info")
    private PayInfo payInfo;

    /**
     * 是否设置该会员卡中部的按钮同时支持微信支付刷卡和会员卡二维码.
     */
    @SerializedName("is_pay_and_qrcode")
    private Boolean isPayAndQrcode;

    /**
     * 每人可领券的数量限制,建议会员卡每人限领一张.
     */
    @SerializedName("get_limit")
    private Integer getLimit;

    /**
     * 卡券领取页面是否可分享,默认为true.
     */
    @SerializedName("can_share")
    private Boolean canShare;

    /**
     * 卡券是否可转赠,默认为true.
     */
    @SerializedName("can_give_friend")
    private Boolean canGiveFriend;

    /**
     * 使用日期,有效期的信息.
     */
    @SerializedName("date_info")
    private DateInfo dateInfo;

    /**
     * 微信小程序开放功能 小程序&卡券打通部分新增8个字段 https://mp.weixin.qq.com/cgi-bin/announce?action=getannouncement&key=1490190158&version=1&lang=zh_CN&platform=2
     * 自定义使用入口跳转小程序的user_name，格式为原始id+@app
     */
    @SerializedName("custom_app_brand_user_name")
    private String customAppBrandUserName;
    /**
     * 自定义使用入口小程序页面地址
     */
    @SerializedName("custom_app_brand_pass")
    private String customAppBrandPass;
    /**
     * 小程序的user_name
     */
    @SerializedName("center_app_brand_user_name")
    private String centerAppBrandUserName;
    /**
     * 自定义居中使用入口小程序页面地址
     */
    @SerializedName("center_app_brand_pass")
    private String centerAppBrandPass;
    /**
     * 小程序的user_name
     */
    @SerializedName("promotion_app_brand_user_name")
    private String promotionAppBrandUserName;
    /**
     * 自定义营销入口小程序页面地址
     */
    @SerializedName("promotion_app_brand_pass")
    private String promotionAppBrandPass;

    /**
     * 小程序的user_name,
     */
    @SerializedName("activate_app_brand_user_name")
    private String activateAppBrandUserName;
    /**
     * 激活小程序页面地址
     */
    @SerializedName("activate_app_brand_pass")
    private String activateAppBrandPass;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
