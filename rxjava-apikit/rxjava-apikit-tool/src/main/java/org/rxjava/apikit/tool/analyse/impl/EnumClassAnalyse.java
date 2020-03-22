package org.rxjava.apikit.tool.analyse.impl;

import org.rxjava.apikit.tool.analyse.Analyse;
import org.rxjava.apikit.tool.generator.Context;
import org.rxjava.apikit.tool.info.ClassTypeInfo;

import java.util.Set;

/**
 * 枚举分析器
 */
public class EnumClassAnalyse implements Analyse {
    @Override
    public void analyse(Context context) {
        Set<ClassTypeInfo> enumInfoSet = context.getEnumInfoSet();
        enumInfoSet.forEach(this::analyseEnum);
    }

    private void analyseEnum(ClassTypeInfo classTypeInfo) {
        try {
            Class enumClass = Class.forName(classTypeInfo.getFullName());
            Enum[] enumConstants = (Enum[]) enumClass.getEnumConstants();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static EnumClassAnalyse create() {
        return new EnumClassAnalyse();
    }
}
