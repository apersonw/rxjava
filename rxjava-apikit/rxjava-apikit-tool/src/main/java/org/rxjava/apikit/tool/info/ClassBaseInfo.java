package org.rxjava.apikit.tool.info;

import lombok.Getter;
import lombok.Setter;

/**
 * @author happy 2019/10/27 17:56
 * 类基本信息
 */
@Getter
@Setter
public class ClassBaseInfo {
    /**
     * 所在包(org.rxjava.apikit.tool.info)
     * 包名为null代表为基本类型：如int,long等
     */
    private String packageName;
    /**
     * 简单类名(ClassBaseInfo)
     */
    private String simpleName;
    /**
     * 全类名(org.rxjava.apikit.tool.info.ClassBaseInfo)
     */
    private String name;
}
