package org.rxjava.third.tencent.weixin.cp.api;

import org.rxjava.third.tencent.weixin.common.error.WxErrorException;
import org.rxjava.third.tencent.weixin.cp.bean.WxCpDepart;

import java.util.List;

/**
 * 部门管理接口
 * Created by BinaryWang on 2017/6/24.
 *
 * @author <a href="https://github.com/binarywang">Binary Wang</a>
 */
public interface WxCpDepartmentService {

    /**
     * 部门管理接口 - 创建部门.
     * 最多支持创建500个部门
     * 详情请见: https://work.weixin.qq.com/api/doc#90000/90135/90205
     *
     * @param depart 部门
     * @return 部门id
     * @throws WxErrorException 异常
     */
    Long create(WxCpDepart depart) throws WxErrorException;

    /**
     * 部门管理接口 - 获取部门列表.
     * 详情请见: https://work.weixin.qq.com/api/doc#90000/90135/90208
     *
     * @param id 部门id。获取指定部门及其下的子部门。非必需，可为null
     * @return 获取的部门列表
     * @throws WxErrorException 异常
     */
    List<WxCpDepart> list(Long id) throws WxErrorException;

    /**
     * 部门管理接口 - 更新部门.
     * 详情请见: https://work.weixin.qq.com/api/doc#90000/90135/90206
     * 如果id为0(未部门),1(黑名单),2(星标组)，或者不存在的id，微信会返回系统繁忙的错误
     *
     * @param group 要更新的group，group的id,name必须设置
     * @throws WxErrorException 异常
     */
    void update(WxCpDepart group) throws WxErrorException;

    /**
     * 部门管理接口 - 删除部门.
     * 详情请见: https://work.weixin.qq.com/api/doc#90000/90135/90207
     * 应用须拥有指定部门的管理权限
     *
     * @param departId 部门id
     * @throws WxErrorException 异常
     */
    void delete(Long departId) throws WxErrorException;
}
