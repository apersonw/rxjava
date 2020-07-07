package org.rxjava.third.weixin.open.bean.result;

import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class WxOpenAuthorizerOptionResult implements Serializable {
    private static final long serialVersionUID = 4477837353654658179L;

    String authorizerAppid;
    String optionName;
    String optionValue;
}
