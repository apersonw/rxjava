package org.rxjava.apikit.tool;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rxjava.apikit.tool.analyse.impl.ControllerAnalyse;
import org.rxjava.apikit.tool.analyse.impl.EnumClassAnalyse;
import org.rxjava.apikit.tool.analyse.impl.ParamClassAnalyse;
import org.rxjava.apikit.tool.generator.Context;
import org.rxjava.apikit.tool.generator.Generator;
import org.rxjava.apikit.tool.utils.LocalPathUtils;

/**
 * @author happy
 * Api生成管理器
 */
@Getter
@Setter
@NoArgsConstructor
public class ApiGenerateManager {
    /**
     * src/main/java的绝对路径
     */
    private String javaSourceDir;
    /**
     * 需要分析的包路径
     */
    private String rootPackage;
    /**
     * 文件编码
     */
    private String fileCharset = "utf8";
    /**
     * 文件扩展名
     */
    private String fileSuffix = ".java";
    /**
     * 需要分析的包文件夹路径
     */
    private String rootDirPath;
    /**
     * 上下文
     */
    private Context context;
    private String[] srcPaths;

    public void generate(Generator generator) throws Exception {
        generator.generate(context);
    }

    /**
     * 开始分析指定文件夹指定包的api及param信息
     *
     * @param javaSourceDir 源码文件夹路径
     * @param rootPackage   java包路径
     * @return api生成管理器
     */
    public static ApiGenerateManager analyse(String javaSourceDir, String rootPackage) {
        ApiGenerateManager manager = new ApiGenerateManager();
        manager.javaSourceDir = javaSourceDir;
        manager.rootPackage = rootPackage;
        //获取java源码的文件夹路径
        manager.rootDirPath = LocalPathUtils.packToPath(javaSourceDir, rootPackage).getAbsolutePath();
        manager.context = Context.create(rootPackage, javaSourceDir);
        //分析控制器信息并保存到上下文
        ControllerAnalyse.create().analyse(manager.context);
        //分析参数类型信息并保存到上下文
        ParamClassAnalyse.create().analyse(manager.context);
        //分析枚举类型信息并保存到上下文
        EnumClassAnalyse.create().analyse(manager.context);
        return manager;
    }
}
