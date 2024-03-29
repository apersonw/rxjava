package org.rxjava.apikit.tool.info;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author happy
 */
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class ClassInfo {
    /**
     * Java文档信息
     */
    protected JavaDocInfo javaDocInfo;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 类名
     */
    private String className;

    public String getFullName() {
        return packageName + "." + className;
    }

    public ClassInfo(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }
}
