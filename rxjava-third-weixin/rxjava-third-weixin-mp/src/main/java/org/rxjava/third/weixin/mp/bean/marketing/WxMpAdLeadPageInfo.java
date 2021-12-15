package top.rxjava.third.weixin.mp.bean.marketing;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

/**
 */
@Data
public class WxMpAdLeadPageInfo implements Serializable {
    private static final long serialVersionUID = -896765006445604780L;
    @SerializedName("page")
    private Integer page;
    @SerializedName("page_size")
    private Integer pageSize;
    @SerializedName("total_page")
    private Integer totalPage;
    @SerializedName("total_number")
    private Integer totalNumber;

}
