package org.rxjava.apikit.tool.generator.impl;

import org.rxjava.apikit.tool.utils.LocalPathUtils;
import org.rxjava.apikit.tool.wrapper.BuilderWrapper;

import java.io.File;

/**
 * @author happy
 * 抽象类：通用生成器
 */
public abstract class AbstractCommonGenerator extends AbstractHttlGenerator {
    /**
     * 创建Api文件
     */
    File createApiFile(BuilderWrapper wrapper, String suffix) {
        String fullDistPackage = wrapper.getFullDistPackage();
        if ("d.ts".equals(suffix) || ("js".equals(suffix)) || ("ts".equals(suffix))) {
            fullDistPackage = "src/";
        }
        return LocalPathUtils.packToPath(outPath, fullDistPackage, wrapper.getDistClassName(), "." + suffix);
    }

    /**
     * 创建参数类文件
     */
    File createParamClassFile(BuilderWrapper wrapper, String suffix) {
        String fullDistPackage = wrapper.getFullDistPackage(wrapper.getDistFolder());
        if ("d.ts".equals(suffix) || ("js".equals(suffix)) || ("ts".equals(suffix))) {
            fullDistPackage = "src/" + fullDistPackage;
        }
        return LocalPathUtils.packToPath(outPath, fullDistPackage, wrapper.getDistClassName(), "." + suffix);
    }
}
