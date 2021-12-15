package top.rxjava.third.weixin.mp.api;

import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.mp.bean.*;
import top.rxjava.third.weixin.mp.bean.result.WxMpMassGetResult;
import top.rxjava.third.weixin.mp.bean.result.WxMpMassSendResult;
import top.rxjava.third.weixin.mp.bean.result.WxMpMassSpeedGetResult;
import top.rxjava.third.weixin.mp.bean.result.WxMpMassUploadResult;

/**
 * 群发消息服务类.
 */
public interface WxMpMassMessageService {
    /**
     * 上传群发用的图文消息，上传后才能群发图文消息.
     * <p>
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN
     *
     * @see #massGroupMessageSend(WxMpMassTagMessage)
     * @see #massOpenIdsMessageSend(WxMpMassOpenIdsMessage)
     */
    WxMpMassUploadResult massNewsUpload(WxMpMassNews news) throws WxErrorException;

    /**
     * 上传群发用的视频，上传后才能群发视频消息.
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN
     *
     * @see #massGroupMessageSend(WxMpMassTagMessage)
     * @see #massOpenIdsMessageSend(WxMpMassOpenIdsMessage)
     */
    WxMpMassUploadResult massVideoUpload(WxMpMassVideo video) throws WxErrorException;

    /**
     * 分组群发消息.
     * 如果发送图文消息，必须先使用 {@link #massNewsUpload(WxMpMassNews)} 获得media_id，然后再发送
     * 如果发送视频消息，必须先使用 {@link #massVideoUpload(WxMpMassVideo)} 获得media_id，然后再发送
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN
     */
    WxMpMassSendResult massGroupMessageSend(WxMpMassTagMessage message) throws WxErrorException;

    /**
     * 按openId列表群发消息.
     * 如果发送图文消息，必须先使用 {@link #massNewsUpload(WxMpMassNews)} 获得media_id，然后再发送
     * 如果发送视频消息，必须先使用 {@link #massVideoUpload(WxMpMassVideo)} 获得media_id，然后再发送
     * 详情请见: http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN
     */
    WxMpMassSendResult massOpenIdsMessageSend(WxMpMassOpenIdsMessage message) throws WxErrorException;

    /**
     * 群发消息预览接口.
     * 开发者可通过该接口发送消息给指定用户，在手机端查看消息的样式和排版。为了满足第三方平台开发者的需求，
     * 在保留对openID预览能力的同时，增加了对指定微信号发送预览的能力，但该能力每日调用次数有限制（100次），请勿滥用。
     * 接口调用请求说明
     * http请求方式: POST
     * https://api.weixin.qq.com/cgi-bin/message/mass/preview?access_token=ACCESS_TOKEN
     * 详情请见：http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140549&token=&lang=zh_CN
     *
     * @return wxMpMassSendResult
     */
    WxMpMassSendResult massMessagePreview(WxMpMassPreviewMessage wxMpMassPreviewMessage) throws WxErrorException;

    /**
     * 删除群发.
     * 群发之后，随时可以通过该接口删除群发。
     * 请注意：
     * 1、只有已经发送成功的消息才能删除
     * 2、删除消息是将消息的图文详情页失效，已经收到的用户，还是能在其本地看到消息卡片。
     * 3、删除群发消息只能删除图文消息和视频消息，其他类型的消息一经发送，无法删除。
     * 4、如果多次群发发送的是一个图文消息，那么删除其中一次群发，就会删除掉这个图文消息也，导致所有群发都失效
     * 接口调用请求说明：
     * http请求方式: POST
     * https://api.weixin.qq.com/cgi-bin/message/mass/delete?access_token=ACCESS_TOKEN
     * 详情请见：https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1481187827_i0l21
     *
     * @param msgId        发送出去的消息ID
     * @param articleIndex 要删除的文章在图文消息中的位置，第一篇编号为1，该字段不填或填0会删除全部文章
     */
    void delete(Long msgId, Integer articleIndex) throws WxErrorException;


    /**
     * 获取群发速度
     * https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Batch_Sends_and_Originality_Checks.html#9
     */
    WxMpMassSpeedGetResult messageMassSpeedGet() throws WxErrorException;


    /**
     * 设置群发速度
     * https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Batch_Sends_and_Originality_Checks.html#9
     *
     * @param speed 群发速度的级别，是一个0到4的整数，数字越大表示群发速度越慢。
     *              speed	realspeed
     *              0	80w/分钟
     *              1	60w/分钟
     *              2	45w/分钟
     *              3	30w/分钟
     *              4	10w/分钟
     */
    void messageMassSpeedSet(Integer speed) throws WxErrorException;


    /**
     * 查询群发消息发送状态【订阅号与服务号认证后均可用】
     * https://developers.weixin.qq.com/doc/offiaccount/Message_Management/Batch_Sends_and_Originality_Checks.html#%E6%9F%A5%E8%AF%A2%E7%BE%A4%E5%8F%91%E6%B6%88%E6%81%AF%E5%8F%91%E9%80%81%E7%8A%B6%E6%80%81%E3%80%90%E8%AE%A2%E9%98%85%E5%8F%B7%E4%B8%8E%E6%9C%8D%E5%8A%A1%E5%8F%B7%E8%AE%A4%E8%AF%81%E5%90%8E%E5%9D%87%E5%8F%AF%E7%94%A8%E3%80%91
     *
     * @param msgId 群发消息后返回的消息id
     * @return 消息发送后的状态，SEND_SUCCESS表示发送成功，SENDING表示发送中，SEND_FAIL表示发送失败，DELETE表示已删除
     */
    WxMpMassGetResult messageMassGet(Long msgId) throws WxErrorException;

}
