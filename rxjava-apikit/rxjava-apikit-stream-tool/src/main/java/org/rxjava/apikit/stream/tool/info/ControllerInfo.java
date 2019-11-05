package org.rxjava.apikit.stream.tool.info;

import lombok.Getter;
import lombok.Setter;
import org.rxjava.apikit.stream.tool.type.ApiType;

import java.util.List;

@Getter
@Setter
public class ControllerInfo extends ClassInfo {
    /**
     * 控制器请求方法列表
     */
    private List<MethodInfo> methodInfos;
    /**
     * api接口类型
     * person:用户
     * admin:管理
     * inner:内部
     */
    private ApiType apiType;
    /**
     * 控制器注释名称
     */
    private String commentName;
    /**
     * 控制器描述
     */
    private String commentDesc;
}
