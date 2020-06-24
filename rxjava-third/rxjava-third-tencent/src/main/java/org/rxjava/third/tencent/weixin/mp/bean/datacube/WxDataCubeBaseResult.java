package org.rxjava.third.tencent.weixin.mp.bean.datacube;

import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.rxjava.third.tencent.weixin.mp.util.json.WxMpGsonBuilder;

import java.io.Serializable;

/**
 * 统计接口的共用属性类.
 * Created by Binary Wang on 2016/8/25.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
@Data
public abstract class WxDataCubeBaseResult implements Serializable {
    private static final long serialVersionUID = 8780389911053297600L;
    protected static final JsonParser JSON_PARSER = new JsonParser();

    /**
     * ref_date.
     * 数据的日期，需在begin_date和end_date之间
     */
    @SerializedName("ref_date")
    private String refDate;

    @Override
    public String toString() {
        return WxMpGsonBuilder.create().toJson(this);
    }

}
