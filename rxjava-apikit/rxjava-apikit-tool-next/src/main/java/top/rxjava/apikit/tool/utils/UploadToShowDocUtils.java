package top.rxjava.apikit.tool.utils;

import okhttp3.*;
import top.rxjava.apikit.tool.form.UploadToShowDocForm;

import java.io.IOException;
import java.util.Objects;

/**
 * @author wugang
 */
public class UploadToShowDocUtils {
    public static String post(UploadToShowDocForm form) throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        form.setApiKey("e9f76907a19375e6398c3980a80728a52111516289");
        form.setApiToken("fd2df1ba07d73bebb483da11028d99a0772770510");
        String requestBodyStr = JsonUtils.serialize(form);

        System.out.println(requestBodyStr);
        RequestBody body = RequestBody.create(requestBodyStr, mediaType);
        Request request = new Request.Builder()
                .url("https://www.showdoc.cc/server/api/item/updateByApi")
                .method("POST", body)
                .addHeader("Content-Type", "application/json")
                .addHeader("Cookie", "PHPSESSID=af76c829767c0e63696533ab7bbe3664")
                .build();
        Response response = client.newCall(request).execute();
        return Objects.requireNonNull(response.body()).string();
    }

    public static void main(String[] args) throws IOException {
        UploadToShowDocForm form = new UploadToShowDocForm();
        form.setCatName("test");
        form.setPageTitle("商品列表");
        form.setPageContent("hello99");
        form.setSNumber("99");
        post(form);
    }
}
