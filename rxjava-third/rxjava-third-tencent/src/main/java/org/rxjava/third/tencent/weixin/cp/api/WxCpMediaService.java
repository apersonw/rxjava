package org.rxjava.third.tencent.weixin.cp.api;

import org.rxjava.third.tencent.weixin.common.bean.result.WxMediaUploadResult;
import org.rxjava.third.tencent.weixin.common.error.WxErrorException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * 媒体管理接口.
 * Created by BinaryWang on 2017/6/24.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public interface WxCpMediaService {

    /**
     * 上传多媒体文件.
     * 上传的多媒体文件有格式和大小限制，如下：
     * 图片（image）: 1M，支持JPG格式
     * 语音（voice）：2M，播放长度不超过60s，支持AMR\MP3格式
     * 视频（video）：10MB，支持MP4格式
     * 缩略图（thumb）：64KB，支持JPG格式
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=上传下载多媒体文件
     *
     * @param mediaType   媒体类型, 请看{@link org.rxjava.third.tencent.weixin.common.api.WxConsts}
     * @param fileType    文件类型，请看{@link org.rxjava.third.tencent.weixin.common.api.WxConsts}
     * @param inputStream 输入流，需要调用方控制关闭该输入流
     */
    WxMediaUploadResult upload(String mediaType, String fileType, InputStream inputStream)
            throws WxErrorException, IOException;

    /**
     * 上传多媒体文件.
     *
     * @param mediaType 媒体类型
     * @param file      文件对象
     * @see #upload(String, String, InputStream)
     */
    WxMediaUploadResult upload(String mediaType, File file) throws WxErrorException;

    /**
     * 下载多媒体文件.
     * 根据微信文档，视频文件下载不了，会返回null
     * 详情请见: http://mp.weixin.qq.com/wiki/index.php?title=上传下载多媒体文件
     *
     * @param mediaId 媒体id
     * @return 保存到本地的临时文件
     */
    File download(String mediaId) throws WxErrorException;

    /**
     * 获取高清语音素材.
     * 可以使用本接口获取从JSSDK的uploadVoice接口上传的临时语音素材，格式为speex，16K采样率。该音频比上文的临时素材获取接口（格式为amr，8K采样率）更加清晰，适合用作语音识别等对音质要求较高的业务。
     * 请求方式：GET（HTTPS）
     * 请求地址：https://qyapi.weixin.qq.com/cgi-bin/media/get/jssdk?access_token=ACCESS_TOKEN&media_id=MEDIA_ID
     * 仅企业微信2.4及以上版本支持。
     * 文档地址：https://work.weixin.qq.com/api/doc#90000/90135/90255
     *
     * @param mediaId 媒体id
     * @return 保存到本地的临时文件
     */
    File getJssdkFile(String mediaId) throws WxErrorException;

    /**
     * 上传图片.
     * 上传图片得到图片URL，该URL永久有效
     * 返回的图片URL，仅能用于图文消息（mpnews）正文中的图片展示；若用于非企业微信域名下的页面，图片将被屏蔽。
     * 每个企业每天最多可上传100张图片
     * 接口url格式：https://qyapi.weixin.qq.com/cgi-bin/media/uploadimg?access_token=ACCESS_TOKEN
     *
     * @param file 上传的文件对象
     * @return 返回图片url
     */
    String uploadImg(File file) throws WxErrorException;
}
