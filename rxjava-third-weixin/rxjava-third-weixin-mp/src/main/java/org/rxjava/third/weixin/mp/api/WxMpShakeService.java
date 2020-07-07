package org.rxjava.third.weixin.mp.api;

import org.rxjava.third.weixin.common.error.WxError;
import org.rxjava.third.weixin.common.error.WxErrorException;
import org.rxjava.third.weixin.mp.bean.WxMpShakeInfoResult;
import org.rxjava.third.weixin.mp.bean.WxMpShakeQuery;
import org.rxjava.third.weixin.mp.bean.shake.*;

/**
 * 摇一摇周边的相关接口.
 */
public interface WxMpShakeService {
    /**
     * 获取设备及用户信息<br/>
     * 获取设备信息，包括UUID、major、minor，以及距离、openID等信息。
     * 详情请见: https://mp.weixin.qq.com/wiki?action=doc&id=mp1443447963
     * http请求方式: POST（请使用https协议）
     * 接口地址：https://api.weixin.qq.com/shakearound/user/getshakeinfo?access_token=ACCESS_TOKE
     *
     * @param wxMpShakeQuery 查询参数
     */
    WxMpShakeInfoResult getShakeInfo(WxMpShakeQuery wxMpShakeQuery) throws WxErrorException;

    /**
     * 页面管理<br/>
     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1459246752
     *
     * @param shakeAroundPageAddQuery
     * @return
     * @throws WxErrorException
     */
    WxMpShakeAroundPageAddResult pageAdd(WxMpShakeAroundPageAddQuery shakeAroundPageAddQuery) throws WxErrorException;

    /**
     * 配置设备与页面的关联关系<br/>
     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1459301931
     *
     * @param shakeAroundDeviceBindPageQuery
     * @return
     * @throws WxErrorException
     */
    WxError deviceBindPageQuery(WxMpShakeAroundDeviceBindPageQuery shakeAroundDeviceBindPageQuery) throws WxErrorException;

    /**
     * 查询设备与页面的关联关系<br/>
     * 详情请见: https://mp.weixin.qq.com/wiki?t=resource/res_main&id=mp1443447914
     *
     * @param shakeAroundRelationSearchQuery
     * @return
     * @throws WxErrorException
     */
    WxMpShakeAroundRelationSearchResult relationSearch(WxMpShakeAroundRelationSearchQuery shakeAroundRelationSearchQuery) throws WxErrorException;
}
