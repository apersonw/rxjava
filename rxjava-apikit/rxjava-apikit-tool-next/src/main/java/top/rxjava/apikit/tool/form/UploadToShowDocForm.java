package top.rxjava.apikit.tool.form;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @author wugang
 */
@Data
public class UploadToShowDocForm {
    @JsonProperty("api_key")
    private String apiKey;
    @JsonProperty("api_token")
    private String apiToken;
    @JsonProperty("cat_name")
    private String catName;
    @JsonProperty("page_title")
    private String pageTitle;
    @JsonProperty("page_content")
    private String pageContent;
    @JsonProperty("s_number")
    private String sNumber;
}
