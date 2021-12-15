package top.rxjava.third.weixin.common.util.http.apache;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;

/**
 * httpclient build interface.
 */
public interface ApacheHttpClientBuilder {

    /**
     * 构建httpclient实例.
     *
     * @return new instance of CloseableHttpClient
     */
    CloseableHttpClient build();

    /**
     * 代理服务器地址.
     */
    ApacheHttpClientBuilder httpProxyHost(String httpProxyHost);

    /**
     * 代理服务器端口.
     */
    ApacheHttpClientBuilder httpProxyPort(int httpProxyPort);

    /**
     * 代理服务器用户名.
     */
    ApacheHttpClientBuilder httpProxyUsername(String httpProxyUsername);

    /**
     * 代理服务器密码.
     */
    ApacheHttpClientBuilder httpProxyPassword(String httpProxyPassword);

    /**
     * ssl连接socket工厂.
     */
    ApacheHttpClientBuilder sslConnectionSocketFactory(SSLConnectionSocketFactory sslConnectionSocketFactory);
}
