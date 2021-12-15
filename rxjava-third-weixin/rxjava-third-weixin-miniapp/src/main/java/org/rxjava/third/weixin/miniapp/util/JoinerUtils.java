/**
 * Copyright (c) 2019,sunnybs.
 * All Rights Reserved.
 * <p>
 * Project Name:yigou
 */
package top.rxjava.third.weixin.miniapp.util;

import com.google.common.base.Joiner;

/**
 * ClassName: JoinerUtils <br/>
 * Description: 字符串连接器 <br/>
 */

public class JoinerUtils {
    private static final String NULL = "null";

    /**
     * <p>
     * 空白连接器，忽略null
     * </p>
     * <p>
     * <p>
     * JoinerUtils.blankJoiner.join("a", "b", "c");
     * <b>result : </b>abc
     * <p>
     * JoinerUtils.blankJoiner.join("a", null, "c");
     * <b>result : </b>ac
     */
    public static final Joiner blankJoiner = Joiner.on("").skipNulls();
    /**
     * <p>
     * 空白连接器
     * </p>
     * <p>
     * <p>
     * JoinerUtils.blankJoinerWithNull.join("a", "b", "c");
     * <b>result : </b>abc
     * <p>
     * JoinerUtils.blankJoinerWithNull.join("a", null, "c");
     * <b>result : </b>anullc
     */
    public static final Joiner blankJoinerWithNull = Joiner.on("").useForNull(NULL);

    /**
     * <p>
     * 空格连接器，忽略null
     * </p>
     * <p>
     * <p>
     * JoinerUtils.spaceJoiner.join("a", "b", "c");
     * <b>result : </b>a b c
     * <p>
     * JoinerUtils.spaceJoiner.join("a", null, "c");
     * <b>result : </b>a c
     */
    public static final Joiner spaceJoiner = Joiner.on(" ").skipNulls();
    /**
     * <p>
     * 空格连接器
     * </p>
     * <p>
     * <p>
     * JoinerUtils.spaceJoinerWithNull.join("a", "b", "c");
     * <b>result : </b>a b c
     * <p>
     * JoinerUtils.spaceJoinerWithNull.join("a", null, "c");
     * <b>result : </b>a null c
     */
    public static final Joiner spaceJoinerWithNull = Joiner.on(" ").useForNull(NULL);

    /**
     * <p>
     * 逗号分隔符连接器，忽略null
     * </p>
     * <p>
     * <p>
     * JoinerUtils.commaJoiner.join("a", "b", "c");
     * <b>result : </b>a,b,c
     * <p>
     * JoinerUtils.commaJoiner.join("a", null, "c");
     * <b>result : </b>a,c
     */
    public static final Joiner commaJoiner = Joiner.on(",").skipNulls();
    /**
     * <p>
     * 逗号分隔符连接器
     * </p>
     * <p>
     * <p>
     * JoinerUtils.commaJoinerWithNull.join("a", "b", "c");
     * <b>result : </b>a,b,c
     * <p>
     * JoinerUtils.commaJoinerWithNull.join("a", null, "c");
     * <b>result : </b>a,null,c
     */
    public static final Joiner commaJoinerWithNull = Joiner.on(",").useForNull(NULL);

    /**
     * <p>
     * 等号分隔符连接器，忽略null
     * </p>
     * <p>
     * <p>
     * JoinerUtils.equalJoiner.join("a", "b", "c");
     * <b>result : </b>a=b=c
     * <p>
     * JoinerUtils.equalJoiner.join("a", null, "c");
     * <b>result : </b>a=c
     */
    public static final Joiner equalJoiner = Joiner.on("=").skipNulls();
    /**
     * <p>
     * 等号分隔符连接器
     * </p>
     * <p>
     * <p>
     * JoinerUtils.equalJoinerWithNull.join("a", "b", "c");
     * <b>result : </b>a=b=c
     * <p>
     * JoinerUtils.equalJoinerWithNull.join("a", null, "c");
     * <b>result : </b>a=null=c
     */
    public static final Joiner equalJoinerWithNull = Joiner.on("=").useForNull(NULL);

    /**
     * <p>
     * 竖线分隔符连接器，忽略null
     * </p>
     * <p>
     * <p>
     * JoinerUtils.vLineJoiner.join("a", "b", "c");
     * <b>result : </b>a|b|c
     * <p>
     * JoinerUtils.vLineJoiner.join("a", null, "c");
     * <b>result : </b>a|c
     */
    public static final Joiner vLineJoiner = Joiner.on("|").skipNulls();
    /**
     * <p>
     * 竖线分隔符连接器
     * </p>
     * <p>
     * <p>
     * JoinerUtils.vLineJoinerWithNull.join("a", "b", "c");
     * <b>result : </b>a|b|c
     * <p>
     * JoinerUtils.vLineJoinerWithNull.join("a", null, "c");
     * <b>result : </b>a|null|c
     */
    public static final Joiner vLineJoinerWithNull = Joiner.on("|").useForNull(NULL);

    /**
     * <p>
     * 中横线分隔符连接器，忽略null
     * </p>
     * <p>
     * <p>
     * JoinerUtils.hLineJoiner.join("a", "b", "c");
     * <b>result : </b>a-b-c
     * <p>
     * JoinerUtils.hLineJoiner.join("a", null, "c");
     * <b>result : </b>a-c
     */
    public static final Joiner hLineJoiner = Joiner.on("-").skipNulls();
    /**
     * <p>
     * 中横线分隔符连接器
     * </p>
     * <p>
     * <p>
     * JoinerUtils.hLineJoinerWithNull.join("a", "b", "c");
     * <b>result : </b>a-b-c
     * <p>
     * JoinerUtils.hLineJoinerWithNull.join("a", null, "c");
     * <b>result : </b>a-null-c
     */
    public static final Joiner hLineJoinerWithNull = Joiner.on("-").useForNull(NULL);

    /**
     * <p>
     * 下划线分隔符连接器，忽略null
     * </p>
     * <p>
     * <p>
     * JoinerUtils.underlineJoiner.join("a", "b", "c");
     * <b>result : </b>a_b_c
     * <p>
     * JoinerUtils.underlineJoiner.join("a", null, "c");
     * <b>result : </b>a_c
     */
    public static final Joiner underlineJoiner = Joiner.on("_").skipNulls();
    /**
     * <p>
     * 下划线分隔符连接器
     * </p>
     * <p>
     * <p>
     * JoinerUtils.underlineJoinerWithNull.join("a", "b", "c");
     * <b>result : </b>a_b_c
     * <p>
     * JoinerUtils.underlineJoinerWithNull.join("a", null, "c");
     * <b>result : </b>a_null_c
     */
    public static final Joiner underlineJoinerWithNull = Joiner.on("_").useForNull(NULL);

    /**
     * <p>
     * 斜线分隔符连接器，忽略null
     * </p>
     * <p>
     * <p>
     * JoinerUtils.pathJoiner.join("a", "b", "c");
     * <b>result : </b>a/b/c
     * <p>
     * JoinerUtils.pathJoiner.join("a", null, "c");
     * <b>result : </b>a/c
     */
    public static final Joiner pathJoiner = Joiner.on("/").skipNulls();
    /**
     * <p>
     * 斜线分隔符连接器
     * </p>
     * <p>
     * <p>
     * JoinerUtils.pathJoinerWithNull.join("a", "b", "c");
     * <b>result : </b>a/b/c
     * <p>
     * JoinerUtils.pathJoinerWithNull.join("a", null, "c");
     * <b>result : </b>a/null/c
     */
    public static final Joiner pathJoinerWithNull = Joiner.on("/").useForNull(NULL);
}
