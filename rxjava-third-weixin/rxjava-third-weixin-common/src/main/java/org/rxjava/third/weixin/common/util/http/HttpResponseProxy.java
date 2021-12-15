package top.rxjava.third.weixin.common.util.http;

import jodd.http.HttpResponse;
import okhttp3.Response;
import org.apache.http.Header;
import org.apache.http.client.methods.CloseableHttpResponse;
import top.rxjava.third.weixin.common.error.WxError;
import top.rxjava.third.weixin.common.error.WxErrorException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 三种http框架的response代理类，方便提取公共方法
 */
public class HttpResponseProxy {
    private static final Pattern PATTERN = Pattern.compile(".*filename=\"(.*)\"");

    private CloseableHttpResponse apacheHttpResponse;
    private HttpResponse joddHttpResponse;
    private Response okHttpResponse;

    public HttpResponseProxy(CloseableHttpResponse apacheHttpResponse) {
        this.apacheHttpResponse = apacheHttpResponse;
    }

    public HttpResponseProxy(HttpResponse joddHttpResponse) {
        this.joddHttpResponse = joddHttpResponse;
    }

    public HttpResponseProxy(Response okHttpResponse) {
        this.okHttpResponse = okHttpResponse;
    }

    public String getFileName() throws WxErrorException {
        //由于对象只能由一个构造方法实现，因此三个response对象必定且只有一个不为空
        if (this.apacheHttpResponse != null) {
            return this.getFileName(this.apacheHttpResponse);
        }

        if (this.joddHttpResponse != null) {
            return this.getFileName(this.joddHttpResponse);
        }

        if (this.okHttpResponse != null) {
            return this.getFileName(this.okHttpResponse);
        }

        //cannot happen
        return null;
    }

    private String getFileName(CloseableHttpResponse response) throws WxErrorException {
        Header[] contentDispositionHeader = response.getHeaders("Content-disposition");
        if (contentDispositionHeader == null || contentDispositionHeader.length == 0) {
            throw new WxErrorException(WxError.builder().errorMsg("无法获取到文件名").errorCode(99999).build());
        }

        return this.extractFileNameFromContentString(contentDispositionHeader[0].getValue());
    }

    private String getFileName(HttpResponse response) throws WxErrorException {
        String content = response.header("Content-disposition");
        return this.extractFileNameFromContentString(content);
    }

    private String getFileName(Response response) throws WxErrorException {
        String content = response.header("Content-disposition");
        return this.extractFileNameFromContentString(content);
    }

    private String extractFileNameFromContentString(String content) throws WxErrorException {
        if (content == null || content.length() == 0) {
            throw new WxErrorException(WxError.builder().errorMsg("无法获取到文件名").errorCode(99999).build());
        }

        Matcher m = PATTERN.matcher(content);
        if (m.matches()) {
            return m.group(1);
        }

        throw new WxErrorException(WxError.builder().errorMsg("无法获取到文件名").errorCode(99999).build());
    }

}
