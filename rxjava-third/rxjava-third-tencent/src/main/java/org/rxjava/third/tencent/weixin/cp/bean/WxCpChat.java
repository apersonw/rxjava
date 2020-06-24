package org.rxjava.third.tencent.weixin.cp.bean;

import lombok.Data;

import java.util.List;

/**
 * 群聊
 *
 * @author gaigeshen
 */
@Data
public class WxCpChat {

    private String id;
    private String name;
    private String owner;
    private List<String> users;

}
