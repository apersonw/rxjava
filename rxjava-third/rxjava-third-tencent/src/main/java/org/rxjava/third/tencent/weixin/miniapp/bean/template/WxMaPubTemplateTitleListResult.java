package org.rxjava.third.tencent.weixin.miniapp.bean.template;

import lombok.Data;
import org.rxjava.third.tencent.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * @author ArBing
 */
@Data
public class WxMaPubTemplateTitleListResult implements Serializable {
    private static final long serialVersionUID = -7718911668757837527L;

    private int count;

    private List<TemplateItem> data;

    public static WxMaPubTemplateTitleListResult fromJson(String json) {
        return WxGsonBuilder.create().fromJson(json, WxMaPubTemplateTitleListResult.class);
    }

    @Data
    public static class TemplateItem {

        private Integer type;

        private Integer tid;

        private String categoryId;

        private String title;
    }
}
