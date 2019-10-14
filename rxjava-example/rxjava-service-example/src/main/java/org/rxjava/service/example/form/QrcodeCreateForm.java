package org.rxjava.service.example.form;

import com.google.zxing.BarcodeFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.rxjava.service.example.type.ImageType;

@Data
public class QrcodeCreateForm {
    /**
     * 图片类型
     */
    private String type = ImageType.png.name();
    /**
     * 图片类型
     */
    private ImageType imageType;
    /**
     * 二维码类型
     *
     * @see BarcodeFormat
     */
    private String qrcodeType = BarcodeFormat.QR_CODE.name();
    /**
     * 图片宽
     */
    @Range(min = 32, max = 1024, message = "图片宽最小32，最大1024，默认300")
    private int width = 300;
    /**
     * 图片高
     */
    @Range(min = 32, max = 1024, message = "图片高最小32，最大1024，默认300")
    private int height = 300;
}
