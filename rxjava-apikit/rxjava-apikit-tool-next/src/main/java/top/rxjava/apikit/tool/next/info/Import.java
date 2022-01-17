package top.rxjava.apikit.tool.next.info;

import lombok.Getter;
import lombok.Setter;

/**
 * @author happy
 */
@Setter
@Getter
public class Import {
    /**
     * 包名
     */
    private String packageName;
    /**
     * 类名
     */
    private String className;
    /**
     * 是否需要进行包转换
     */
    private boolean inside;
    /**
     * 按需
     */
    private boolean onDemand;

    public Import(String packageName, String className, boolean inside, boolean onDemand) {
        this.packageName = packageName;
        this.className = className;
        this.inside = inside;
        this.onDemand = onDemand;
    }
}
