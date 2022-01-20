package top.rxjava.apikit.tool.analyse.impl;

import lombok.extern.slf4j.Slf4j;
import top.rxjava.apikit.tool.analyse.Analyse;
import top.rxjava.apikit.tool.Context;
import top.rxjava.apikit.tool.info.ClassTypeInfo;
import top.rxjava.apikit.tool.info.CommonClassInfo;
import top.rxjava.apikit.tool.info.EnumConstantInfo;
import top.rxjava.apikit.tool.info.EnumParamClassInfo;
import top.rxjava.apikit.tool.utils.JdtClassWrapper;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 枚举分析器
 */
@Slf4j
public class EnumClassAnalyse implements Analyse {
    private Map<CommonClassInfo, EnumParamClassInfo> enumParamClassMap = new HashMap<>();
    private ArrayDeque<CommonClassInfo> analysDeque = new ArrayDeque<>();
    private Context context;

    @Override
    public void analyse(Context context) {
        this.context = context;
        Set<ClassTypeInfo> enumInfoSet = context.getEnumInfoSet();
        List<CommonClassInfo> classInfos = enumInfoSet.stream().map(classTypeInfo -> new CommonClassInfo(classTypeInfo.getPackageName(), classTypeInfo.getClassName())).collect(Collectors.toList());
        analysDeque.addAll(classInfos);
        handler();
        enumParamClassMap.forEach(context::addEnumParamClassInfo);
    }

    private void analyseEnum(ClassTypeInfo classTypeInfo) {
        //todo:分析枚举
        try {
            Class<?> enumClass = Class.forName(classTypeInfo.getFullName());
            Enum<?>[] enumConstants = (Enum<?>[]) enumClass.getEnumConstants();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始处理分析到的枚举类
     */
    private void handler() {
        CommonClassInfo classInfo;
        while ((classInfo = analysDeque.poll()) != null) {
            EnumParamClassInfo enumParamClassInfo = analyseClassInfo(classInfo);
            enumParamClassMap.put(classInfo, enumParamClassInfo);
        }
    }

    /**
     * 分析类信息
     */
    private EnumParamClassInfo analyseClassInfo(CommonClassInfo classInfo) {
        try {
            Class<?> enumClass = Class.forName(classInfo.getPackageName() + "." + classInfo.getClassName());
            Enum<?>[] enumConstants = (Enum<?>[]) enumClass.getEnumConstants();

            EnumParamClassInfo enumParamClassInfo = new EnumParamClassInfo();
            enumParamClassInfo.setPackageName(classInfo.getPackageName());
            enumParamClassInfo.setClassName(classInfo.getClassName());
            enumParamClassInfo.setClazz(enumClass);

            //设置枚举常量信息
            List<EnumConstantInfo> enumConstantInfos = Arrays.stream(enumConstants)
                    .map(a -> {
                        EnumConstantInfo enumConstantInfo = new EnumConstantInfo();
                        enumConstantInfo.setName(a.name());
                        enumConstantInfo.setOrdinal(a.ordinal());
                        //设置注释
                        Optional<JdtClassWrapper> optionalJdtClassWrapper = JdtClassWrapper.getOptionalJavadocInfo(context.getJavaFilePath(), enumClass);
                        optionalJdtClassWrapper.ifPresent(jdtClassWrapper -> enumConstantInfo.setJavaDocInfo(jdtClassWrapper.getEnumElementComment(a.name())));
                        return enumConstantInfo;
                    })
                    .collect(Collectors.toList());
            enumParamClassInfo.setEnumConstantInfos(enumConstantInfos);

            //获取类的超类
            Type genericSuperclass = enumClass.getGenericSuperclass();
            if (genericSuperclass != null && !genericSuperclass.equals(Object.class)) {
                ClassTypeInfo superTypeInfo = ClassTypeInfo.form(genericSuperclass);
                enumParamClassInfo.setSuperType(superTypeInfo);
            }

            TypeVariable<?>[] typeParameters = enumClass.getTypeParameters();
            for (TypeVariable<?> typeParameter : typeParameters) {
                enumParamClassInfo.addTypeParameter(typeParameter.getName());
            }

            return enumParamClassInfo;
        } catch (Throwable th) {
            log.error("分析enumParam错误,classInfo:{}", classInfo, th);
            throw new RuntimeException(th);
        }
    }

    public static EnumClassAnalyse create() {
        return new EnumClassAnalyse();
    }
}
