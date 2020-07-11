package org.rxjava.third.weixin.pay.v3;

import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;

public interface Credentials {

    String getSchema();

    String getToken(HttpUriRequest request) throws IOException;
}