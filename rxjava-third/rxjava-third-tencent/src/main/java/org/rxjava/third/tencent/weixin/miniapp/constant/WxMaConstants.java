package org.rxjava.third.tencent.weixin.miniapp.constant;

/**
 * 小程序常量.
 */
public class WxMaConstants {
    /**
     * 微信接口返回的参数errcode.
     */
    public static final String ERRCODE = "errcode";

    /**
     * 素材类型.
     */
    public static class MediaType {
        /**
         * 图片.
         */
        public static final String IMAGE = "image";
    }

    /**
     * 消息格式.
     */
    public static class MsgDataFormat {
        public static final String XML = "XML";
        public static final String JSON = "JSON";
    }

    /**
     * 客服消息的消息类型.
     */
    public static class KefuMsgType {
        /**
         * 文本消息.
         */
        public static final String TEXT = "text";
        /**
         * 图片消息.
         */
        public static final String IMAGE = "image";
        /**
         * 图文链接.
         */
        public static final String LINK = "link";
        /**
         * 小程序卡片消息.
         */
        public static final String MA_PAGE = "miniprogrampage";
    }

    public static final class ErrorCode {
        /**
         * 40001 获取access_token时AppSecret错误，或者access_token无效.
         */
        public static final int ERR_40001 = 40001;

        /**
         * 42001 access_token超时.
         */
        public static final int ERR_42001 = 42001;

        /**
         * 40014 不合法的access_token，请开发者认真比对access_token的有效性（如是否过期）.
         */
        public static final int ERR_40014 = 40014;
    }

    /**
     * 内容安全检测的媒体类型
     */
    public static final class SecCheckMediaType {

        /**
         * 音频
         */
        public static final int VOICE = 1;

        /**
         * 图片
         */
        public static final int IMAGE = 2;
    }

    /**
     * 快递账号绑定类型
     */
    public static final class BindAccountType {

        /**
         * 绑定
         */
        public static final String BIND = "bind";

        /**
         * 解绑
         */
        public static final String UNBIND = "unbind";
    }

    /**
     * 快递下单订单来源
     */
    public static final class OrderAddSource {

        /**
         * 小程序
         */
        public static final int MINI_PROGRAM = 0;

        /**
         * APP或H5
         */
        public static final int APP_OR_H5 = 2;
    }

    /**
     * 快递下单保价
     */
    public static final class OrderAddInsured {
        /**
         * 不保价
         */
        public static final int INSURED_PROGRAM = 0;

        /**
         * 保价
         */
        public static final int USE_INSURED = 1;

        /**
         * 默认保价金额
         */
        public static final int DEFAULT_INSURED_VALUE = 0;
    }


    /**
     * 小程序订阅消息跳转小程序类型
     * <p>
     * developer为开发版；trial为体验版；formal为正式版；默认为正式版
     */
    public static final class MiniprogramState {
        /**
         * 开发版
         */
        public static final String DEVELOPER = "developer";

        /**
         * 体验版
         */
        public static final String TRIAL = "trial";

        /**
         * 正式版
         */
        public static final String FORMAL = "formal";
    }


    /**
     * 进入小程序查看的语言类型
     * 支持zh_CN(简体中文)、en_US(英文)、zh_HK(繁体中文)、zh_TW(繁体中文)，默认为zh_CN
     */
    public static final class MiniprogramLang {
        /**
         * 简体中文
         */
        public static final String ZH_CN = "zh_CN";

        /**
         * 英文
         */
        public static final String EN_US = "en_US";

        /**
         * 繁体中文
         */
        public static final String ZH_HK = "zh_HK";

        /**
         * 繁体中文
         */
        public static final String ZH_TW = "zh_TW";
    }
}
