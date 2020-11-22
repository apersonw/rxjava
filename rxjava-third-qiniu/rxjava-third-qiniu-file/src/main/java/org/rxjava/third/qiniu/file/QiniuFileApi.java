package org.rxjava.third.qiniu.file;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import com.qiniu.util.StringMap;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.common.core.exception.ErrorMessageException;
import org.rxjava.spring.utils.JsonUtils;
import org.rxjava.spring.utils.UuidUtils;
import reactor.core.publisher.Mono;

import java.io.*;

/**
 * 七牛云Api
 */
public class QiniuFileApi implements Serializable {
    private final String accessKey;
    private final String secretKey;
    private final String bucket;
    private final String token;
    private final UploadManager uploadManager;

    private QiniuFileApi() {
        throw new RuntimeException("禁止反射破坏单例");
    }

    private QiniuFileApi(String accessKey, String secretKey, String bucket) {
        if (StringUtils.isAnyEmpty(accessKey, secretKey, bucket)) {
            throw ErrorMessageException.of("stringNotEmpty");
        }
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucket = bucket;

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        this.uploadManager = new UploadManager(cfg);
        //获取凭证
        Auth auth = Auth.create(accessKey, secretKey);
        this.token = auth.uploadToken(bucket);
    }

    public static QiniuFileApi getInstance(String accessKey, String secretKey, String bucket) {
        return LazyHolder.lazy(accessKey, secretKey, bucket);
    }

    private static class LazyHolder {
        private static QiniuFileApi lazy(String accessKey, String secretKey, String bucket) {
            return new QiniuFileApi(accessKey, secretKey, bucket);
        }
    }

    /**
     * 禁止序列化破坏单例
     */
    private Object readResolve() {
        return LazyHolder.lazy(accessKey, secretKey, bucket);
    }

    /**
     * 七牛云上传文件
     */
    public Mono<DefaultPutRet> uploadFile() throws FileNotFoundException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("/Users/happy/rxjava/test.txt")));
        return Mono.create(monoSink -> {
            try {
                uploadManager.asyncPut(
                        IOUtils.toByteArray(bufferedInputStream),
                        "testdev-" + UuidUtils.getInstance().randomUuidToBase64(),
                        token,
                        new StringMap().put("testdevname", "testdevname"),
                        null,
                        true,
                        ((key1, response) -> {
                            try {
                                //解析上传成功的结果
                                DefaultPutRet putRet = JsonUtils.deserialize(response.bodyString(), DefaultPutRet.class);
                                monoSink.success(putRet);
                            } catch (IOException e) {
                                monoSink.error(e);
                            }
                        }));
            } catch (IOException e) {
                monoSink.error(e);
            }
        });
    }
}
