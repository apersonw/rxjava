package top.rxjava.third.weixin.miniapp.api;

import top.rxjava.third.weixin.common.error.WxErrorException;
import top.rxjava.third.weixin.miniapp.bean.template.WxMaTemplateAddResult;
import top.rxjava.third.weixin.miniapp.bean.template.WxMaTemplateLibraryGetResult;
import top.rxjava.third.weixin.miniapp.bean.template.WxMaTemplateLibraryListResult;
import top.rxjava.third.weixin.miniapp.bean.template.WxMaTemplateListResult;

import java.util.List;

/**
 */
@Deprecated
public interface WxMaTemplateService {
    /**
     * 获取小程序模板库标题列表.
     */
    String TEMPLATE_LIBRARY_LIST_URL = "https://api.weixin.qq.com/cgi-bin/wxopen/template/library/list";

    /**
     * 获取模板库某个模板标题下关键词库.
     */
    String TEMPLATE_LIBRARY_KEYWORD_URL = "https://api.weixin.qq.com/cgi-bin/wxopen/template/library/get";

    /**
     * 组合模板并添加至帐号下的个人模板库.
     */
    String TEMPLATE_ADD_URL = "https://api.weixin.qq.com/cgi-bin/wxopen/template/add";

    /**
     * 获取帐号下已存在的模板列表.
     */
    String TEMPLATE_LIST_URL = "https://api.weixin.qq.com/cgi-bin/wxopen/template/list";

    /**
     * 删除帐号下的某个模板.
     */
    String TEMPLATE_DEL_URL = "https://api.weixin.qq.com/cgi-bin/wxopen/template/del";

    /**
     * 获取小程序模板库标题列表
     * <p>
     * 详情请见: <a href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1500465446_j4CgR&token=&lang=zh_CN">获取小程序模板库标题列表</a>
     * 接口url格式: https://api.weixin.qq.com/cgi-bin/wxopen/template/library/list?access_token=ACCESS_TOKEN
     */
    WxMaTemplateLibraryListResult findTemplateLibraryList(int offset, int count) throws WxErrorException;

    /**
     * 获取模板库某个模板标题下关键词库
     * <p>
     * 详情请见: <a href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1500465446_j4CgR&token=&lang=zh_CN">获取小程序模板库标题列表</a>
     * 接口url格式: https://api.weixin.qq.com/cgi-bin/wxopen/template/library/get?access_token=ACCESS_TOKEN
     */
    WxMaTemplateLibraryGetResult findTemplateLibraryKeywordList(String id) throws WxErrorException;

    /**
     * 组合模板并添加至帐号下的个人模板库
     * <p>
     * 详情请见: <a href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1500465446_j4CgR&token=&lang=zh_CN">获取小程序模板库标题列表</a>
     * 接口url格式: https://api.weixin.qq.com/cgi-bin/wxopen/template/add?access_token=ACCESS_TOKEN
     */
    WxMaTemplateAddResult addTemplate(String id, List<Integer> keywordIdList) throws WxErrorException;

    /**
     * 获取帐号下已存在的模板列表
     * <p>
     * 详情请见: <a href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1500465446_j4CgR&token=&lang=zh_CN">获取小程序模板库标题列表</a>
     * 接口url格式: https://api.weixin.qq.com/cgi-bin/wxopen/template/list?access_token=ACCESS_TOKEN
     *
     * @return
     */
    WxMaTemplateListResult findTemplateList(int offset, int count) throws WxErrorException;

    /**
     * 删除帐号下的某个模板
     * <p>
     * 详情请见: <a href="https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=open1500465446_j4CgR&token=&lang=zh_CN">获取小程序模板库标题列表</a>
     * 接口url格式: https://api.weixin.qq.com/cgi-bin/wxopen/template/list?access_token=ACCESS_TOKEN
     */
    boolean delTemplate(String templateId) throws WxErrorException;
}
