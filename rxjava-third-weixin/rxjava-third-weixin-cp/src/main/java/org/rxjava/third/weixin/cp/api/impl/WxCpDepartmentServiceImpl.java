package top.rxjava.third.weixin.cp.api.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.util.json.GsonHelper;
import top.rxjava.third.weixin.cp.api.WxCpDepartmentService;
import top.rxjava.third.weixin.cp.api.WxCpService;
import top.rxjava.third.weixin.cp.bean.WxCpDepart;
import top.rxjava.third.weixin.cp.util.json.WxCpGsonBuilder;

import java.util.List;

import static top.rxjava.third.weixin.cp.constant.WxCpApiPathConsts.Department.*;

/**
 * 部门管理接口
 */
@RequiredArgsConstructor
public class WxCpDepartmentServiceImpl implements WxCpDepartmentService {
    private final WxCpService mainService;

    @Override
    public Long create(WxCpDepart depart) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_CREATE);
        String responseContent = this.mainService.post(url, depart.toJson());
        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
        return GsonHelper.getAsLong(tmpJsonElement.getAsJsonObject().get("id"));
    }

    @Override
    public void update(WxCpDepart group) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_UPDATE);
        this.mainService.post(url, group.toJson());
    }

    @Override
    public void delete(Long departId) throws WxErrorException {
        String url = String.format(this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_DELETE), departId);
        this.mainService.get(url, null);
    }

    @Override
    public List<WxCpDepart> list(Long id) throws WxErrorException {
        String url = this.mainService.getWxCpConfigStorage().getApiUrl(DEPARTMENT_LIST);
        if (id != null) {
            url += "?id=" + id;
        }

        String responseContent = this.mainService.get(url, null);
        JsonElement tmpJsonElement = new JsonParser().parse(responseContent);
        return WxCpGsonBuilder.create()
                .fromJson(tmpJsonElement.getAsJsonObject().get("department"),
                        new TypeToken<List<WxCpDepart>>() {
                        }.getType()
                );
    }
}
