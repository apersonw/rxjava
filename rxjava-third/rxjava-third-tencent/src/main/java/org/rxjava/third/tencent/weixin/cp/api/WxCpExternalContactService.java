package org.rxjava.third.tencent.weixin.cp.api;

import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.cp.bean.*;

import java.util.Date;
import java.util.List;

/**
 * 外部联系人管理接口，企业微信的外部联系人的接口和通讯录接口已经拆离
 * Created by Joe Cao on 2019/6/14
 *
 * @author <a href="https://github.com/JoeCao">JoeCao</a>
 */
public interface WxCpExternalContactService {
    /**
     * 获取外部联系人详情.
     * <p>
     * 企业可通过此接口，根据外部联系人的userid，拉取外部联系人详情。权限说明：
     * 企业需要使用外部联系人管理secret所获取的accesstoken来调用
     * 第三方应用需拥有“企业客户”权限。
     * 第三方应用调用时，返回的跟进人follow_user仅包含应用可见范围之内的成员。
     *
     * @param userId 外部联系人的userid
     * @return .
     * @deprecated 建议使用 {@link #getContactDetail(String)}
     */
    @Deprecated
    WxCpUserExternalContactInfo getExternalContact(String userId) throws WxErrorException;

    /**
     * 获取客户详情.
     * <p>
     * <p>
     * 企业可通过此接口，根据外部联系人的userid（如何获取?），拉取客户详情。
     * <p>
     * 请求方式：GET（HTTPS）
     * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/externalcontact/get?access_token=ACCESS_TOKEN&external_userid=EXTERNAL_USERID
     * <p>
     * 权限说明：
     * <p>
     * 企业需要使用“客户联系”secret或配置到“可调用应用”列表中的自建应用secret所获取的accesstoken来调用（accesstoken如何获取？）；
     * 第三方/自建应用调用时，返回的跟进人follow_user仅包含应用可见范围之内的成员。
     *
     * @param userId 外部联系人的userid，注意不是企业成员的帐号
     * @return .
     * @throws WxErrorException .
     */
    WxCpUserExternalContactInfo getContactDetail(String userId) throws WxErrorException;

    /**
     * 获取客户列表.
     * <p>
     * 企业可通过此接口获取指定成员添加的客户列表。客户是指配置了客户联系功能的成员所添加的外部联系人。没有配置客户联系功能的成员，所添加的外部联系人将不会作为客户返回。
     * <p>
     * 请求方式：GET（HTTPS）
     * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/externalcontact/list?access_token=ACCESS_TOKEN&userid=USERID
     * <p>
     * 权限说明：
     * <p>
     * 企业需要使用“客户联系”secret或配置到“可调用应用”列表中的自建应用secret所获取的accesstoken来调用（accesstoken如何获取？）；
     * 第三方应用需拥有“企业客户”权限。
     * 第三方/自建应用只能获取到可见范围内的配置了客户联系功能的成员。
     *
     * @param userId 企业成员的userid
     * @return List of External wx id
     * @throws WxErrorException .
     */
    List<String> listExternalContacts(String userId) throws WxErrorException;

    /**
     * 企业和第三方服务商可通过此接口获取配置了客户联系功能的成员(Customer Contact)列表。
     * <p>
     * 企业需要使用外部联系人管理secret所获取的accesstoken来调用（accesstoken如何获取？）；
     * 第三方应用需拥有“企业客户”权限。
     * 第三方应用只能获取到可见范围内的配置了客户联系功能的成员
     *
     * @return List of CpUser id
     * @throws WxErrorException .
     */
    List<String> listFollowers() throws WxErrorException;

    /**
     * 企业和第三方可通过此接口，获取所有离职成员的客户列表，并可进一步调用离职成员的外部联系人再分配接口将这些客户重新分配给其他企业成员。
     *
     * @param page
     * @param pageSize
     * @return
     * @throws WxErrorException
     */
    WxCpUserExternalUnassignList listUnassignedList(Integer page, Integer pageSize) throws WxErrorException;

    /**
     * 企业可通过此接口，将已离职成员的外部联系人分配给另一个成员接替联系。
     *
     * @param externalUserid
     * @param handOverUserid
     * @param takeOverUserid
     * @return
     * @throws WxErrorException
     */
    WxCpBaseResp transferExternalContact(String externalUserid, String handOverUserid, String takeOverUserid) throws WxErrorException;

    /**
     * 该接口用于获取配置过客户群管理的客户群列表。
     * 企业需要使用“客户联系”secret或配置到“可调用应用”列表中的自建应用secret所获取的accesstoken来调用（accesstoken如何获取？）。
     * 暂不支持第三方调用。
     * 微信文档：https://work.weixin.qq.com/api/doc/90000/90135/92119
     */
    WxCpUserExternalGroupChatList listGroupChat(Integer pageIndex, Integer pageSize, int status, String[] userIds, String[] partyIds) throws WxErrorException;

    /**
     * 通过客户群ID，获取详情。包括群名、群成员列表、群成员入群时间、入群方式。（客户群是由具有客户群使用权限的成员创建的外部群）
     * 企业需要使用“客户联系”secret或配置到“可调用应用”列表中的自建应用secret所获取的accesstoken来调用（accesstoken如何获取？）。
     * 暂不支持第三方调用。
     * 微信文档：https://work.weixin.qq.com/api/doc/90000/90135/92122
     *
     * @param chatId
     * @return
     * @throws WxErrorException
     */
    WxCpUserExternalGroupChatInfo getGroupChat(String chatId) throws WxErrorException;

    /**
     * 企业可通过此接口获取成员联系客户的数据，包括发起申请数、新增客户数、聊天数、发送消息数和删除/拉黑成员的客户数等指标。
     * 企业需要使用“客户联系”secret或配置到“可调用应用”列表中的自建应用secret所获取的accesstoken来调用（accesstoken如何获取？）。
     * 第三方应用需拥有“企业客户”权限。
     * 第三方/自建应用调用时传入的userid和partyid要在应用的可见范围内;
     *
     * @param startTime
     * @param endTime
     * @param userIds
     * @param partyIds
     * @return
     * @throws WxErrorException
     */
    WxCpUserExternalUserBehaviorStatistic getUserBehaviorStatistic(Date startTime, Date endTime, String[] userIds, String[] partyIds) throws WxErrorException;

    /**
     * 获取指定日期全天的统计数据。注意，企业微信仅存储60天的数据。
     * 企业需要使用“客户联系”secret或配置到“可调用应用”列表中的自建应用secret所获取的accesstoken来调用（accesstoken如何获取？）。
     * 暂不支持第三方调用。
     *
     * @param startTime
     * @param orderBy
     * @param orderAsc
     * @param pageIndex
     * @param pageSize
     * @param userIds
     * @param partyIds
     * @return
     * @throws WxErrorException
     */
    WxCpUserExternalGroupChatStatistic getGroupChatStatistic(Date startTime, Integer orderBy, Integer orderAsc, Integer pageIndex, Integer pageSize, String[] userIds, String[] partyIds) throws WxErrorException;
}
