package org.rxjava.apikit.tool.analyse.impl;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.collections4.CollectionUtils;
import org.rxjava.apikit.tool.analyse.MessageAnalyse;
import org.rxjava.apikit.tool.generator.Context;
import org.rxjava.apikit.tool.info.*;
import org.rxjava.apikit.tool.utils.JdtClassWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import reactor.core.publisher.Flux;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author happy
 * 参数类的类型分析器
 */
public class ParamClassAnalyse implements MessageAnalyse {
    private static final Logger log = LoggerFactory.getLogger(ParamClassAnalyse.class);
    private Context context;
    private Set<ClassInfo> classInfoSet = new HashSet<>();
    private List<ParamClassInfo> paramClassInfos = new ArrayList<>();
    private ArrayDeque<ClassInfo> analysDeque = new ArrayDeque<>();
    private Map<ClassInfo, ParamClassInfo> paramClassMap = new HashMap<>();
    private Set<Class> typeBack = ImmutableSet.of(
            Class.class, Object.class, void.class, Void.class
    );

    public static ParamClassAnalyse create() {
        return new ParamClassAnalyse();
    }

    @Override
    public void analyse(Context context) {
        this.context = context;
        //获取待分析的参数类信息
        List<ClassInfo> classInfoList = Flux
                .fromIterable(context.getApis().getValues())
                .flatMapIterable(ApiClassInfo::getMethodInfos)
                .flatMapIterable(m -> {
                    List<TypeInfo> types = new ArrayList<>();
                    types.add(m.getResultDataType());
                    m.getParams().forEach(p -> types.add(p.getTypeInfo()));
                    return types;
                })
                .flatMapIterable(type -> {
                    List<TypeInfo> types = new ArrayList<>();
                    findTypes(type, types);
                    return types;
                })
                .filter(typeInfo -> typeInfo.getType().equals(TypeInfo.Type.OTHER))
                .filter(typeInfo -> !typeInfo.isCollection())
                .filter(typeInfo -> !typeInfo.isGeneric())
                .filter(typeInfo -> !typeInfo.isObject())
                .map(typeInfo -> new ClassInfo(typeInfo.getPackageName(), typeInfo.getClassName()))
                .distinct()
                .collectList()
                .block();

        classInfoSet.addAll(Objects.requireNonNull(classInfoList));
        analysDeque.addAll(classInfoList);
        handler();
        paramClassMap.forEach(context::addParamClassInfo);
    }

    /**
     * 开始分析类型信息
     */
    private void handler() {
        ClassInfo classInfo;
        while ((classInfo = analysDeque.poll()) != null) {
            ParamClassInfo paramClassInfo = analyseParamClass(classInfo);
            add(classInfo, paramClassInfo);

            //如果有超类，则将超类信息也放入
            List<TypeInfo> typeInfos = paramClassInfo.getProperties().stream().map(FieldInfo::getTypeInfo).collect(Collectors.toList());
            if (paramClassInfo.getSuperType() != null) {
                typeInfos.add(paramClassInfo.getSuperType());
            }

            List<ClassInfo> paramInfos = Flux
                    .fromIterable(typeInfos)
                    .flatMapIterable(type -> {
                        List<TypeInfo> types = new ArrayList<>();
                        findTypes(type, types);
                        return types;
                    })
                    .filter(typeInfo -> typeInfo.getType().equals(TypeInfo.Type.OTHER))
                    .filter(typeInfo -> !typeInfo.isCollection())
                    .filter(typeInfo -> !typeInfo.isGeneric())
                    .filter(typeInfo -> !typeInfo.isObject())
                    .map(typeInfo -> new ClassInfo(typeInfo.getPackageName(), typeInfo.getClassName()))
                    .distinct()
                    .collectList()
                    .block();

            Objects.requireNonNull(paramInfos).forEach(paramInfo -> {
                if (classInfoSet.add(paramInfo)) {
                    analysDeque.addFirst(paramInfo);
                }
            });
        }
    }

    private void add(ClassInfo classInfo, ParamClassInfo paramClassInfo) {
        paramClassMap.put(classInfo, paramClassInfo);
        paramClassInfos.add(paramClassInfo);
    }

    /**
     * 分析参数类信息
     */
    private ParamClassInfo analyseParamClass(ClassInfo classInfo) {
        try {
            Class clazz = Class.forName(classInfo.getPackageName() + "." + classInfo.getName());


            ParamClassInfo paramClassInfo = new ParamClassInfo();
            paramClassInfo.setPackageName(classInfo.getPackageName());
            paramClassInfo.setName(classInfo.getName());
            paramClassInfo.setClazz(clazz);
            //设置注释
            Optional<JdtClassWrapper> optionalJdtClassWrapper = JdtClassWrapper.getOptionalJavadocInfo(clazz, context.getJavaFilePath());
            optionalJdtClassWrapper.ifPresent(jdtClassWrapper -> paramClassInfo.setJavadocInfo(jdtClassWrapper.getClassComment()));

            Type genericSuperclass = clazz.getGenericSuperclass();
            if (genericSuperclass != null && !genericSuperclass.equals(Object.class)) {
                TypeInfo superTypeInfo = TypeInfo.form(genericSuperclass);
                paramClassInfo.setSuperType(superTypeInfo);
            }

            TypeVariable[] typeParameters = clazz.getTypeParameters();
            for (TypeVariable typeParameter : typeParameters) {
                paramClassInfo.addTypeParameter(typeParameter.getName());
            }

            Set<String> nameSet = new HashSet<>();
            for (Method method : clazz.getDeclaredMethods()) {
                if (Modifier.isPublic(method.getModifiers())
                        && method.getParameterTypes().length == 0
                        && !typeBack.contains(method.getReturnType())
                        && (method.getName().startsWith("get") || method.getName().startsWith("is"))
                ) {
                    try {
                        TypeInfo typeInfo = TypeInfo.form(method.getGenericReturnType());
                        PropertyDescriptor propertyDescriptor = BeanUtils.findPropertyForMethod(method);
                        if (propertyDescriptor != null) {
                            if (typeInfo == null) {
                                throw new RuntimeException("类型解析失败!错误的字段:" + method.getGenericReturnType());
                            }

                            String name = propertyDescriptor.getName();
                            if (!nameSet.contains(name)) {
                                PropertyInfo propertyInfo = new PropertyInfo(name, typeInfo);

                                optionalJdtClassWrapper.ifPresent(javadocInfo -> propertyInfo.setJavadocInfo(javadocInfo.getFieldComment(name)));
                                paramClassInfo.add(propertyInfo);
                                nameSet.add(name);
                            }
                        }
                    } catch (RuntimeException ex) {
                        log.info("错误,忽略属性继续:{}", method, ex);
                    }
                }
            }

            paramClassInfo.sortPropertys();
            return paramClassInfo;
        } catch (Throwable th) {
            log.error("分析message错误,classInfo:{}", classInfo, th);
            throw new RuntimeException(th);
        }
    }

    private void findTypes(TypeInfo type, List<TypeInfo> list) {
        list.add(type);
        if (CollectionUtils.isNotEmpty(type.getTypeArguments())) {
            type.getTypeArguments().forEach(t -> findTypes(t, list));
        }
    }
}
