package org.rxjava.third.tencent.weixin.cp.api.impl;

import lombok.RequiredArgsConstructor;
import org.rxjava.third.tencent.weixin.common.bean.menu.WxMenu;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.cp.api.WxCpMenuService;
import org.rxjava.third.tencent.weixin.cp.api.WxCpService;
import org.rxjava.third.tencent.weixin.cp.util.json.WxCpGsonBuilder;

import static org.rxjava.third.tencent.weixin.cp.constant.WxCpApiPathConsts.Menu.*;

/**
 * 菜单管理相关接口.
 * Created by Binary Wang on 2017-6-25.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@RequiredArgsConstructor
public class WxCpMenuServiceImpl implements WxCpMenuService {
    private final WxCpService mainService;

    @Override
    public void create(WxMenu menu) throws WxErrorException {
        this.create(this.mainService.getWxCpConfigStorage().getAgentId(), menu);
    }

    @Override
    public void create(Integer agentId, WxMenu menu) throws WxErrorException {
        String url = String.format(this.mainService.getWxCpConfigStorage().getApiUrl(MENU_CREATE), agentId);
        this.mainService.post(url, menu.toJson());
    }

    @Override
    public void delete() throws WxErrorException {
        this.delete(this.mainService.getWxCpConfigStorage().getAgentId());
    }

    @Override
    public void delete(Integer agentId) throws WxErrorException {
        String url = String.format(this.mainService.getWxCpConfigStorage().getApiUrl(MENU_DELETE), agentId);
        this.mainService.get(url, null);
    }

    @Override
    public WxMenu get() throws WxErrorException {
        return this.get(this.mainService.getWxCpConfigStorage().getAgentId());
    }

    @Override
    public WxMenu get(Integer agentId) throws WxErrorException {
        String url = String.format(this.mainService.getWxCpConfigStorage().getApiUrl(MENU_GET), agentId);
        try {
            String resultContent = this.mainService.get(url, null);
            return WxCpGsonBuilder.create().fromJson(resultContent, WxMenu.class);
        } catch (WxErrorException e) {
            // 46003 不存在的菜单数据
            if (e.getError().getErrorCode() == 46003) {
                return null;
            }
            throw e;
        }
    }
}
