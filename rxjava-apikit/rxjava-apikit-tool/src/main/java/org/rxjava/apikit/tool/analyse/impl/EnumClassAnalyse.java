package org.rxjava.apikit.tool.analyse.impl;

import lombok.extern.slf4j.Slf4j;
import org.rxjava.apikit.tool.analyse.Analyse;
import org.rxjava.apikit.tool.generator.Context;
import org.rxjava.apikit.tool.info.*;
import org.rxjava.apikit.tool.utils.JdtClassWrapper;
import org.springframework.beans.BeanUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 枚举分析器
 */
@Slf4j
public class EnumClassAnalyse implements Analyse {
    private Map<ClassInfo, EnumParamClassInfo> enumParamClassMap = new HashMap<>();
    private ArrayDeque<ClassInfo> analysDeque = new ArrayDeque<>();
    private Context context;

    @Override
    public void analyse(Context context) {
        this.context = context;
        Set<ClassTypeInfo> enumInfoSet = context.getEnumInfoSet();
        List<ClassInfo> classInfos = enumInfoSet.stream().map(classTypeInfo -> new ClassInfo(classTypeInfo.getPackageName(), classTypeInfo.getClassName())).collect(Collectors.toList());
        analysDeque.addAll(classInfos);
        handler();
        enumParamClassMap.forEach(context::addEnumParamClassInfo);
    }

    private void analyseEnum(ClassTypeInfo classTypeInfo) {
        //todo:分析枚举
        try {
            Class enumClass = Class.forName(classTypeInfo.getFullName());
            Enum[] enumConstants = (Enum[]) enumClass.getEnumConstants();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 开始处理分析到的枚举类
     */
    private void handler() {
        ClassInfo classInfo;
        while ((classInfo = analysDeque.poll()) != null) {
            EnumParamClassInfo enumParamClassInfo = analyseClassInfo(classInfo);
            enumParamClassMap.put(classInfo, enumParamClassInfo);
        }
    }

    /**
     * 分析类信息
     */
    private EnumParamClassInfo analyseClassInfo(ClassInfo classInfo) {
        try {
            Class enumClass = Class.forName(classInfo.getPackageName() + "." + classInfo.getClassName());
            Enum[] enumConstants = (Enum[]) enumClass.getEnumConstants();

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
                        return enumConstantInfo;
                    })
                    .collect(Collectors.toList());
            enumParamClassInfo.setEnumConstantInfos(enumConstantInfos);

            //设置注释
            Optional<JdtClassWrapper> optionalJdtClassWrapper = JdtClassWrapper.getOptionalJavadocInfo(context.getJavaFilePath(), enumClass);
            optionalJdtClassWrapper.ifPresent(jdtClassWrapper -> enumParamClassInfo.setJavaDocInfo(jdtClassWrapper.getClassComment()));

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
