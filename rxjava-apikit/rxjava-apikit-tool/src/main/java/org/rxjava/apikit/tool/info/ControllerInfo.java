package org.rxjava.apikit.tool.info;

import org.rxjava.apikit.tool.type.ApiType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author happy 2019/10/26 22:22
 * 控制器信息
 */
@Getter
@Setter
public class ControllerInfo extends ClassBaseInfo {
    /**
     * 控制器请求方法列表
     */
    private List<ControllerMethodInfo> methodInfos;
    /**
     * api接口类型
     * person:用户
     * admin:管理
     * inner:内部
     */
    private ApiType apiType;
}
