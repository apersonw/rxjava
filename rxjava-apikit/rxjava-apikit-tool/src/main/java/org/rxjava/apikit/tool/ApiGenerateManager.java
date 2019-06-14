package org.rxjava.apikit.tool;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rxjava.apikit.tool.analyse.impl.ControllerAnalyse;
import org.rxjava.apikit.tool.analyse.impl.ParamClassAnalyse;
import org.rxjava.apikit.tool.generator.Context;
import org.rxjava.apikit.tool.generator.Generator;
import org.rxjava.apikit.tool.generator.impl.JavaClientApiGenerator;
import org.rxjava.apikit.tool.generator.impl.JavaScriptApiGenerator;
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
     * 对象工厂
     */
    private ObjectFactory objectFactory;
    /**
     * 需要分析的包文件夹路径
     */
    private String rootDirPath;
    /**
     * 上下文
     */
    private Context context;
    private String[] srcPaths;

    /**
     * 开始执行各种分析器
     */
    private void analyse() {
        //转换包路径为绝对路径
        rootDirPath = LocalPathUtils.packToPath(javaSourceDir, rootPackage).getAbsolutePath();
        //设置上下文
        context = new Context();
        context.setJavaFilePath(javaSourceDir);
        context.setRootPackage(rootPackage);

        //分析控制器信息并保存到上下文
        new ControllerAnalyse().analyse(context);
        //分析参数类型信息并保存到上下文
        new ParamClassAnalyse().analyse(context);
    }

    public void generate(Generator generator) throws Exception {
        generator.generate(context);
    }

    public ApiGenerateManager(String javaSourceDir, String rootPackage) {
        this.javaSourceDir = javaSourceDir;
        this.rootPackage = rootPackage;
        analyse();
    }
}
