package top.rxjava.third.weixin.pay.bean.profitsharing;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import top.rxjava.third.weixin.common.annotation.Required;
import top.rxjava.third.weixin.pay.bean.request.BaseWxPayRequest;
import top.rxjava.third.weixin.pay.constant.WxPayConstants;
import top.rxjava.third.weixin.pay.exception.WxPayException;

import java.util.Map;

/**
 * 添加/删除分账接受方请求对象
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class ProfitSharingReceiverRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = 2628263563539120323L;
    /**
     * 字段名：分账接收方.
     * 变量名：receiver
     * 是否必填：是
     * String(2048)
     * 示例值：{
     * "type": "MERCHANT_ID",
     * "account": "190001001",
     * "name": "示例商户全称",
     * "relation_type": "STORE_OWNER"
     * }
     * 描述：分账接收方对象，json格式
     */
    @XStreamAlias("receiver")
    @Required
    private String receiver;

    @Override
    protected void checkConstraints() throws WxPayException {
        this.setSignType(WxPayConstants.SignType.HMAC_SHA256);
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("receiver", receiver);
    }
}
