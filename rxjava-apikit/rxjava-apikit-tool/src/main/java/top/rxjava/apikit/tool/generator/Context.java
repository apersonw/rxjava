package top.rxjava.apikit.tool.generator;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import top.rxjava.apikit.tool.info.*;
import top.rxjava.apikit.tool.wrapper.BuilderWrapper;

import java.util.*;

/**
 * 上下文
 *
 * @author happy
 */
@Setter
@Getter
public class Context {

    public static Context create(String rootPackage, String javaFilePath, boolean reactive) {
        Context context = new Context();
        context.rootPackage = rootPackage;
        context.javaFilePath = javaFilePath;
        context.reactive=reactive;
        return context;
    }

    /**
     * 是否响应式项目
     */
    private boolean reactive;
    /**
     * Api类的包信息
     */
    protected PackageInfo<ApiClassInfo> apis = new PackageInfo<>();
    /**
     * 主包路径
     */
    private String rootPackage;
    /**
     * Java文件路径
     */
    private String javaFilePath;
    /**
     * 参数类包装map
     */
    private Map<String, BuilderWrapper<ParamClassInfo>> paramClassWrapperMap;
    /**
     * 枚举类包装map
     */
    private Map<String, BuilderWrapper<EnumParamClassInfo>> enumParamClassWrapperMap;
    /**
     * 枚举类
     */
    private Set<ClassTypeInfo> enumInfoSet = new HashSet<>();

    private TreeMap<String, ParamClassInfo> fullNameParamMap = new TreeMap<>(Comparator.comparing(r -> r));

    public void addParamClassInfo(CommonClassInfo key, ParamClassInfo paramClassInfo) {
        paramMap.put(key, paramClassInfo);
        fullNameParamMap.put(key.getFullName(), paramClassInfo);
    }

    public void addEnumParamClassInfo(CommonClassInfo key, EnumParamClassInfo enumParamClassInfo) {
        enumParamMap.put(key, enumParamClassInfo);
    }

    private TreeMap<CommonClassInfo, ParamClassInfo> paramMap = new TreeMap<>(Comparator.comparing(CommonClassInfo::getFullName));

    private TreeMap<CommonClassInfo, EnumParamClassInfo> enumParamMap = new TreeMap<>(Comparator.comparing(CommonClassInfo::getFullName));

    public void addApi(ApiClassInfo apiInfo) {
        apis.add(apiInfo.getPackageName(), apiInfo);
    }

    public Collection<ParamClassInfo> getParamClassInfos() {
        return paramMap.values();
    }

    public Collection<EnumParamClassInfo> getEnumParamClassInfos() {
        return enumParamMap.values();
    }

    public BuilderWrapper<ParamClassInfo> getParamWrapper(String fullName) {
        return paramClassWrapperMap.get(fullName);
    }

    public BuilderWrapper<? extends CommonClassInfo> getParamOrEnumWrapper(String fullName) {
        BuilderWrapper<ParamClassInfo> paramClassInfoBuilderWrapper = paramClassWrapperMap.get(fullName);
        if (ObjectUtils.isEmpty(paramClassInfoBuilderWrapper)) {
            return enumParamClassWrapperMap.get(fullName);
        }
        return paramClassInfoBuilderWrapper;
    }

}
