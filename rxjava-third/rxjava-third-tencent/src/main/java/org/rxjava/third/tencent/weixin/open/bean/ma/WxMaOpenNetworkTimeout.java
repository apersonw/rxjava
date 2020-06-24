package org.rxjava.third.tencent.weixin.open.bean.ma;

import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class WxMaOpenNetworkTimeout implements Serializable {

    private Integer request;

    private Integer connectSocket;

    private Integer uploadFile;

    private Integer downloadFile;
}
