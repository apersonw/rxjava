package org.rxjava.third.tencent.weixin.mp.api;

import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.mp.bean.tag.WxTagListUser;
import org.rxjava.third.tencent.weixin.mp.bean.tag.WxUserTag;

import java.util.List;

/**
 * 用户标签管理相关接口
 */
public interface WxMpUserTagService {
    /**
     * 创建标签
     * 一个公众号，最多可以创建100个标签。
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/create?access_token=ACCESS_TOKEN
     *
     * @param name 标签名字（30个字符以内）
     */
    WxUserTag tagCreate(String name) throws WxErrorException;

    /**
     * 获取公众号已创建的标签
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/get?access_token=ACCESS_TOKEN
     */
    List<WxUserTag> tagGet() throws WxErrorException;

    /**
     * 编辑标签
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/update?access_token=ACCESS_TOKEN
     */
    Boolean tagUpdate(Long tagId, String name) throws WxErrorException;

    /**
     * 删除标签
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/delete?access_token=ACCESS_TOKEN
     */
    Boolean tagDelete(Long tagId) throws WxErrorException;

    /**
     * 获取标签下粉丝列表
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/user/tag/get?access_token=ACCESS_TOKEN
     */
    WxTagListUser tagListUser(Long tagId, String nextOpenid)
            throws WxErrorException;

    /**
     * 批量为用户打标签
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/members/batchtagging?access_token=ACCESS_TOKEN
     */
    boolean batchTagging(Long tagId, String[] openids) throws WxErrorException;

    /**
     * 批量为用户取消标签
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/members/batchuntagging?access_token=ACCESS_TOKEN
     */
    boolean batchUntagging(Long tagId, String[] openids) throws WxErrorException;


    /**
     * 获取用户身上的标签列表
     * 详情请见：<a href="http://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1421140837&token=&lang=zh_CN">用户标签管理</a>
     * 接口url格式： https://api.weixin.qq.com/cgi-bin/tags/getidlist?access_token=ACCESS_TOKEN
     *
     * @return 标签Id的列表
     */
    List<Long> userTagList(String openid) throws WxErrorException;

}
