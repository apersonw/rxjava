package org.rxjava.apikit.tool;

import org.rxjava.apikit.tool.analyse.Analyse;
import org.rxjava.apikit.tool.generator.Context;

/**
 * @author happy
 * 对象工厂
 */
public interface ObjectFactory {
    /**
     * 创建上下文对象
     * @return 上下文
     */
    Context createContext();

    /**
     * 创建l分析器
     * @return 分析器
     */
    Analyse createAnalyse();

    /**
     * 消息分析器
     * @return 分析器
     */
    Analyse createMessageAnalyse();
}
