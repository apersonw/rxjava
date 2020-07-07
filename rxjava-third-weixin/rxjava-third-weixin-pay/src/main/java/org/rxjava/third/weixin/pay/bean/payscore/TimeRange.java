package org.rxjava.third.weixin.pay.bean.payscore;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 服务时间范围.
 */
@Data
@NoArgsConstructor
public class TimeRange implements Serializable {
    private static final long serialVersionUID = 8169562173656314930L;
    /**
     * start_time : 20091225091010
     * end_time : 20091225121010
     */
    @SerializedName("start_time")
    private String startTime;
    @SerializedName("end_time")
    private String endTime;
}
