package org.rxjava.apikit.tool.generator;

import java.util.Set;

/**
 * @author happy
 * 类名Mapper接口
 */
public interface ClassNameMapper {
    String apply(Set<String> nameSet, String packageName, String name);
}
