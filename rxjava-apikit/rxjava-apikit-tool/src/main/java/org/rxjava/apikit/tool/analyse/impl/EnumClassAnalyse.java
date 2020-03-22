package org.rxjava.apikit.tool.analyse.impl;

import org.rxjava.apikit.tool.analyse.Analyse;
import org.rxjava.apikit.tool.generator.Context;

/**
 * 枚举分析器
 */
public class EnumClassAnalyse implements Analyse {
    @Override
    public void analyse(Context context) {
    }
    public static EnumClassAnalyse create() {
        return new EnumClassAnalyse();
    }
}
