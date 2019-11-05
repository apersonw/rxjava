package org.rxjava.apikit.stream.tool.analyse;

import org.rxjava.apikit.stream.tool.info.ClassCommentInfo;
import org.rxjava.apikit.stream.tool.utils.CommentUtils;
import org.rxjava.apikit.stream.tool.utils.FilePathUtils;

import java.nio.file.Path;

/**
 * 注释分析器
 */
public class CommentAnalyse {
    /**
     * 分析
     */
    public ClassCommentInfo analyse(String srcMainJavaPath, Class cls) {
        Path sourceCodeAbsolutePath = FilePathUtils.getSourceCodeAbsolutePath(srcMainJavaPath, cls);
        return CommentUtils.parseClass(sourceCodeAbsolutePath);
    }
}