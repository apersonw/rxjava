package org.rxjava.third.weixin.miniapp.bean;

import lombok.*;
import org.rxjava.third.weixin.miniapp.util.json.WxMaGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 模板消息.
 * 参考 https://developers.weixin.qq.com/miniprogram/dev/api-backend/templateMessage.send.html
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WxMaTemplateMessage implements Serializable {
    private static final long serialVersionUID = 5063374783759519418L;

    /**
     * 接收者（用户）的 openid.
     * <p>
     * 参数：touser
     * 是否必填： 是
     * 描述： 接收者（用户）的 openid
     */
    private String toUser;

    /**
     * 所需下发的模板消息的id.
     * <p>
     * 参数：template_id
     * 是否必填： 是
     * 描述： 所需下发的模板消息的id
     */
    private String templateId;

    /**
     * 点击模板卡片后的跳转页面，仅限本小程序内的页面.
     * <p>
     * 参数：page
     * 是否必填： 否
     * 描述： 点击模板卡片后的跳转页面，仅限本小程序内的页面。支持带参数,（示例index?foo=bar）。该字段不填则模板无跳转。
     */
    private String page;

    /**
     * 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id.
     * <p>
     * 参数：form_id
     * 是否必填： 是
     * 描述： 表单提交场景下，为 submit 事件带上的 formId；支付场景下，为本次支付的 prepay_id
     */
    private String formId;

    /**
     * 模板内容，不填则下发空模板.
     * <p>
     * 参数：data
     * 是否必填： 是
     * 描述： 模板内容，不填则下发空模板
     */
    private List<WxMaTemplateData> data;

    /**
     * 模板需要放大的关键词，不填则默认无放大.
     * <p>
     * 参数：emphasis_keyword
     * 是否必填： 否
     * 描述： 模板需要放大的关键词，不填则默认无放大
     */
    private String emphasisKeyword;

    public WxMaTemplateMessage addData(WxMaTemplateData datum) {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        this.data.add(datum);

        return this;
    }

    public String toJson() {
        return WxMaGsonBuilder.create().toJson(this);
    }

}
