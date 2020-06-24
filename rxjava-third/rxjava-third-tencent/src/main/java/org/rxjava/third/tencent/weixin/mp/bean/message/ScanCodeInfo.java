package org.rxjava.third.tencent.weixin.mp.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import org.rxjava.third.tencent.weixin.common.util.xml.XStreamCDataConverter;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * Created by BinaryWang on 2017/5/4.
 *
 * @author Binary Wang
 */
@XStreamAlias("ScanCodeInfo")
@Data
public class ScanCodeInfo implements Serializable {
    private static final long serialVersionUID = 4745181270645050122L;

    /**
     * 扫描类型，一般是qrcode.
     */
    @XStreamAlias("ScanType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String scanType;

    /**
     * 扫描结果，即二维码对应的字符串信息.
     */
    @XStreamAlias("ScanResult")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String scanResult;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}
