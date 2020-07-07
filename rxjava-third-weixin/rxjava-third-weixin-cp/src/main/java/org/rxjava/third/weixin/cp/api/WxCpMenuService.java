package org.rxjava.third.weixin.cp.api;

import org.rxjava.third.weixin.common.bean.menu.WxMenu;
import org.rxjava.third.weixin.common.error.WxErrorException;

/**
 * 菜单管理相关接口
 */
public interface WxCpMenuService {

    /**
     * 自定义菜单创建接口
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
     * <p>
     * 注意: 这个方法使用WxCpConfigStorage里的agentId
     *
     * @param menu 菜单对象
     * @see #create(Integer, WxMenu)
     */
    void create(WxMenu menu) throws WxErrorException;

    /**
     * 自定义菜单创建接口
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单创建接口
     * <p>
     * 注意: 这个方法不使用WxCpConfigStorage里的agentId，需要开发人员自己给出
     *
     * @param agentId 企业号应用的id
     * @param menu    菜单对象
     * @see #create(org.rxjava.third.weixin.common.bean.menu.WxMenu)
     */
    void create(Integer agentId, WxMenu menu) throws WxErrorException;

    /**
     * 自定义菜单删除接口
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单删除接口
     * <p>
     * 注意: 这个方法使用WxCpConfigStorage里的agentId
     *
     * @see #delete(Integer)
     */
    void delete() throws WxErrorException;

    /**
     * 自定义菜单删除接口
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单删除接口
     * <p>
     * 注意: 这个方法不使用WxCpConfigStorage里的agentId，需要开发人员自己给出
     *
     * @param agentId 企业号应用的id
     * @see #delete()
     */
    void delete(Integer agentId) throws WxErrorException;

    /**
     * 自定义菜单查询接口
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单查询接口
     * <p>
     * 注意: 这个方法使用WxCpConfigStorage里的agentId
     *
     * @see #get(Integer)
     */
    WxMenu get() throws WxErrorException;

    /**
     * 自定义菜单查询接口
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=自定义菜单查询接口
     * <p>
     * 注意: 这个方法不使用WxCpConfigStorage里的agentId，需要开发人员自己给出
     *
     * @param agentId 企业号应用的id
     * @see #get()
     */
    WxMenu get(Integer agentId) throws WxErrorException;
}
