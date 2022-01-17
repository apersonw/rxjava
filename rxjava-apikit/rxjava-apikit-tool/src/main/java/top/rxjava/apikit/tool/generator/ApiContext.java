package top.rxjava.apikit.tool.generator;

import lombok.Data;
import top.rxjava.apikit.tool.info.ControllerInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author happy 2019/10/26 22:03
 * Api上下文
 */
@Data
public class ApiContext {
    private List<ControllerInfo> controllerInfos = new ArrayList<>();
}
