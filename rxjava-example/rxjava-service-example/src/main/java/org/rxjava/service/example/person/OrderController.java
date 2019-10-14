package org.rxjava.service.example.person;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.rxjava.service.example.form.QrcodeCreateForm;
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
        MultiFormatWriter pdf417Writer = new MultiFormatWriter();
        //注意中文乱码问题
        try {
            int imageWidth = Math.min(Math.max(32, form.getWidth()), 1024);
            int imageHeight = Math.min(Math.max(32, form.getHeight()), 1024);
            BitMatrix bitMatrix = pdf417Writer.encode(RandomStringUtils.random(10), BarcodeFormat.valueOf("QR_CODE"), imageWidth, imageHeight);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

//            MatrixToImageWriter.writeToStream(bitMatrix, isPng ? "png" : "jpg", outputStream, new MatrixToImageConfig(
//
//            ));
            outputStream.flush();
            byte[] bytes = outputStream.toByteArray();
            ResponseEntity<ByteArrayResource> body = ResponseEntity.ok()
                    .contentLength(bytes.length)
                    .contentType(MediaType.IMAGE_PNG)
                    .body(new ByteArrayResource(bytes));
            return Mono.just(body);
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}
