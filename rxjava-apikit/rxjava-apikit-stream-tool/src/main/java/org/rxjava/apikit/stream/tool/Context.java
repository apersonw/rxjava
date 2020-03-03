package org.rxjava.apikit.stream.tool;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * 上下文
 */
@Getter
@Setter
public class Context {
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
