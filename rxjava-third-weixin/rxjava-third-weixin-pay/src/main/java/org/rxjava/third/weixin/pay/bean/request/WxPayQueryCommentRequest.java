package top.rxjava.third.weixin.pay.bean.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.*;
import top.rxjava.third.weixin.common.annotation.Required;
import top.rxjava.third.weixin.pay.exception.WxPayException;

import java.util.Map;

/**
 * 拉取订单评价数据接口的请求参数封装类.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Builder(builderMethodName = "newBuilder")
@NoArgsConstructor
@AllArgsConstructor
@XStreamAlias("xml")
public class WxPayQueryCommentRequest extends BaseWxPayRequest {
    private static final long serialVersionUID = 2633600418272768186L;

    /**
     * 字段名：开始时间.
     * 变量名：begin_time
     * 是否必填：是
     * 类型：String(19)
     * 示例值：20170724000000
     * 描述：按用户评论时间批量拉取的起始时间，格式为yyyyMMddHHmmss
     */
    @Required
    @XStreamAlias("begin_time")
    private String beginTime;

    /**
     * 字段名：结束时间.
     * 变量名：end_time
     * 是否必填：是
     * 类型：String(19)
     * 示例值：20170725000000
     * 描述：按用户评论时间批量拉取的结束时间，格式为yyyyMMddHHmmss
     */
    @Required
    @XStreamAlias("end_time")
    private String endTime;

    /**
     * 字段名：位移.
     * 变量名：offset
     * 是否必填：是
     * 类型：uint(64)
     * 示例值：0
     * 描述：指定从某条记录的下一条开始返回记录。接口调用成功时，会返回本次查询最后一条数据的offset。商户需要翻页时，应该把本次调用返回的offset 作为下次调用的入参。注意offset是评论数据在微信支付后台保存的索引，未必是连续的
     */
    @Required
    @XStreamAlias("offset")
    private Integer offset;

    /**
     * 字段名：条数.
     * 变量名：limit
     * 是否必填：否
     * 类型：uint(32)
     * 示例值：100
     * 描述：一次拉取的条数, 最大值是200，默认是200
     */
    @XStreamAlias("limit")
    private Integer limit;

    /**
     * 检查约束情况.
     */
    @Override
    protected void checkConstraints() throws WxPayException {
    }

    @Override
    protected void storeMap(Map<String, String> map) {
        map.put("begin_time", beginTime);
        map.put("end_time", endTime);
        map.put("offset", offset.toString());
        if (limit != null) {
            map.put("limit", limit.toString());
        }
    }

}
