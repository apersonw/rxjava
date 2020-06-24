package org.rxjava.third.tencent.weixin.mp.api.impl;

import com.google.gson.JsonObject;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.time.FastDateFormat;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.mp.api.WxMpDataCubeService;
import org.rxjava.third.tencent.weixin.mp.api.WxMpService;
import org.rxjava.third.tencent.weixin.mp.bean.datacube.*;
import org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl;

import java.text.Format;
import java.util.Date;
import java.util.List;

import static org.rxjava.third.tencent.weixin.mp.enums.WxMpApiUrl.DataCube.*;

/**
 */
@RequiredArgsConstructor
public class WxMpDataCubeServiceImpl implements WxMpDataCubeService {
    private final Format dateFormat = FastDateFormat.getInstance("yyyy-MM-dd");

    private final WxMpService wxMpService;

    @Override
    public List<WxDataCubeUserSummary> getUserSummary(Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMpService.post(GET_USER_SUMMARY, buildParams(beginDate, endDate));
        return WxDataCubeUserSummary.fromJson(responseContent);
    }

    @Override
    public List<WxDataCubeUserCumulate> getUserCumulate(Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMpService.post(GET_USER_CUMULATE, buildParams(beginDate, endDate));
        return WxDataCubeUserCumulate.fromJson(responseContent);
    }

    @Override
    public List<WxDataCubeArticleResult> getArticleSummary(Date beginDate, Date endDate) throws WxErrorException {
        return this.getArticleResults(GET_ARTICLE_SUMMARY, beginDate, endDate);
    }

    @Override
    public List<WxDataCubeArticleTotal> getArticleTotal(Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMpService.post(GET_ARTICLE_TOTAL, buildParams(beginDate, endDate));
        return WxDataCubeArticleTotal.fromJson(responseContent);
    }

    @Override
    public List<WxDataCubeArticleResult> getUserRead(Date beginDate, Date endDate) throws WxErrorException {
        return this.getArticleResults(GET_USER_READ, beginDate, endDate);
    }

    @Override
    public List<WxDataCubeArticleResult> getUserReadHour(Date beginDate, Date endDate) throws WxErrorException {
        return this.getArticleResults(GET_USER_READ_HOUR, beginDate, endDate);
    }

    @Override
    public List<WxDataCubeArticleResult> getUserShare(Date beginDate, Date endDate) throws WxErrorException {
        return this.getArticleResults(GET_USER_SHARE, beginDate, endDate);
    }

    @Override
    public List<WxDataCubeArticleResult> getUserShareHour(Date beginDate, Date endDate) throws WxErrorException {
        return this.getArticleResults(GET_USER_SHARE_HOUR, beginDate, endDate);
    }

    private List<WxDataCubeArticleResult> getArticleResults(WxMpApiUrl url, Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMpService.post(url, buildParams(beginDate, endDate));
        return WxDataCubeArticleResult.fromJson(responseContent);
    }

    @Override
    public List<WxDataCubeMsgResult> getUpstreamMsg(Date beginDate, Date endDate) throws WxErrorException {
        return this.getUpstreamMsg(GET_UPSTREAM_MSG, beginDate, endDate);
    }

    @Override
    public List<WxDataCubeMsgResult> getUpstreamMsgHour(Date beginDate, Date endDate) throws WxErrorException {
        return this.getUpstreamMsg(GET_UPSTREAM_MSG_HOUR, beginDate, endDate);
    }

    @Override
    public List<WxDataCubeMsgResult> getUpstreamMsgWeek(Date beginDate, Date endDate) throws WxErrorException {
        return this.getUpstreamMsg(GET_UPSTREAM_MSG_WEEK, beginDate, endDate);
    }

    @Override
    public List<WxDataCubeMsgResult> getUpstreamMsgMonth(Date beginDate, Date endDate) throws WxErrorException {
        return this.getUpstreamMsg(GET_UPSTREAM_MSG_MONTH, beginDate, endDate);
    }

    @Override
    public List<WxDataCubeMsgResult> getUpstreamMsgDist(Date beginDate, Date endDate) throws WxErrorException {
        return this.getUpstreamMsg(GET_UPSTREAM_MSG_DIST, beginDate, endDate);
    }

    @Override
    public List<WxDataCubeMsgResult> getUpstreamMsgDistWeek(Date beginDate, Date endDate) throws WxErrorException {
        return this.getUpstreamMsg(GET_UPSTREAM_MSG_DIST_WEEK, beginDate, endDate);
    }

    @Override
    public List<WxDataCubeMsgResult> getUpstreamMsgDistMonth(Date beginDate, Date endDate) throws WxErrorException {
        return this.getUpstreamMsg(GET_UPSTREAM_MSG_DIST_MONTH, beginDate, endDate);
    }

    private List<WxDataCubeMsgResult> getUpstreamMsg(WxMpApiUrl url, Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMpService.post(url, buildParams(beginDate, endDate));
        return WxDataCubeMsgResult.fromJson(responseContent);
    }

    @Override
    public List<WxDataCubeInterfaceResult> getInterfaceSummary(Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMpService.post(GET_INTERFACE_SUMMARY, buildParams(beginDate, endDate));
        return WxDataCubeInterfaceResult.fromJson(responseContent);
    }

    private String buildParams(Date beginDate, Date endDate) {
        JsonObject param = new JsonObject();
        param.addProperty("begin_date", this.dateFormat.format(beginDate));
        param.addProperty("end_date", this.dateFormat.format(endDate));
        return param.toString();
    }

    @Override
    public List<WxDataCubeInterfaceResult> getInterfaceSummaryHour(Date beginDate, Date endDate) throws WxErrorException {
        String responseContent = this.wxMpService.post(GET_INTERFACE_SUMMARY_HOUR, buildParams(beginDate, endDate));
        return WxDataCubeInterfaceResult.fromJson(responseContent);
    }
}
