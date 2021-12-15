package top.rxjava.third.weixin.open.bean.result;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import top.rxjava.third.weixin.open.util.json.WxOpenGsonBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
public class WxOpenMaBindTesterResult extends WxOpenResult {


    private static final long serialVersionUID = -730133894662203011L;

    @SerializedName("userstr")
    private String userstr;

    @Override
    public String toString() {
        return WxOpenGsonBuilder.create().toJson(this);
    }

}
