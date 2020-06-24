package org.rxjava.third.tencent.weixin.wxpay.bean.notify;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rxjava.third.tencent.weixin.common.util.xml.XStreamCDataConverter;
import org.rxjava.third.tencent.weixin.common.util.xml.XStreamInitializer;

/**
 * 微信支付订单和退款的异步通知共用的响应类.
 */
@Data
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayNotifyResponse {
    @XStreamOmitField
    private static final transient String FAIL = "FAIL";
    @XStreamOmitField
    private static final transient String SUCCESS = "SUCCESS";

    @XStreamAlias("return_code")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String returnCode;
    @XStreamConverter(value = XStreamCDataConverter.class)
    @XStreamAlias("return_msg")
    private String returnMsg;

    /**
     * Fail string.
     *
     * @param msg the msg
     * @return the string
     */
    public static String fail(String msg) {
        WxPayNotifyResponse response = new WxPayNotifyResponse(FAIL, msg);
        XStream xstream = XStreamInitializer.getInstance();
        xstream.autodetectAnnotations(true);
        return xstream.toXML(response).replace("\n", "").replace("  ", "");
    }

    /**
     * Success string.
     *
     * @param msg the msg
     * @return the string
     */
    public static String success(String msg) {
        WxPayNotifyResponse response = new WxPayNotifyResponse(SUCCESS, msg);
        XStream xstream = XStreamInitializer.getInstance();
        xstream.autodetectAnnotations(true);
        return xstream.toXML(response).replace("\n", "").replace("  ", "");
    }

    /**
     * Fail string.
     *
     * @param msg the msg
     * @return the string
     */
    public static String failResp(String msg) {
        return generateXml(FAIL, msg);
    }

    /**
     * Success string.
     *
     * @param msg the msg
     * @return the string
     */
    public static String successResp(String msg) {
        return generateXml(SUCCESS, msg);
    }


    /**
     * 使用格式化字符串生成xml字符串
     */
    private static String generateXml(String code, String msg) {
        return String.format("<xml><return_code><![CDATA[%s]]></return_code><return_msg><![CDATA[%s]]></return_msg></xml>", code, msg);
    }
}
