package org.rxjava.third.tencent.weixin.wxpay.bean.result;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;

/**
 * 转换短链接结果对象类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@XStreamAlias("xml")
public class WxPayShorturlResult extends BaseWxPayResult {
    /**
     * URL链接
     * short_url
     * 是
     * String(64)
     * weixin：//wxpay/s/XXXXXX
     * 转换后的URL
     */
    @XStreamAlias("short_url")
    private String shortUrl;

    /**
     * 从XML结构中加载额外的熟悉
     *
     * @param d Document
     */
    @Override
    protected void loadXML(Document d) {
        shortUrl = readXMLString(d, "short_url");
    }

}
