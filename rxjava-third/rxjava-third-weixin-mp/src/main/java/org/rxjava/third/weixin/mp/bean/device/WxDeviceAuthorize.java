package org.rxjava.third.weixin.mp.bean.device;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxDeviceAuthorize extends AbstractDeviceBean {
    private static final long serialVersionUID = 8786321356569903887L;

    @SerializedName("device_num")
    private String deviceNum;
    @SerializedName("op_type")
    private String opType;
    @SerializedName("product_id")
    private String productId;
    @SerializedName("device_list")
    private List<WxDevice> deviceList = new LinkedList<>();

    public void addDevice(WxDevice... devices) {
        this.deviceList.addAll(Arrays.asList(devices));
    }
}
