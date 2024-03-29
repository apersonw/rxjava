package org.rxjava.apikit.tool.generator;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ObjectUtils;
import org.rxjava.apikit.tool.info.*;
import org.rxjava.apikit.tool.wrapper.BuilderWrapper;

import java.util.*;

/**
 * 上下文
 *
 * @author happy
 */
@Setter
@Getter
public class Context {

    public static Context create(String rootPackage, String javaFilePath) {
        Context context = new Context();
        context.rootPackage = rootPackage;
        context.javaFilePath = javaFilePath;
        return context;
    }

    /**
     * 包信息
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
     * 消息包装map
     */
    private Map<String, BuilderWrapper<ParamClassInfo>> paramClassWrapperMap;
    /**
     * 消息包装map
     */
    private Map<String, BuilderWrapper<EnumParamClassInfo>> enumParamClassWrapperMap;
    /**
     * 枚举类
     */
    private Set<ClassTypeInfo> enumInfoSet = new HashSet<>();

    private TreeMap<String, ParamClassInfo> fullNameParamMap = new TreeMap<>(Comparator.comparing(r -> r));

    public void addParamClassInfo(ClassInfo key, ParamClassInfo paramClassInfo) {
        paramMap.put(key, paramClassInfo);
        fullNameParamMap.put(key.getFullName(), paramClassInfo);
    }

    public void addEnumParamClassInfo(ClassInfo key, EnumParamClassInfo enumParamClassInfo) {
        enumParamMap.put(key, enumParamClassInfo);
    }

    private TreeMap<ClassInfo, ParamClassInfo> paramMap = new TreeMap<>(Comparator.comparing(ClassInfo::getFullName));

    private TreeMap<ClassInfo, EnumParamClassInfo> enumParamMap = new TreeMap<>(Comparator.comparing(ClassInfo::getFullName));

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

    public BuilderWrapper<? extends ClassInfo> getParamOrEnumWrapper(String fullName) {
        BuilderWrapper<ParamClassInfo> paramClassInfoBuilderWrapper = paramClassWrapperMap.get(fullName);
        if (ObjectUtils.isEmpty(paramClassInfoBuilderWrapper)) {
            return enumParamClassWrapperMap.get(fullName);
        }
        return paramClassInfoBuilderWrapper;
    }

}
