package org.rxjava.third.qiniu.file.info;

import com.qiniu.util.IOUtils;
import lombok.Cleanup;
import lombok.Data;
import org.rxjava.common.core.utils.UuidUtils;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;
import org.springframework.util.MimeType;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

/**
 * 上传文件信息
 */
@Data
public class UploadFileInfo {
    private FilePart filePart;
    private byte[] bytes;
    private String hash;
    private String mime;

    public UploadFileInfo(FilePart filePart) {
        this.filePart = filePart;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getHash() {
        return hash;
    }

    public String getMime() {
        return mime;
    }

    public Mono<UploadFileInfo> init() {
        String id = UuidUtils.getInstance().randomUuidToBase64();
        return Mono.fromCallable(() -> Files.createTempFile("testdev-" + id, ".temp"))
                .publishOn(Schedulers.elastic())
                .subscribeOn(Schedulers.elastic())
                .flatMap(temp -> filePart
                        .transferTo(temp.toFile())
                        .then(Mono.fromCallable(() -> {
                            @Cleanup
                            BufferedInputStream input = new BufferedInputStream(new FileInputStream(temp.toFile()));
                            bytes = IOUtils.toByteArray(input);
                            hash = Base64Utils.encodeToUrlSafeString(DigestUtils.md5Digest(bytes));
                            mime = Optional
                                    .ofNullable(filePart.headers().getContentType())
                                    .map(MimeType::toString)
                                    .orElse(null);
                            return this;
                        }))
                        .doOnSuccess(r -> {
                            try {
                                Files.delete(temp);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        })
                        .doOnError(throwable -> {
                            try {
                                Files.delete(temp);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        })
                        .publishOn(Schedulers.elastic())
                        .subscribeOn(Schedulers.elastic()));
    }
}
