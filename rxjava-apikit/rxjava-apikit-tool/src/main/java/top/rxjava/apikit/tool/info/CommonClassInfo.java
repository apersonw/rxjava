package top.rxjava.apikit.tool.info;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author happy
 * 通用类信息
 */
@Setter
@Getter
@EqualsAndHashCode
@NoArgsConstructor
public class CommonClassInfo {
    /**
     * 类上的JavaDoc
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

    public CommonClassInfo(String packageName, String className) {
        this.packageName = packageName;
        this.className = className;
    }
}
