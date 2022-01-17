package top.rxjava.apikit.tool.analyse.impl;

import com.google.common.collect.ImmutableSet;
import org.apache.commons.collections4.CollectionUtils;
import top.rxjava.apikit.tool.analyse.Analyse;
import top.rxjava.apikit.tool.generator.Context;
import top.rxjava.apikit.tool.info.*;
import top.rxjava.apikit.tool.utils.JdtClassWrapper;
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

/**
 * @author happy
 * 参数类的类型分析器
 */
public class ParamClassAnalyse implements Analyse {
    private static final Logger log = LoggerFactory.getLogger(ParamClassAnalyse.class);
    private Context context;
    private Set<CommonClassInfo> classInfoSet = new HashSet<>();
    private Set<ClassTypeInfo> enumInfoSet = new HashSet<>();
    private List<ParamClassInfo> paramClassInfos = new ArrayList<>();
    private ArrayDeque<CommonClassInfo> analysDeque = new ArrayDeque<>();
    private Map<CommonClassInfo, ParamClassInfo> paramClassMap = new HashMap<>();
    private Set<Class<?>> typeBack = ImmutableSet.of(
            Class.class, Object.class, void.class, Void.class
    );

    public static ParamClassAnalyse create() {
        return new ParamClassAnalyse();
    }

    @Override
    public void analyse(Context context) {
        this.context = context;
        //1、获取待分析的参数类信息
        //2、获得待分析的参数类属性类信息
        //3、过滤掉集合类、泛型类、对象类、ObjectId类
        //4、转换为ClassInfo类型
        List<CommonClassInfo> classInfoList = Flux
                .fromIterable(context.getApis().getValues())
                .flatMapIterable(ApiClassInfo::getApiMethodList)
                .flatMapIterable(apiMethodInfo -> {
                    List<ClassTypeInfo> classTypeInfoList = new ArrayList<>();
                    classTypeInfoList.add(apiMethodInfo.getResultDataType());
                    apiMethodInfo.getParams().forEach(param -> classTypeInfoList.add(param.getClassTypeInfo()));
                    return classTypeInfoList;
                })
                .flatMapIterable(classTypeInfo -> {
                    List<ClassTypeInfo> classTypeInfoList = new ArrayList<>();
                    findTypes(classTypeInfo, classTypeInfoList);
                    return classTypeInfoList;
                })
                .filter(this::filterClassTypeInfo)
                .map(typeInfo -> new CommonClassInfo(typeInfo.getPackageName(), typeInfo.getClassName()))
                .distinct()
                .collectList()
                .block();

        classInfoSet.addAll(Objects.requireNonNull(classInfoList));
        analysDeque.addAll(classInfoList);
        handler();
        paramClassMap.forEach(context::addParamClassInfo);
        this.context.setEnumInfoSet(this.enumInfoSet);
    }

    /**
     * 开始处理分析到的方法参数类属性信息
     */
    private void handler() {
        CommonClassInfo classInfo;
        while ((classInfo = analysDeque.poll()) != null) {
            ParamClassInfo paramClassInfo = analyseClassInfo(classInfo);
            add(classInfo, paramClassInfo);

            //1、获得属性类的类型信息
            //2、如果有超类，则将超类的属性类的类型信息也放入
            List<ClassTypeInfo> propertyClassTypeInfoList = new ArrayList<>();
            paramClassInfo.getProperties().forEach(propertyInfo -> {
                propertyClassTypeInfoList.add(propertyInfo.getClassTypeInfo());
            });
            if (paramClassInfo.getSuperType() != null) {
                propertyClassTypeInfoList.add(paramClassInfo.getSuperType());
            }

            List<CommonClassInfo> propertyClassInfoList = Flux
                    .fromIterable(propertyClassTypeInfoList)
                    .flatMapIterable(classTypeInfo -> {
                        List<ClassTypeInfo> classTypeInfos = new ArrayList<>();
                        findTypes(classTypeInfo, classTypeInfos);
                        return classTypeInfos;
                    })
                    .filter(this::filterClassTypeInfo)
                    .map(typeInfo -> new CommonClassInfo(typeInfo.getPackageName(), typeInfo.getClassName()))
                    .distinct()
                    .collectList()
                    .block();

            Objects.requireNonNull(propertyClassInfoList).forEach(propertyClassInfo -> {
                //3、检查属性类信息是否已分析过
                if (classInfoSet.add(propertyClassInfo)) {
                    //4、将获取到且未分析过的属性类信息放到待分析队列中
                    analysDeque.addFirst(propertyClassInfo);
                }
            });
        }
    }

    private void add(CommonClassInfo classInfo, ParamClassInfo paramClassInfo) {
        paramClassMap.put(classInfo, paramClassInfo);
        paramClassInfos.add(paramClassInfo);
    }

    /**
     * 分析类信息
     */
    private ParamClassInfo analyseClassInfo(CommonClassInfo classInfo) {
        try {
            Class<?> clazz = Class.forName(classInfo.getPackageName() + "." + classInfo.getClassName());

            ParamClassInfo paramClassInfo = new ParamClassInfo();
            paramClassInfo.setPackageName(classInfo.getPackageName());
            paramClassInfo.setClassName(classInfo.getClassName());
            paramClassInfo.setClazz(clazz);
            //设置注释
            Optional<JdtClassWrapper> optionalJdtClassWrapper = JdtClassWrapper.getOptionalJavadocInfo(context.getJavaFilePath(), clazz);
            optionalJdtClassWrapper.ifPresent(jdtClassWrapper -> paramClassInfo.setJavaDocInfo(jdtClassWrapper.getClassComment()));

            //获取类的超类
            Type genericSuperclass = clazz.getGenericSuperclass();
            if (genericSuperclass != null && !genericSuperclass.equals(Object.class)) {
                ClassTypeInfo superTypeInfo = ClassTypeInfo.form(genericSuperclass);
                paramClassInfo.setSuperType(superTypeInfo);
            }

            TypeVariable<?>[] typeParameters = clazz.getTypeParameters();
            for (TypeVariable<?> typeParameter : typeParameters) {
                paramClassInfo.addTypeParameter(typeParameter.getName());
            }

            //通过方法获取类型字段名称
            Set<String> nameSet = new HashSet<>();
            for (Method method : clazz.getDeclaredMethods()) {
                if (Modifier.isPublic(method.getModifiers())
                        && method.getParameterTypes().length == 0
                        && !typeBack.contains(method.getReturnType())
                        && (method.getName().startsWith("get") || method.getName().startsWith("is"))
                ) {
                    try {
                        ClassTypeInfo typeInfo = ClassTypeInfo.form(method.getGenericReturnType());
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
            log.error("分析param错误,classInfo.getClassName:{}", classInfo.getClassName(), th);
            throw new RuntimeException(th);
        }
    }

    private void findTypes(ClassTypeInfo type, List<ClassTypeInfo> list) {
        list.add(type);
        if (CollectionUtils.isNotEmpty(type.getTypeArguments())) {
            type.getTypeArguments().forEach(t -> findTypes(t, list));
        }
    }

    private boolean filterClassTypeInfo(ClassTypeInfo classTypeInfo) {
        if (classTypeInfo.isEnumClass()) {
            this.enumInfoSet.add(classTypeInfo);
            return false;
        }

        return ClassTypeInfo.TypeEnum.OTHER.equals(classTypeInfo.getType())
                && !classTypeInfo.isCollection()
                && !classTypeInfo.isGeneric()
                && !classTypeInfo.isObject()
                && classTypeInfo.isObjectId();
    }
}
