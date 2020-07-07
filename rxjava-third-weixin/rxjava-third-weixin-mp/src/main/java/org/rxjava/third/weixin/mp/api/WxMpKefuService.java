package org.rxjava.third.weixin.mp.api;

import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.rxjava.third.weixin.mp.bean.kefu.request.WxMpKfAccountRequest;
import org.rxjava.third.weixin.mp.bean.kefu.result.*;

import java.io.File;
import java.util.Date;

/**
 * 客服接口.
 * 注意：命名采用kefu拼音的原因是：其英文CustomerService如果再加上Service后缀显得有点啰嗦，如果不加又显得表意不完整。
 */
public interface WxMpKefuService {
    /**
     * 发送客服消息
     * 详情请见: <a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140547&token=&lang=zh_CN">发送客服消息</a>
     * 接口url格式：https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
     *
     * @throws WxErrorException 异常
     */
    boolean sendKefuMessage(WxMpKefuMessage message) throws WxErrorException;

    //*******************客服管理接口***********************//

    /**
     * 获取客服基本信息
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">客服管理</a>
     * 接口url格式：https://api.weixin.qq.com/cgi-bin/customservice/getkflist?access_token=ACCESS_TOKEN
     *
     * @throws WxErrorException 异常
     */
    WxMpKfList kfList() throws WxErrorException;

    /**
     * 获取在线客服接待信息
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">客服管理</a>
     * 接口url格式：https://api.weixin.qq.com/cgi-bin/customservice/getonlinekflist?access_token=ACCESS_TOKEN
     *
     * @throws WxErrorException 异常
     */
    WxMpKfOnlineList kfOnlineList() throws WxErrorException;

    /**
     * 添加客服账号
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">客服管理</a>
     * 接口url格式：https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN
     *
     * @throws WxErrorException 异常
     */
    boolean kfAccountAdd(WxMpKfAccountRequest request) throws WxErrorException;

    /**
     * 设置客服信息（即更新客服信息）
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">客服管理</a>
     * 接口url格式：https://api.weixin.qq.com/customservice/kfaccount/update?access_token=ACCESS_TOKEN
     */
    boolean kfAccountUpdate(WxMpKfAccountRequest request) throws WxErrorException;

    /**
     * 设置客服信息（即更新客服信息）
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">客服管理</a>
     * 接口url格式：https://api.weixin.qq.com/customservice/kfaccount/inviteworker?access_token=ACCESS_TOKEN
     *
     * @throws WxErrorException 异常
     */
    boolean kfAccountInviteWorker(WxMpKfAccountRequest request) throws WxErrorException;

    /**
     * 上传客服头像
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">客服管理</a>
     * 接口url格式：https://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT
     *
     * @throws WxErrorException 异常
     */
    boolean kfAccountUploadHeadImg(String kfAccount, File imgFile) throws WxErrorException;

    /**
     * 删除客服账号
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044813&token=&lang=zh_CN">客服管理</a>
     * 接口url格式：https://api.weixin.qq.com/customservice/kfaccount/del?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT
     *
     * @throws WxErrorException 异常
     */
    boolean kfAccountDel(String kfAccount) throws WxErrorException;

    //*******************客服会话控制接口***********************//

    /**
     * 创建会话
     * 此接口在客服和用户之间创建一个会话，如果该客服和用户会话已存在，则直接返回0。指定的客服帐号必须已经绑定微信号且在线。
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN">客服会话控制接口</a>
     * 接口url格式： https://api.weixin.qq.com/customservice/kfsession/create?access_token=ACCESS_TOKEN
     *
     * @throws WxErrorException 异常
     */
    boolean kfSessionCreate(String openid, String kfAccount) throws WxErrorException;

    /**
     * 关闭会话
     * 开发者可以使用本接口，关闭一个会话。
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN">客服会话控制接口</a>
     * 接口url格式： https://api.weixin.qq.com/customservice/kfsession/close?access_token=ACCESS_TOKEN
     *
     * @throws WxErrorException 异常
     */
    boolean kfSessionClose(String openid, String kfAccount) throws WxErrorException;

