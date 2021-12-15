package top.rxjava.apikit.tool.generator.impl;

import top.rxjava.apikit.tool.generator.ClassNameMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;

/**
 * @author happy
 * 默认类名Mapper
 */
public class DefaultClassNameMapper implements ClassNameMapper {
    @Override
    public String apply(Set<String> nameSet, String packageName, String name) {
        if (!nameSet.contains(name)) {
            return name;
        }
        String[] packParts = packageName.split("\\.");

        StringBuilder nameSb = new StringBuilder(name);
        for (int i = packParts.length - 1; i >= 0; i--) {
            String part = packParts[i];
            if (StringUtils.isNotEmpty(part)) {
                nameSb.insert(0, Character.toUpperCase(Character.toUpperCase(part.codePointAt(0))));
                nameSb.insert(0, part.substring(1));
            }
            String curName = nameSb.toString();
            if (!nameSet.contains(curName)) {
                return curName;
            }
        }

        for (int i = 0; ; i++) {
            String curName = name + i;
            if (!nameSet.contains(curName)) {
                return curName;
            }
        }
    }
}
