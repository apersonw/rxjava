package org.rxjava.third.weixin.pay.util;

import org.rxjava.third.weixin.pay.bean.request.BaseWxPayRequest;
import org.rxjava.third.weixin.pay.bean.result.BaseWxPayResult;

public class XmlConfig {

    /**
     * 是否使用快速模式
     * <p>
     * 如果设置为true，将会影响下面的方法，不再使用反射的方法来进行xml转换。
     * 1: BaseWxPayRequest的toXML方法
     * 2: BaseWxPayResult的fromXML方法
     *
     * @see BaseWxPayRequest#toXML
     * @see BaseWxPayResult#fromXML
     * <p>
     * 启用快速模式后，将能：
     * 1：性能提升约 10 ~ 15倍
     * 2：可以通过 graalvm 生成native image，大大减少系统开销(CPU,RAM)，加快应用启动速度(亚秒级)，加快系统部署速度（脱离JRE）.
     * <p>
     * 参考测试案例: org.rxjava.third.weixin.wxpay.bean.result.WxPayRedpackQueryResultTest#benchmark
     * 参考网址: https://www.graalvm.org/
     */
    public static boolean fastMode = false;

}
