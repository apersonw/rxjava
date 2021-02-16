package org.rxjava.webflux.bus;

/**
 * @author happy 2019-06-04 10:24
 * 总线消息类型
 */
public enum BusEventType {
    //应用服务总线消息
    /**
     * 应用订单取消成功
     */
    APP_ORDER_CANCEL_SUCCESS,
    /**
     * 应用订单待发货
     */
    APP_ORDER_WAIT_DELIVER,
    /**
     * 应用订单发货成功
     */
    APP_ORDER_SHIP_SUCCESS,

    //订单服务总线消息类型
    /**
     * 支付成功
     */
    PAY_SUCCESS,
    /**
     * 订单取消开始
     */
    ORDER_CANCEL_START,

    //会员卡服务总线消息类型

    //商品服务总线消息类型

}