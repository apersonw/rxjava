package org.rxjava.apikit.tool.generator;

import org.rxjava.apikit.tool.info.ControllerInfo;
import lombok.Data;

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
