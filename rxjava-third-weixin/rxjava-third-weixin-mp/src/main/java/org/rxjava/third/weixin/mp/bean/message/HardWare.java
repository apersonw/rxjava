package top.rxjava.third.weixin.mp.bean.message;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import top.rxjava.third.weixin.common.util.xml.XStreamCDataConverter;
import top.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 */
@XStreamAlias("HardWare")
@Data
public class HardWare implements Serializable {
    private static final long serialVersionUID = -1295785297354896461L;

    /**
     * 消息展示，目前支持myrank(排行榜)
     */
    @XStreamAlias("MessageView")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String messageView;
    /**
     * 消息点击动作，目前支持ranklist(点击跳转排行榜)
     */
    @XStreamAlias("MessageAction")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String messageAction;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }
}
