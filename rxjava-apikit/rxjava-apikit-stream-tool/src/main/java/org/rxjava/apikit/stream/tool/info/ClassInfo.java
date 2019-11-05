package org.rxjava.apikit.stream.tool.info;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClassInfo {
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
