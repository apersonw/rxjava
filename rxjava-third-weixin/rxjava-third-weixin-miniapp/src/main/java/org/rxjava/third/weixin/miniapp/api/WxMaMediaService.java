package top.rxjava.third.weixin.miniapp.api;

import top.rxjava.third.weixin.common.bean.result.WxMediaUploadResult;
import top.rxjava.third.weixin.common.error.WxErrorException;

import java.io.File;
import java.io.InputStream;

/**
 * 临时素材接口
 */
public interface WxMaMediaService {
    String MEDIA_UPLOAD_URL = "https://api.weixin.qq.com/cgi-bin/media/upload?type=%s";
    String MEDIA_GET_URL = "https://api.weixin.qq.com/cgi-bin/media/get";

    /**
     * 新增临时素材
     * 小程序可以使用本接口把媒体文件（目前仅支持图片）上传到微信服务器，用户发送客服消息或被动回复用户消息。
     * 详情请见: <a href="https://mp.weixin.qq.com/debug/wxadoc/dev/api/custommsg/material.html#新增临时素材">新增临时素材</a>
     * 接口url格式：https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
     *
     * @param mediaType 媒体类型,
     * @param file      文件对象
     * @see #uploadMedia(String, String, InputStream)
     */
    WxMediaUploadResult uploadMedia(String mediaType, File file) throws WxErrorException;

    /**
     * 新增临时素材
     * 小程序可以使用本接口把媒体文件（目前仅支持图片）上传到微信服务器，用户发送客服消息或被动回复用户消息。
     * <p>
     * 详情请见: <a href="https://mp.weixin.qq.com/debug/wxadoc/dev/api/custommsg/material.html#新增临时素材">新增临时素材</a>
     * 接口url格式：https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
     *
     * @param mediaType   媒体类型
     * @param fileType    文件类型
     * @param inputStream 输入流
     * @see #uploadMedia(java.lang.String, java.io.File)
     */
    WxMediaUploadResult uploadMedia(String mediaType, String fileType, InputStream inputStream) throws WxErrorException;

    /**
     * 获取临时素材
     * 小程序可以使用本接口获取客服消息内的临时素材（即下载临时的多媒体文件）。目前小程序仅支持下载图片文件。
     * <p>
     * 详情请见: <a href="https://mp.weixin.qq.com/debug/wxadoc/dev/api/custommsg/material.html#获取临时素材">获取临时素材</a>
     * 接口url格式：https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID
     *
     * @param mediaId 媒体Id
     * @return 保存到本地的临时文件
     */
    File getMedia(String mediaId) throws WxErrorException;

}
