package org.rxjava.apikit.stream.tool;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * api工具上下文
 */
@Getter
@Setter
public class ApikitContext {
    /**
     * 分析包路径
     */
    private String analysePackage;
    /**
     * src/main/java的绝对路径
     */
    private String srcMainJavaPath;
    private List<String> nameList = new ArrayList<>();
}
