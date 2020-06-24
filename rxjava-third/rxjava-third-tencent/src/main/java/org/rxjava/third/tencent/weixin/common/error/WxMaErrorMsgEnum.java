package org.rxjava.third.tencent.weixin.common.error;

import lombok.Getter;

/**
 * 微信小程序错误码
 *
 * @author <a href="https://github.com/biggates">biggates</a>
 */
@Getter
public enum WxMaErrorMsgEnum {
    /**
     * 获取 access_token 时 AppSecret 错误，
     * 或者 access_token 无效。请开发者认真比对 AppSecret 的正确性，或查看是否正在为恰当的小程序调用接口
     * 对应操作：<code>sendCustomerMessage</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/customer-message/sendCustomerMessage.html
     */
    CODE_40001(40001, "access_token 无效或 AppSecret 错误"),
    /**
     * 不合法的凭证类型
     * 对应操作：<code>sendCustomerMessage</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/customer-message/sendCustomerMessage.html
     */
    CODE_40002(40002, "不合法的凭证类型"),
    /**
     * touser不是正确的openid.
     * 对应操作：<code>sendCustomerMessage</code>, <code>sendUniformMessage</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
     * POST https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/customer-message/sendCustomerMessage.html
     * https://developers.weixin.qq.com/miniprogram/dev/api/open-api/uniform-message/sendUniformMessage.html
     */
    CODE_40003(40003, "openid 不正确"),
    /**
     * 无效媒体文件类型
     * 对应操作：<code>uploadTempMedia</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/customer-message/uploadTempMedia.html
     */
    CODE_40004(40004, "无效媒体文件类型"),
    /**
     * 无效媒体文件 ID.
     * 对应操作：<code>getTempMedia</code>
     * 对应地址：
     * GET https://api.weixin.qq.com/cgi-bin/media/get?access_token=ACCESS_TOKEN&media_id=MEDIA_ID
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/customer-message/getTempMedia.html
     */
    CODE_40007(40007, "无效媒体文件 ID"),
    /**
     * appid不正确，或者不符合绑定关系要求.
     * 对应操作：<code>sendUniformMessage</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/uniform-message/sendUniformMessage.html
     */
    CODE_40013(40013, "appid不正确，或者不符合绑定关系要求"),
    /**
     * template_id 不正确.
     * 对应操作：<code>sendUniformMessage</code>, <code>sendTemplateMessage</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN
     * POST https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/uniform-message/sendUniformMessage.html
     * https://developers.weixin.qq.com/miniprogram/dev/api/open-api/template-message/sendTemplateMessage.html
     */
    CODE_40037(40037, "template_id 不正确"),
    /**
     * form_id不正确，或者过期.
     * 对应操作：<code>sendUniformMessage</code>, <code>sendTemplateMessage</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN
     * POST https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/uniform-message/sendUniformMessage.html
     * https://developers.weixin.qq.com/miniprogram/dev/api/open-api/template-message/sendTemplateMessage.html
     */
    CODE_41028(41028, "form_id 不正确，或者过期"),
    /**
     * code 或 template_id 不正确.
     * 对应操作：<code>code2Session</code>, <code>sendUniformMessage</code>, <code>sendTemplateMessage</code>
     * 对应地址：
     * GET https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
     * POST https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN
     * POST https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/login/code2Session.html
     * https://developers.weixin.qq.com/miniprogram/dev/api/open-api/uniform-message/sendUniformMessage.html
     * https://developers.weixin.qq.com/miniprogram/dev/api/open-api/template-message/sendTemplateMessage.html
     */
    CODE_41029(41029, "请求的参数不正确"),
    /**
     * form_id 已被使用，或者所传page页面不存在，或者小程序没有发布
     * 对应操作：<code>sendUniformMessage</coce>, <code>getWXACodeUnlimit</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN
     * POST https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/uniform-message/sendUniformMessage.html
     * https://developers.weixin.qq.com/miniprogram/dev/api/open-api/qr-code/getWXACodeUnlimit.html
     */
    CODE_41030(41030, "请求的参数不正确"),
    /**
     * 调用分钟频率受限.
     * 对应操作：<code>getWXACodeUnlimit</code>, <code>sendUniformMessage</code>, <code>sendTemplateMessage</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=ACCESS_TOKEN
     * POST https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=ACCESS_TOKEN
     * POST https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/uniform-message/sendUniformMessage.html
     * https://developers.weixin.qq.com/miniprogram/dev/api/open-api/qr-code/getWXACodeUnlimit.html
     */
    CODE_45009(45009, "调用分钟频率受限"),
    /**
     * 频率限制，每个用户每分钟100次.
     * 对应操作：<code>code2Session</code>
     * 对应地址：
     * GET https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/login/code2Session.html
     */
    CODE_45011(45011, "频率限制，每个用户每分钟100次"),
    /**
     * 回复时间超过限制.
     * 对应操作：<code>sendCustomerMessage</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/customer-message/sendCustomerMessage.html
     */
    CODE_45015(45015, "回复时间超过限制"),
    /**
     * 接口调用超过限额， 或生成码个数总和到达最大个数限制.
     * 对应操作：<code>createWXAQRCode</code>, <code>sendTemplateMessage</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=ACCESS_TOKEN
     * POST https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/qr-code/getWXACode.html
     * https://developers.weixin.qq.com/miniprogram/dev/api/open-api/template-message/sendTemplateMessage.html
     */
    CODE_45029(45029, "接口调用超过限额"),
    /**
     * 客服接口下行条数超过上限.
     * 对应操作：<code>sendCustomerMessage</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/customer-message/sendCustomerMessage.html
     */
    CODE_45047(45047, "客服接口下行条数超过上限"),
    /**
     * command字段取值不对
     * 对应操作：<code>customerTyping</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/message/custom/typing?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/customer-message/customerTyping.html
     */
    CODE_45072(45072, "command字段取值不对"),
    /**
     * 下发输入状态，需要之前30秒内跟用户有过消息交互.
     * 对应操作：<code>customerTyping</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/message/custom/typing?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/customer-message/customerTyping.html
     */
    CODE_45080(45080, "下发输入状态，需要之前30秒内跟用户有过消息交互"),
    /**
     * 已经在输入状态，不可重复下发.
     * 对应操作：<code>customerTyping</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/message/custom/typing?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/customer-message/customerTyping.html
     */
    CODE_45081(45081, "已经在输入状态，不可重复下发"),
    /**
     * API 功能未授权，请确认小程序已获得该接口.
     * 对应操作：<code>sendCustomerMessage</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/customer-message/sendCustomerMessage.html
     */
    CODE_48001(48001, "API 功能未授权"),
    /**
     * 内容含有违法违规内容.
     * 对应操作：<code>imgSecCheck</code>, <code>msgSecCheck</code>
     * 对应地址：
     * POST https://api.weixin.qq.com/wxa/img_sec_check?access_token=ACCESS_TOKEN
     * POST https://api.weixin.qq.com/wxa/msg_sec_check?access_token=ACCESS_TOKEN
     * 参考文档地址： https://developers.weixin.qq.com/miniprogram/dev/api/open-api/sec-check/imgSecCheck.html
     * https://developers.weixin.qq.com/miniprogram/dev/api/open-api/sec-check/msgSecCheck.html
     */
    CODE_87014(87014, "内容含有违法违规内容"),
    /**
     * 系统繁忙，此时请开发者稍候再试.
     */
    CODE_MINUS_1(-1, "系统繁忙，此时请开发者稍候再试"),
    /**
     * code 无效.
     */
    CODE_40029(40029, "code 无效"),
    /**
     * access_token 过期.
     */
    CODE_42001(42001, "access_token 过期"),
    /**
     * post 数据为空.
     */
    CODE_44002(44002, "post 数据为空"),
    /**
     * post 数据中参数缺失.
     */
    CODE_47001(47001, "post 数据中参数缺失"),
    /**
     * 参数 activity_id 错误.
     */
    CODE_47501(47501, "参数 activity_id 错误"),
    /**
     * 参数 target_state 错误.
     */
    CODE_47502(47502, "参数 target_state 错误"),
    /**
     * 参数 version_type 错误.
     */
    CODE_47503(47503, "参数 version_type 错误"),
    /**
     * activity_id 过期.
     */
    CODE_47504(47504, "activity_id 过期"),
    /**
     * 没有绑定开放平台帐号.
     */
    CODE_89002(89002, "没有绑定开放平台帐号"),
    /**
     * 订单无效.
     */
    CODE_89300(89300, "订单无效"),

