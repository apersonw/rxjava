package top.rxjava.apikit.tool.generator;

/**
 * @author happy
 * 生成器接口
 */
public interface Generator {
    /**
     * 根据上下文生成
     *
     * @param context 上下文
     */
    void generate(Context context) throws Exception;

    /**
     * 获取输出路径
     *
     * @return 文件输出路径
     */
    String getOutPath();

    /**
     * 设置版本号
     *
     * @param version 版本号
     */
    void setVersion(String version);
}
