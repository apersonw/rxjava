package top.rxjava.third.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import top.rxjava.third.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.List;

/**
 * 服务商模式获取永久授权码信息
 */
@Getter
@Setter
public class WxCpTpPermanentCodeInfo extends WxCpBaseResp {

    private static final long serialVersionUID = -5028321625140879571L;

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private Long expiresIn;

    @SerializedName("permanent_code")
    private String permanentCode;

    /**
     * 授权企业信息
     */
    @SerializedName("auth_corp_info")
    private AuthCorpInfo authCorpInfo;

    /**
     * 授权信息。如果是通讯录应用，且没开启实体应用，是没有该项的。通讯录应用拥有企业通讯录的全部信息读写权限
     */
    @SerializedName("auth_info")
    private AuthInfo authInfo;

    /**
     * 授权用户信息
     */
    @SerializedName("auth_user_info")
    private AuthUserInfo authUserInfo;


    @Getter
    @Setter
    public static class AuthCorpInfo {
        @SerializedName("corpid")
        private String corpId;

        @SerializedName("corp_name")
        private String corpName;

        @SerializedName("corp_type")
        private String corpType;

        @SerializedName("corp_square_logo_url")
        private String corpSquareLogoUrl;

        @SerializedName("corp_round_logo_url")
        private String corpRoundLogoUrl;

        @SerializedName("corp_user_max")
        private String corpUserMax;

        @SerializedName("corp_agent_max")
        private String corpAgentMax;

        /**
         * 所绑定的企业微信主体名称(仅认证过的企业有)
         */
        @SerializedName("corp_full_name")
        private String corpFullName;

        /**
         * 认证到期时间
         */
        @SerializedName("verified_end_time")
        private Long verifiedEndTime;

        /**
         * 企业类型，1. 企业; 2. 政府以及事业单位; 3. 其他组织, 4.团队号
         */
        @SerializedName("subject_type")
        private Integer subjectType;

        /**
         * 授权企业在微工作台（原企业号）的二维码，可用于关注微工作台
         */
        @SerializedName("corp_wxqrcode")
        private String corpWxQrcode;

        @SerializedName("corp_scale")
        private String corpScale;

        @SerializedName("corp_industry")
        private String corpIndustry;

        @SerializedName("corp_sub_industry")
        private String corpSubIndustry;

        @SerializedName("location")
        private String location;

    }

    /**
     * 授权信息
     */
    @Getter
    @Setter
    public static class AuthInfo {

        /**
         * 授权的应用信息，注意是一个数组，但仅旧的多应用套件授权时会返回多个agent，对新的单应用授权，永远只返回一个agent
         */
        @SerializedName("agent")
        private List<Agent> agents;

    }

    @Getter
    @Setter
    public static class Agent {
        @SerializedName("agentid")
        private Integer agentId;

        @SerializedName("name")
        private String name;

        @SerializedName("round_logo_url")
        private String roundLogoUrl;

        @SerializedName("square_logo_url")
        private String squareLogoUrl;

        /**
         * 旧的多应用套件中的对应应用id，新开发者请忽略
         */
        @SerializedName("appid")
        @Deprecated
        private String appid;

        /**
         * 应用权限
         */
        @SerializedName("privilege")
        private Privilege privilege;

    }

    /**
     * 授权人员信息
     */
    @Getter
    @Setter
    public static class AuthUserInfo {
        @SerializedName("userid")
        private String userId;

        @SerializedName("name")
        private String name;

        @SerializedName("avatar")
        private String avatar;
    }

    /**
     * 应用对应的权限
     */
    @Getter
    @Setter
    public static class Privilege {

        /**
         * 权限等级。
         * 1:通讯录基本信息只读
         * 2:通讯录全部信息只读
         * 3:通讯录全部信息读写
         * 4:单个基本信息只读
         * 5:通讯录全部信息只写
         */
        @SerializedName("level")
        private Integer level;

        @SerializedName("allow_party")
        private List<Integer> allowParties;

        @SerializedName("allow_user")
        private List<String> allowUsers;

        @SerializedName("allow_tag")
        private List<Integer> allowTags;

        @SerializedName("extra_party")
        private List<Integer> extraParties;

        @SerializedName("extra_user")
        private List<String> extraUsers;

        @SerializedName("extra_tag")
        private List<Integer> extraTags;


    }

    public static WxCpTpPermanentCodeInfo fromJson(String json) {
        return WxCpGsonBuilder.create().fromJson(json, WxCpTpPermanentCodeInfo.class);
    }

    public String toJson() {
        return WxCpGsonBuilder.create().toJson(this);
    }

}
