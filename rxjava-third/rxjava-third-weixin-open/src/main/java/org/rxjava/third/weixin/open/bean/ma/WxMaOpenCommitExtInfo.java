package org.rxjava.third.weixin.open.bean.ma;

import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.rxjava.third.weixin.open.util.json.WxOpenGsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信小程序三方平台代上传代码提交额外信息对象
 * 如果代码中已经有配置，则配置的合并规则为：除了pages和tabBar.list直接覆盖原配置，其他都为插入或同级覆盖。
 * extjson 详细说明
 * https://developers.weixin.qq.com/miniprogram/dev/devtools/ext.html#%E5%B0%8F%E7%A8%8B%E5%BA%8F%E6%A8%A1%E6%9D%BF%E5%BC%80%E5%8F%91
 */
@Data
public class WxMaOpenCommitExtInfo implements Serializable {

    WxMaOpenCommitExtInfo() {

    }

    /**
     * 授权小程序Appid，可填入商户小程序AppID，以区分不同商户
     */
    private String extAppid;

    /**
     * 配置 ext.json 是否生效
     */
    private Boolean extEnable = Boolean.TRUE;

    /**
     * 是否直接提交到待审核列表
     */
    private Boolean directCommit = Boolean.FALSE;

    @SerializedName("ext")
    private Map<String, Object> extMap;

    @SerializedName("extPages")
    private Map<String, WxMaOpenPage> extPages;

    /**
     * 页面路径列表(和app.json结构一致)
     */
    @SerializedName("pages")
    private List<String> pageList;

    /**
     * 分包结构配置
     */
    @SerializedName("subpackages")
    private List<WxMaOpenSubpackage> subpackageList;

    @SerializedName("window")
    private WxMaOpenWindow window;

    @SerializedName("networkTimeout")
    private WxMaOpenNetworkTimeout networkTimeout;

    @SerializedName("tabBar")
    private WxMaOpenTabBar tabBar;

    /**
     * 添加扩展项
     *
     * @param key
     * @param value
     */
    public void addExt(String key, String value) {
        if (extMap == null)
            extMap = new HashMap<>();
        if (StringUtils.isNoneBlank(key, value))
            extMap.put(key, value);
    }

    /**
     * 添加扩展页面
     *
     * @param pagePath
     * @param page
     */
    public void addExtPage(String pagePath, WxMaOpenPage page) {
        if (extPages == null)
            extPages = new HashMap<>();
        if (StringUtils.isNotBlank(pagePath) && page != null)
            extPages.put(pagePath, page);
    }

    /**
     * 添加页面
     *
     * @param pagePath
     */
    public void addPage(String pagePath) {
        if (pageList == null)
            pageList = new ArrayList<>();
        if (StringUtils.isNotBlank(pagePath))
            pageList.add(pagePath);
    }

    public static WxMaOpenCommitExtInfo INSTANCE() {
        return new WxMaOpenCommitExtInfo();
    }

    public String toJson() {
        return WxOpenGsonBuilder.create().toJson(this);
    }
}
