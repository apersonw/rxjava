package top.rxjava.third.weixin.cp.bean;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.rxjava.third.weixin.cp.util.json.WxCpGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * 企业号应用信息.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WxCpAgent implements Serializable {
    private static final long serialVersionUID = 5002894979081127234L;

    @SerializedName("errcode")
    private Integer errCode;

    @SerializedName("errmsg")
    private String errMsg;

    @SerializedName("agentid")
    private Integer agentId;

    @SerializedName("name")
    private String name;

    @SerializedName("square_logo_url")
    private String squareLogoUrl;

    @SerializedName("logo_mediaid")
    private String logoMediaId;

    @SerializedName("description")
    private String description;

    @SerializedName("allow_userinfos")
    private Users allowUserInfos;

    @SerializedName("allow_partys")
    private Parties allowParties;

    @SerializedName("allow_tags")
    private Tags allowTags;

    @SerializedName("close")
    private Integer close;

    @SerializedName("redirect_domain")
    private String redirectDomain;

    @SerializedName("report_location_flag")
    private Integer reportLocationFlag;

    @SerializedName("isreportenter")
    private Integer isReportEnter;

    @SerializedName("home_url")
    private String homeUrl;

    public static WxCpAgent fromJson(String json) {
        return WxCpGsonBuilder.create().fromJson(json, WxCpAgent.class);
    }

    public String toJson() {
        return WxCpGsonBuilder.create().toJson(this);
    }

    @Data
    public static class Users implements Serializable {
        private static final long serialVersionUID = 8801100463558788565L;
        @SerializedName("user")
        private List<User> users;
    }

    @Data
    public class User implements Serializable {
        private static final long serialVersionUID = 7287632514385508024L;
        @SerializedName("userid")
        private String userId;
    }

    @Data
    public class Parties {
        @SerializedName("partyid")
        private List<Integer> partyIds = null;
    }

    @Data
    public class Tags {
        @SerializedName("tagid")
        private List<Integer> tagIds = null;
    }

}
