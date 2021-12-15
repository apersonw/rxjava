package top.rxjava.third.weixin.mp.util.requestexecuter.material;

import com.google.common.collect.ImmutableMap;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import top.rxjava.third.weixin.common.WxType;
import top.rxjava.third.weixin.common.error.WxError;
import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.common.util.http.RequestHttp;
import top.rxjava.third.weixin.common.util.http.apache.Utf8ResponseHandler;
import top.rxjava.third.weixin.common.util.json.WxGsonBuilder;
import top.rxjava.third.weixin.mp.bean.material.WxMpMaterialNews;
import top.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * httpclient 实现的素材请求执行器.
 */
public class MaterialNewsInfoApacheHttpRequestExecutor
        extends MaterialNewsInfoRequestExecutor<CloseableHttpClient, HttpHost> {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public MaterialNewsInfoApacheHttpRequestExecutor(RequestHttp requestHttp) {
        super(requestHttp);
    }

    @Override
    public WxMpMaterialNews execute(String uri, String materialId, WxType wxType) throws WxErrorException, IOException {
        HttpPost httpPost = new HttpPost(uri);
        if (requestHttp.getRequestHttpProxy() != null) {
            RequestConfig config = RequestConfig.custom().setProxy(requestHttp.getRequestHttpProxy()).build();
            httpPost.setConfig(config);
        }

        httpPost.setEntity(new StringEntity(WxGsonBuilder.create().toJson(ImmutableMap.of("media_id", materialId))));
        try (CloseableHttpResponse response = requestHttp.getRequestHttpClient().execute(httpPost)) {
            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
            this.logger.debug("响应原始数据：{}", responseContent);
            WxError error = WxError.fromJson(responseContent, WxType.MP);
            if (error.getErrorCode() != 0) {
                throw new WxErrorException(error);
            } else {
                return WxMpGsonBuilder.create().fromJson(responseContent, WxMpMaterialNews.class);
            }
        } finally {
            httpPost.releaseConnection();
        }
    }
}