    /**
     * 代小程序实现业务的错误码，部分和小程序业务一致
     * https://developers.weixin.qq.com/doc/oplatform/Third-party_Platforms/Mini_Programs/Intro.html
     */
    CODE_85060(85060, "无效的taskid"),

    CODE_85027(85027, "身份证绑定管理员名额达到上限"),

    CODE_85061(85061, "手机号绑定管理员名额达到上限"),

    CODE_85026(85026, "微信号绑定管理员名额达到上限"),

    CODE_85063(85063, "身份证黑名单"),

    CODE_85062(85062, "手机号黑名单"),

    CODE_85016(85016, "域名数量超过限制"),

    CODE_85017(85017, "没有新增域名，请确认小程序已经添加了域名或该域名是否没有在第三方平台添加"),

    CODE_85018(85018, "域名没有在第三方平台设置"),

    CODE_89019(89019, "业务域名无更改，无需重复设置"),

    CODE_89020(89020, "尚未设置小程序业务域名，请先在第三方平台中设置小程序业务域名后在调用本接口"),

    CODE_89021(89021, "请求保存的域名不是第三方平台中已设置的小程序业务域名或子域名"),

    CODE_89029(89029, "业务域名数量超过限制"),

    CODE_89231(89231, "个人小程序不支持调用 setwebviewdomain 接口"),

