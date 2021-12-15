package top.rxjava.third.weixin.mp.bean.card;


import lombok.Data;
import top.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * 微信卡券.
 *
 * @version 15/11/11
 */
@Data
public class WxMpCard implements Serializable {
    private static final long serialVersionUID = 9214301870017772921L;

    private String cardId;

    private Long beginTime;

    private Long endTime;

    private String userCardStatus;

    private String membershipNumber;

    private String code;

    private Integer bonus;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
