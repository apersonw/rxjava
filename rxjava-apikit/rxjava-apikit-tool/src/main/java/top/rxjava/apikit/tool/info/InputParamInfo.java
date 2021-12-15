package top.rxjava.apikit.tool.info;

import lombok.Getter;
import lombok.Setter;

/**
 * @author happy 2019/10/27 00:24
 * 输入的参数信息
 */
@Getter
@Setter
public class InputParamInfo extends ParamInfo {
    /**
     * 是否有@PathVariable注解
     */
    private boolean pathVariable;
    /**
     * 是否有@Valid注解
     */
    private boolean valid;
}
