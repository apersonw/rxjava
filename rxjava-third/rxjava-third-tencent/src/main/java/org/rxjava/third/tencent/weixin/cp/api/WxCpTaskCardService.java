package org.rxjava.third.tencent.weixin.cp.api;

import org.rxjava.third.tencent.weixin.common.error.WxErrorException;

import java.util.List;

/**
 * 任务卡片管理接口.
 */
public interface WxCpTaskCardService {

    /**
     * 更新任务卡片消息状态
     * 详情请见: https://work.weixin.qq.com/api/doc#90000/90135/91579
     * <p>
     * 注意: 这个方法使用WxCpConfigStorage里的agentId
     *
     * @param userIds    企业的成员ID列表
     * @param taskId     任务卡片ID
     * @param clickedKey 已点击按钮的Key
     */
    void update(List<String> userIds, String taskId, String clickedKey) throws WxErrorException;
}