    CODE_91001(91001, "不是公众号快速创建的小程序"),

    CODE_91002(91002, "小程序发布后不可改名"),

    CODE_91003(91003, "改名状态不合法"),

    CODE_91004(91004, "昵称不合法"),

    CODE_91005(91005, "昵称 15 天主体保护"),

    CODE_91006(91006, "昵称命中微信号"),

    CODE_91007(91007, "昵称已被占用"),

    CODE_91008(91008, "昵称命中 7 天侵权保护期"),

    CODE_91009(91009, "需要提交材料"),

    CODE_91010(91010, "其他错误"),

    CODE_91011(91011, "查不到昵称修改审核单信息"),

    CODE_91012(91012, "其他错误"),

    CODE_91013(91013, "占用名字过多"),

    CODE_91014(91014, "+号规则 同一类型关联名主体不一致"),

    CODE_91015(91015, "原始名不同类型主体不一致"),

    CODE_91016(91016, "名称占用者 ≥2"),

    CODE_91017(91017, "+号规则 不同类型关联名主体不一致"),

    CODE_40097(40097, "参数错误"),

    CODE_41006(41006, "media_id 不能为空"),

    CODE_46001(46001, "media_id 不存在"),

    CODE_40009(40009, "图片尺寸太大"),

    CODE_53202(53202, "本月头像修改次数已用完"),

    CODE_53200(53200, "本月功能介绍修改次数已用完"),

    CODE_53201(53201, "功能介绍内容命中黑名单关键字"),

    CODE_85083(85083, "搜索标记位被封禁，无法修改"),

    CODE_85084(85084, "非法的 status 值，只能填 0 或者 1"),

    CODE_85013(85013, "无效的自定义配置"),

    CODE_85014(85014, "无效的模版编号"),

    CODE_85043(85043, "模版错误"),

    CODE_85044(85044, "代码包超过大小限制"),

    CODE_85045(85045, "ext_json 有不存在的路径"),

    CODE_85046(85046, "tabBar 中缺少 path"),

    CODE_85047(85047, "pages 字段为空"),

