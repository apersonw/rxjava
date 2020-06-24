package org.rxjava.third.tencent.miniapp.bean.template;

import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.rxjava.third.tencent.common.util.json.WxGsonBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * @author ArBing
 */
@Data
public class WxMaTemplateLibraryListResult implements Serializable{
  private static final long serialVersionUID = -2780782521447602209L;

  @SerializedName("total_count")
  private int totalCount;
  private List<TemplateItem> list;

  public static WxMaTemplateLibraryListResult fromJson(String json){
    return WxGsonBuilder.create().fromJson(json, WxMaTemplateLibraryListResult.class);
  }

  @Data
  public static class TemplateItem{

    private String id;
    private String title;
  }
}
