package org.rxjava.service.example.person;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.rxjava.service.example.form.QrcodeCreateForm;
import org.rxjava.service.example.type.ImageType;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.io.ByteArrayOutputStream;

/**
 * 订单
 */
@Data
@RestController
public class OrderController {

    /**
     * 生成二维码
     */
    @GetMapping("qrcode")
    public Mono<ResponseEntity<ByteArrayResource>> createQrcode(
            @Valid QrcodeCreateForm form
    ) {
        return Mono.fromCallable(() -> {
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            int imageWidth = Math.min(Math.max(32, form.getWidth()), 1024);
            int imageHeight = Math.min(Math.max(32, form.getHeight()), 1024);
            BitMatrix bitMatrix = multiFormatWriter.encode(RandomStringUtils.random(100), BarcodeFormat.valueOf(form.getQrcodeType()), imageWidth, imageHeight);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            MatrixToImageWriter.writeToStream(bitMatrix, form.getType(), outputStream, new MatrixToImageConfig());
            outputStream.flush();
            byte[] bytes = outputStream.toByteArray();

            MediaType mediaType;
            ImageType imageType = ImageType.valueOf(form.getType());
            if (imageType == ImageType.jpg) {
                mediaType = MediaType.IMAGE_JPEG;
            } else {
                mediaType = MediaType.IMAGE_PNG;
            }
            return ResponseEntity.ok()
                    .contentLength(bytes.length)
                    .contentType(mediaType)
                    .body(new ByteArrayResource(bytes));
        });
    }
}
