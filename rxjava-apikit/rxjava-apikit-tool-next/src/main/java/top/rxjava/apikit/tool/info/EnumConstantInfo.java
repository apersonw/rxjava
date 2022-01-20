package top.rxjava.apikit.tool.info;

import lombok.Data;

/**
 * @author wugang
 */
@Data
public class EnumConstantInfo {
    private String name;
    private int ordinal;
    /**
     * 文档注释信息
     */
    private JavaDocInfo javaDocInfo;
}
