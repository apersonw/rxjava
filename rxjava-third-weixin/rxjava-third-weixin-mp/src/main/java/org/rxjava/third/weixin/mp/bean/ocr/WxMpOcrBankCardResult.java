package org.rxjava.third.weixin.mp.bean.ocr;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.rxjava.third.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * 银行卡OCR识别结果
 */
@Data
public class WxMpOcrBankCardResult implements Serializable {

    private static final long serialVersionUID = 554136620394204143L;
    @SerializedName("number")
    private String number;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

    public static WxMpOcrBankCardResult fromJson(String json) {
        return WxMpGsonBuilder.create().fromJson(json, WxMpOcrBankCardResult.class);
    }
}