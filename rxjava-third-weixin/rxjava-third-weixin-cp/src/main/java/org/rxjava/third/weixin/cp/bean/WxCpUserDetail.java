package top.rxjava.third.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

/**
 * 使用user_ticket获取成员详情接口返回类.
 */
@Data
public class WxCpUserDetail {

    /**
     * 成员UserID
     */
    @SerializedName("userid")
    private String userId;

    /**
     * 成员姓名
     */
    private String name;

    /**
     * 成员手机号，仅在用户同意snsapi_privateinfo授权时返回
     */
    private String mobile;

    /**
     * 性别。0表示未定义，1表示男性，2表示女性
     */
    private String gender;

    /**
     * 成员邮箱，仅在用户同意snsapi_privateinfo授权时返回
     */
    private String email;

    /**
     * 头像url。注：如果要获取小图将url最后的”/0”改成”/100”即可。仅在用户同意snsapi_privateinfo授权时返回
     */
    private String avatar;

    /**
     * 员工个人二维码（扫描可添加为外部联系人），仅在用户同意snsapi_privateinfo授权时返回
     */
    @SerializedName("qr_code")
    private String qrCode;

}
