package top.rxjava.third.weixin.pay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import top.rxjava.third.weixin.common.annotation.Required;
import top.rxjava.third.weixin.pay.constant.WxPayConstants.AccountType;
import top.rxjava.third.weixin.pay.exception.WxPayException;

import java.util.Arrays;
import java.util.Map;

/**
 * 微信支付下载资金账单请求参数类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayDownloadFundFlowRequest extends BaseWxPayRequest {
    private static final String[] ACCOUNT_TYPES = new String[]{AccountType.BASIC, AccountType.OPERATION, AccountType.FEES};
    private static final String SIGN_TYPE_HMAC_SHA256 = "HMAC-SHA256";
    private static final String TAR_TYPE_GZIP = "GZIP";

    /**
     * 对账单日期
     * bill_date
     * 是
     * String(8)
     * 20140603
     * 下载对账单的日期，格式：20140603
     */
    @Required
    @XStreamAlias("bill_date")
    private String billDate;

    /**
     * 资金账户类型
     * account_type
     * 是
     * Basic
     * String(8)
     * --Basic，基本账户
     * --Operation，运营账户
     * --Fees，手续费账户
     */
    @Required
    @XStreamAlias("account_type")
    private String accountType;

    /**
     * 压缩账单
     * tar_type
     * 否
     * String(8)
     * GZIP
     * 非必传参数，固定值：GZIP，返回格式为.gzip的压缩包账单。不传则默认为数据流形式。
     */
    @XStreamAlias("tar_type")
    private String tarType;

    @Override
    protected void checkConstraints() throws WxPayException {
        if (StringUtils.isNotBlank(this.getTarType()) && !TAR_TYPE_GZIP.equals(this.getTarType())) {
            throw new WxPayException("tar_type值如果存在，只能为GZIP");
        }

        if (!ArrayUtils.contains(ACCOUNT_TYPES, this.getAccountType())) {
            throw new WxPayException(String.format("account_type必须为%s其中之一,实际值：%s",
                    Arrays.toString(ACCOUNT_TYPES), this.getAccountType()));
        }
        /**
         * 目前仅支持HMAC-SHA256
         */
        this.setSignType(SIGN_TYPE_HMAC_SHA256);
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("bill_date", billDate);
        map.put("account_type", accountType);
        map.put("tar_type", tarType);
    }
}
