package top.rxjava.third.weixin.cp.api.impl;

import lombok.RequiredArgsConstructor;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.util.json.WxGsonBuilder;
import top.rxjava.third.weixin.cp.api.WxCpService;
import top.rxjava.third.weixin.cp.api.WxCpTaskCardService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static top.rxjava.third.weixin.cp.constant.WxCpApiPathConsts.TaskCard.UPDATE_TASK_CARD;

/**
 * 任务卡片管理接口.
 */
@RequiredArgsConstructor
public class WxCpTaskCardServiceImpl implements WxCpTaskCardService {
    private final WxCpService mainService;

    @Override
    public void update(List<String> userIds, String taskId, String clickedKey) throws WxErrorException {
        Integer agentId = this.mainService.getWxCpConfigStorage().getAgentId();

        Map<String, Object> data = new HashMap<>(4);
        data.put("userids", userIds);
        data.put("agentid", agentId);
        data.put("task_id", taskId);
        data.put("clicked_key", clickedKey);

        String url = this.mainService.getWxCpConfigStorage().getApiUrl(UPDATE_TASK_CARD);
        this.mainService.post(url, WxGsonBuilder.create().toJson(data));
    }
}
