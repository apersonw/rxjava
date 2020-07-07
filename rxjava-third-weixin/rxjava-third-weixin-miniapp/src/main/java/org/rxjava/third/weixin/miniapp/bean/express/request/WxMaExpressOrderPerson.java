package org.rxjava.third.weixin.miniapp.bean.express.request;


import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 发件人/收件人信息对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WxMaExpressOrderPerson implements Serializable {
    private static final long serialVersionUID = -7816060207882761506L;

    /**
     * 发件人/收件人姓名
     * <p>
     * 是否必填： 是
     * 描述： 不超过64字节
     */
    private String name;

    /**
     * 发件人/收件人座机号码
     * <p>
     * 是否必填： 否
     * 描述： 若不填写则必须填写 mobile，不超过32字节
     */
    private String tel;

    /**
     * 发件人/收件人手机号码
     * <p>
     * 是否必填： 否
     * 描述： 若不填写则必须填写 tel，不超过32字节
     */
    private String mobile;

    /**
     * 发件人/收件人公司名
     * <p>
     * 是否必填： 否
     * 描述： 不超过64字节
     */
    private String company;

    /**
     * 发件人/收件人邮编
     * <p>
     * 是否必填： 否
     * 描述： 不超过10字节
     */
    @SerializedName("post_code")
    private String postCode;

    /**
     * 发件人/收件人所在国家
     * <p>
     * 是否必填： 否
     * 描述： 不超过64字节
     */
    private String country;

    /**
     * 发件人/收件人省份
     * <p>
     * 是否必填： 是
     * 描述： 比如："广东省"，不超过64字节
     */
    private String province;

    /**
     * 发件人/收件人地区或市
     * <p>
     * 是否必填： 是
     * 描述： 比如："广州市"，不超过64字节
     */
    private String city;

    /**
     * 发件人/收件人区或县
     * <p>
     * 是否必填： 是
     * 描述： 比如："天河区"，不超过64字节
     */
    private String area;

    /**
     * 发件人/收件人详细地址
     * <p>
     * 是否必填： 是
     * 描述： 比如："XX路XX号XX大厦XX"，不超过512字节
     */
    private String address;

}
