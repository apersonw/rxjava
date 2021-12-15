package top.rxjava.apikit.tool.utils;

/**
 * 订单状态
 */
public enum OrderStatus {
    /**
     * 已创建
     */
    CREATED,
    /**
     * 已付款
     */
    PAID,
    /**
     * 已发货
     */
    SHIPPED,
    /**
     * 确认收货
     */
    CONFIRMED,
    /**
     * 取消订单
     */
    CANCELLED;
}
