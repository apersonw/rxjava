package org.rxjava.common.bus;

/**
 * @author happy 2019-06-04 10:24
 * 总线消息类型
 */
public enum BusEventType {
    /**
     * 登陆
     */
    LOGIN,
    /**
     * 注册
     */
    REGISTER,
    /**
     * 订单付款成功
     */
    ORDER_PAY_OK,
    /**
     * 订单取消成功
     */
    ORDER_CANCEL_OK
}