    /**
     * 获取客户的会话状态
     * 此接口获取一个客户的会话，如果不存在，则kf_account为空。
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN">客服会话控制接口</a>
     * 接口url格式： https://api.weixin.qq.com/customservice/kfsession/getsession?access_token=ACCESS_TOKEN&openid=OPENID
     *
     * @throws WxErrorException 异常
     */
    WxMpKfSessionGetResult kfSessionGet(String openid) throws WxErrorException;

    /**
     * 获取客服的会话列表
     * 开发者可以通过本接口获取某个客服正在接待的会话列表。
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN">客服会话控制</a>
     * 接口url格式： https://api.weixin.qq.com/customservice/kfsession/getsessionlist?access_token=ACCESS_TOKEN&kf_account=KFACCOUNT
     *
     * @throws WxErrorException 异常
     */
    WxMpKfSessionList kfSessionList(String kfAccount) throws WxErrorException;

    /**
     * 获取未接入会话列表
     * 开发者可以通过本接口获取当前正在等待队列中的会话列表，此接口最多返回最早进入队列的100个未接入会话。
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1458044820&token=&lang=zh_CN">客服会话控制</a>
     * 接口url格式： https://api.weixin.qq.com/customservice/kfsession/getwaitcase?access_token=ACCESS_TOKEN
     *
     * @throws WxErrorException 异常
     */
    WxMpKfSessionWaitCaseList kfSessionGetWaitCase() throws WxErrorException;

    //*******************获取聊天记录的接口***********************//

    /**
     * 获取聊天记录（原始接口）
     * 此接口返回的聊天记录中，对于图片、语音、视频，分别展示成文本格式的[image]、[voice]、[video]
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1464937269_mUtmK&token=&lang=zh_CN">获取聊天记录</a>
     * 接口url格式： https://api.weixin.qq.com/customservice/msgrecord/getmsglist?access_token=ACCESS_TOKEN
     *
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @param msgId     消息id顺序从小到大，从1开始
     * @param number    每次获取条数，最多10000条
     * @return 聊天记录对象
     * @throws WxErrorException 异常
     */
    WxMpKfMsgList kfMsgList(Date startTime, Date endTime, Long msgId, Integer number) throws WxErrorException;

    /**
     * 获取聊天记录（优化接口，返回指定时间段内所有的聊天记录）
     * 此接口返回的聊天记录中，对于图片、语音、视频，分别展示成文本格式的[image]、[voice]、[video]
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1464937269_mUtmK&token=&lang=zh_CN">获取聊天记录</a>
     * 接口url格式： https://api.weixin.qq.com/customservice/msgrecord/getmsglist?access_token=ACCESS_TOKEN
     *
     * @param startTime 起始时间
     * @param endTime   结束时间
     * @return 聊天记录对象
     * @throws WxErrorException 异常
     */
    WxMpKfMsgList kfMsgList(Date startTime, Date endTime) throws WxErrorException;

    /**
     * 客服输入状态
     * 开发者可通过调用“客服输入状态”接口，返回客服当前输入状态给用户。
     * 此接口需要客服消息接口权限。
     * 如果不满足发送客服消息的触发条件，则无法下发输入状态。
     * 下发输入状态，需要客服之前30秒内跟用户有过消息交互。
     * 在输入状态中（持续15s），不可重复下发输入态。
     * 在输入状态中，如果向用户下发消息，会同时取消输入状态。
     * <p>
     * 详情请见：<a href="https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140547">客服输入状态</a>
     * 接口url格式：https://api.weixin.qq.com/cgi-bin/message/custom/typing?access_token=ACCESS_TOKEN
     *
     * @param openid  用户id
     * @param command "Typing"：对用户下发“正在输入"状态 "CancelTyping"：取消对用户的”正在输入"状态
     * @throws WxErrorException 异常
     */
    boolean sendKfTypingState(String openid, String command) throws WxErrorException;
}
