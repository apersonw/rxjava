package org.rxjava.third.qiniu;

import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.IOUtils;
import com.qiniu.util.StringMap;
import org.rxjava.common.core.utils.JsonUtils;
import org.rxjava.common.core.utils.UUIDUtils;
import reactor.core.publisher.Mono;

import java.io.*;

public class QiniuApi {
    /**
     * 七牛云上传文件
     */
    public Mono<DefaultPutRet> uploadFile() throws FileNotFoundException {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region0());
        UploadManager uploadManager = new UploadManager(cfg);
        //获取凭证
        String accessKey = "qZtdLJlYqO9kMZLJBxjJzmZJsMFc_cJc_0h-KuSI";
        String secretKey = "t_rKhd0ThyBP-hn_Na_XPAAimnxdM9_Rdifn5DLn";
        String bucket = "flcloud-dev";
        Auth auth = Auth.create(accessKey, secretKey);
        String token = auth.uploadToken(bucket);

        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(new File("/Users/happy/rxjava/test.txt")));

        return Mono.create(monoSink -> {
            try {
                uploadManager.asyncPut(
                        IOUtils.toByteArray(bufferedInputStream),
                        "testdev-" + UUIDUtils.randomUUIDToBase64(),
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
