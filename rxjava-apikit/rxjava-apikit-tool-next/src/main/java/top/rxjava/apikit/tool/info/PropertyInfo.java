package top.rxjava.apikit.tool.info;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author happy
 * 属性信息
 */
@Setter
@Getter
@ToString
public class PropertyInfo extends FieldInfo {
    /**
     * 文档注释信息
     */
    private JavaDocInfo javadocInfo;
    /**
     * 是否需要的参数
     */
    private boolean required=false;

    public PropertyInfo(String name, ClassTypeInfo classTypeInfo) {
        super(name, classTypeInfo);
    }
}