    CODE_85048(85048, "ext_json 解析失败"),

    CODE_80082(80082, "没有权限使用该插件"),

    CODE_80067(80067, "找不到使用的插件"),

    CODE_80066(80066, "非法的插件版本"),

    CODE_86000(86000, "不是由第三方代小程序进行调用"),

    CODE_86001(86001, "不存在第三方的已经提交的代码"),

    CODE_85006(85006, "标签格式错误"),

    CODE_85007(85007, "页面路径错误"),

    CODE_85008(85008, "类目填写错误"),

    CODE_85009(85009, "已经有正在审核的版本"),

    CODE_85010(85010, "item_list 有项目为空"),

    CODE_85011(85011, "标题填写错误"),

    CODE_85023(85023, "审核列表填写的项目数不在 1-5 以内"),

    CODE_85077(85077, "小程序类目信息失效（类目中含有官方下架的类目，请重新选择类目）"),

    CODE_86002(86002, "小程序还未设置昵称、头像、简介。请先设置完后再重新提交"),

    CODE_85085(85085, "近 7 天提交审核的小程序数量过多，请耐心等待审核完毕后再次提交"),

    CODE_85086(85086, "提交代码审核之前需提前上传代码"),

    CODE_85087(85087, "小程序已使用 api navigateToMiniProgram，请声明跳转 appid 列表后再次提交"),

    CODE_85012(85012, "无效的审核 id"),

    CODE_87013(87013, "撤回次数达到上限（每天一次，每个月 10 次）"),

    CODE_85019(85019, "没有审核版本"),

    CODE_85020(85020, "审核状态未满足发布"),

    CODE_87011(87011, "现网已经在灰度发布，不能进行版本回退"),

    CODE_87012(87012, "该版本不能回退，可能的原因：1:无上一个线上版用于回退 2:此版本为已回退版本，不能回退 3:此版本为回退功能上线之前的版本，不能回退"),

    CODE_85079(85079, "小程序没有线上版本，不能进行灰度"),

    CODE_85080(85080, "小程序提交的审核未审核通过"),

    CODE_85081(85081, "无效的发布比例"),

    CODE_85082(85082, "当前的发布比例需要比之前设置的高"),

    CODE_85021(85021, "状态不可变"),

    CODE_85022(85022, "action 非法"),

    CODE_89401(89401, "系统不稳定，请稍后再试，如多次失败请通过社区反馈"),

    CODE_89402(89402, "该审核单不在待审核队列，请检查是否已提交审核或已审完"),

    CODE_89403(89403, "本单属于平台不支持加急种类，请等待正常审核流程"),

    CODE_89404(89404, "本单已加速成功，请勿重复提交"),

    CODE_89405(89405, "本月加急额度不足，请提升提审质量以获取更多额度"),

    CODE_85064(85064, "找不到模版/草稿"),

    CODE_85065(85065, "模版库已满"),

    /**
     * 小程序订阅消息错误码
     * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/subscribe-message/subscribeMessage.send.html
     */
    CODE_43101(43101, "用户拒绝接受消息，如果用户之前曾经订阅过，则表示用户取消了订阅关系"),

    CODE_47003(47003, "模板参数不准确，可能为空或者不满足规则，errmsg会提示具体是哪个字段出错"),

    /**
     * 小程序绑定体验者
     */
    CODE_85001(85001, "微信号不存在或微信号设置为不可搜索"),

    CODE_85002(85002, "小程序绑定的体验者数量达到上限"),

    CODE_85003(85003, "微信号绑定的小程序体验者达到上限"),

    CODE_85004(85004, "微信号已经绑定"),

//  CODE_504002(-504002, "云函数未找到 Function not found"),
    ;

    private int code;
    private String msg;

    WxMaErrorMsgEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 通过错误代码查找其中文含义.
     */
    public static String findMsgByCode(int code) {
        for (WxMaErrorMsgEnum value : WxMaErrorMsgEnum.values()) {
            if (value.code == code) {
                return value.msg;
            }
        }

        return null;
    }
}
