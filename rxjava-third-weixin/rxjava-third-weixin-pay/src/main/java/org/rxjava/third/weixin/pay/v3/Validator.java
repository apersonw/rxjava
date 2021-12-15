package top.rxjava.third.weixin.pay.v3;

import org.apache.http.client.methods.CloseableHttpResponse;

import java.io.IOException;

public interface Validator {
    boolean validate(CloseableHttpResponse response) throws IOException;
}
