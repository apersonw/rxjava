package top.rxjava.apikit.tool.wrapper;

import top.rxjava.apikit.tool.generator.Context;
import top.rxjava.apikit.tool.info.EnumParamClassInfo;

/**
 * @author happy
 * Java参数类信息包装器
 */
public class JavaEnumParamClassWrapper extends JavaWrapper<EnumParamClassInfo> {
    public JavaEnumParamClassWrapper(Context context, EnumParamClassInfo classInfo, String distRootPackage) {
        super(context, classInfo, distRootPackage);
    }
}
