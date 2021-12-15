package top.rxjava.third.weixin.common.service;

import top.rxjava.third.weixin.common.error.WxErrorException;

/**
 * 微信服务接口.
 */
public interface WxService {
    /**
     * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的GET请求.
     *
     * @param queryParam 参数
     * @param url        请求接口地址
     * @return 接口响应字符串
     * @throws WxErrorException 异常
     */
    String get(String url, String queryParam) throws WxErrorException;

    /**
     * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求.
     *
     * @param postData 请求参数json值
     * @param url      请求接口地址
     * @return 接口响应字符串
     * @throws WxErrorException 异常
     */
    String post(String url, String postData) throws WxErrorException;

    /**
     * 当本Service没有实现某个API的时候，可以用这个，针对所有微信API中的POST请求.
     *
     * @param url 请求接口地址
     * @param obj 请求对象
     * @return 接口响应字符串
     * @throws WxErrorException 异常
     */
    String post(String url, Object obj) throws WxErrorException;
}
