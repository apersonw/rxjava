package org.rxjava.third.weixin.mp.api.impl;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.mp.api.WxMpCommentService;
import org.rxjava.third.weixin.mp.api.WxMpService;
import org.rxjava.third.weixin.mp.bean.comment.WxMpCommentListVo;

import static org.rxjava.third.weixin.mp.enums.WxMpApiUrl.Comment.*;

/**
 */
@RequiredArgsConstructor
public class WxMpCommentServiceImpl implements WxMpCommentService {
    private final WxMpService wxMpService;

    @Override
    public void open(String msgDataId, Integer index) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("msg_data_id", msgDataId);
        if (index != null) {
            json.addProperty("index", index);
        }

        this.wxMpService.post(OPEN, json.toString());
    }

    @Override
    public void close(String msgDataId, Integer index) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("msg_data_id", msgDataId);
        if (index != null) {
            json.addProperty("index", index);
        }

        this.wxMpService.post(CLOSE, json.toString());
    }

    @Override
    public WxMpCommentListVo list(String msgDataId, Integer index, int begin, int count, int type) throws WxErrorException {
        JsonObject json = new JsonObject();
        json.addProperty("msg_data_id", msgDataId);
        json.addProperty("begin", begin);
        json.addProperty("count", count);
        json.addProperty("type", type);

        if (index != null) {
            json.addProperty("index", index);
        }

        return WxMpCommentListVo.fromJson(this.wxMpService.post(LIST, json.toString()));
    }

    @Override
    public void markElect(String msgDataId, Integer index, Long userCommentId) throws WxErrorException {
        JsonObject json = this.buildJson(msgDataId, index, userCommentId);
        this.wxMpService.post(MARK_ELECT, json.toString());
    }

    @Override
    public void unmarkElect(String msgDataId, Integer index, Long userCommentId) throws WxErrorException {
        JsonObject json = this.buildJson(msgDataId, index, userCommentId);
        this.wxMpService.post(UNMARK_ELECT, json.toString());
    }

    @Override
    public void delete(String msgDataId, Integer index, Long userCommentId) throws WxErrorException {
        JsonObject json = this.buildJson(msgDataId, index, userCommentId);

        this.wxMpService.post(DELETE, json.toString());
    }

    @Override
    public void replyAdd(String msgDataId, Integer index, Long userCommentId, String content) throws WxErrorException {
        JsonObject json = this.buildJson(msgDataId, index, userCommentId);
        json.addProperty("content", content);

        this.wxMpService.post(REPLY_ADD, json.toString());
    }

    @Override
    public void replyDelete(String msgDataId, Integer index, Long userCommentId) throws WxErrorException {
        JsonObject json = this.buildJson(msgDataId, index, userCommentId);
        this.wxMpService.post(REPLY_DELETE, json.toString());
    }

    private JsonObject buildJson(String msgDataId, Integer index, Long userCommentId) {
        JsonObject json = new JsonObject();
        json.addProperty("msg_data_id", msgDataId);
        json.addProperty("user_comment_id", userCommentId);
        if (index != null) {
            json.addProperty("index", index);
        }
        return json;
    }
}
