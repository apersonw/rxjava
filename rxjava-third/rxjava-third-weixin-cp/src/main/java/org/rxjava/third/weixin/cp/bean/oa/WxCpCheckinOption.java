package org.rxjava.third.weixin.cp.bean.oa;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 企业微信打卡规则.
 */
@Data
public class WxCpCheckinOption implements Serializable {
    private static final long serialVersionUID = -1964233697990417482L;

    @SerializedName("userid")
    private String userId;

    private Group group;

    @Data
    public static class CheckinDate implements Serializable {
        private static final long serialVersionUID = -5601722383347110974L;

        private List<Integer> workdays;

        @SerializedName("checkintime")
        private CheckinTime[] checkinTime;

        @SerializedName("flex_time")
        private Long flexTime;

        @SerializedName("noneed_offwork")
        private Boolean noNeedOffwork;

        @SerializedName("limit_aheadtime")
        private Long limitAheadTime;
    }

    @Data
    public static class CheckinTime implements Serializable {
        private static final long serialVersionUID = -8579954143265336276L;

        @SerializedName("work_sec")
        private Long workSec;

        @SerializedName("off_work_sec")
        private Long offWorkSec;

        @SerializedName("remind_work_sec")
        private Long remindWorkSec;

        @SerializedName("remind_off_work_sec")
        private Long remindOffWorkSec;
    }

    @Data
    public static class Group implements Serializable {

        private static final long serialVersionUID = -5888406969613403044L;

        @SerializedName("groupid")
        private Long id;

        @SerializedName("groupname")
        private String name;

        @SerializedName("grouptype")
        private Integer type;

        @SerializedName("checkindate")
        private List<CheckinDate> checkinDate;

        @SerializedName("spe_workdays")
        private List<SpeDay> speWorkdays;

        @SerializedName("spe_offdays")
        private List<SpeDay> speOffdays;

        @SerializedName("sync_holidays")
        private Boolean syncHolidays;

        @SerializedName("need_photo")
        private Boolean needPhoto;

        @SerializedName("note_can_use_local_pic")
        private Boolean noteCanUseLocalPic;

        @SerializedName("allow_checkin_offworkday")
        private Boolean allowCheckinOffWorkday;

        @SerializedName("allow_apply_offworkday")
        private Boolean allowApplyOffWorkday;

        @SerializedName("wifimac_infos")
        private List<WifiMacInfo> wifiMacInfos;

        @SerializedName("loc_infos")
        private List<LocInfo> locInfos;

    }

    @Data
    public static class WifiMacInfo implements Serializable {
        private static final long serialVersionUID = -4657809185716627368L;

        @SerializedName("wifiname")
        private String name;

        @SerializedName("wifimac")
        private String mac;
    }

    @Data
    public static class LocInfo implements Serializable {
        private static final long serialVersionUID = -618965280668099608L;

        private Long lat;
        private Long lng;

        @SerializedName("loc_title")
        private String title;

        @SerializedName("loc_detail")
        private String detail;

        private Long distance;
    }

    @Data
    public static class SpeDay implements Serializable {
        private static final long serialVersionUID = -3538818921359212748L;

        private Long timestamp;
        private String notes;

        @SerializedName("checkintime")
        private List<CheckinTime> checkinTime;

    }

}
