package org.rxjava.apikit.tool.utils;

import com.google.common.collect.ImmutableMap;
import org.rxjava.apikit.tool.analyse.impl.ApiAnalyse;
import org.rxjava.apikit.tool.info.ParamInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.lang.reflect.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

/**
 * @author happy 2019/10/27 19:16
 * 类分析器
 */
public class ClassAnalyseUtils {

    /**
     * 分析参数类型
     * 原始类型(Class)、参数化类型(ParameterizedType)、数组类型(GenericArrayType)、类型变量(TypeVariable)
     */
    public static ParamInfo analyse(Type returnType) {
        ParamInfo paramInfo = new ParamInfo();
        if (returnType instanceof Class) {
            analyseClass(returnType, paramInfo);
        } else if (returnType instanceof ParameterizedType) {
            paramInfo = analyseParameterizedType(returnType);
        } else if (returnType instanceof GenericArrayType) {
            //泛型数组类型
        }
        return paramInfo;
    }

    /**
     * 分析原始类型
     * 原始类型，不仅仅包含我们平常所指的类，还包括枚举、数组、注解等；
     */
    private static void analyseClass(Type returnType, ParamInfo paramInfo) {
        Class<?> cls = (Class<?>) returnType;
        //获取数组的元素类型
        if (cls.isArray()) {
            cls = cls.getComponentType();
            paramInfo.setArray(true);
        }
        paramInfo.setName(cls.getName());
        paramInfo.setSimpleName(cls.getSimpleName());
        Package clsPackage = cls.getPackage();
        Optional.ofNullable(clsPackage).ifPresent(r -> paramInfo.setPackageName(r.getName()));

        //分析类变量信息
        Field[] declaredFields = cls.getDeclaredFields();
        BaseType baseType = BaseType.IMMUTABLE_MAP.get(cls.getName());
        //非基本类型，继续分析
        if (null == baseType) {
            List<ParamInfo> childParamInfo = new ArrayList<>();
            for (Field declaredField : declaredFields) {
                Type genericType = declaredField.getGenericType();

                ParamInfo fieldParamInnfo;
                if (genericType instanceof TypeVariable) {
                    TypeVariable<?> typeVariable = (TypeVariable<?>) genericType;
                    //类型变量，即泛型中的变量；例如：T、K、V等变量，可以表示任何类；在这需要强调的是，TypeVariable代表着泛型中的变量，而ParameterizedType则代表整个泛型；
                    fieldParamInnfo = new ParamInfo();
                } else {
                    fieldParamInnfo = analyse(genericType);
                }

                String fieldName = declaredField.getName();
                fieldParamInnfo.setFieldName(fieldName);
                childParamInfo.add(fieldParamInnfo);
            }
            paramInfo.setChildParamInfo(childParamInfo);
        }
    }

    /**
     * 分析参数化类型
     * 参数化类型，就是我们平常所用到的泛型List、Map；
     */
    private static ParamInfo analyseParameterizedType(Type returnType) {
        ParameterizedType parameterzedType = (ParameterizedType) returnType;
        //获取<>中实际类型,可能会存在多个泛型，例如Map<K,V>,所以会返回Type[]数组；
        Type[] actualTypeArguments = parameterzedType.getActualTypeArguments();

        //分析Map<>的Map类型
        ParamInfo paramInfo = analyse(parameterzedType.getRawType());

        //分析<>里面的类型
        Map<String, ParamInfo> actualTypes = new HashMap<>();
        for (Type actualTypeArgument : actualTypeArguments) {
            ParamInfo actualType = analyse(actualTypeArgument);
            actualTypes.put(actualTypeArgument.getTypeName(), actualType);
        }
        paramInfo.setActualTypes(actualTypes);

        return paramInfo;
    }

    /**
     * 分析参数化类型
     * 参数化类型，就是我们平常所用到的泛型List、Map；
     */
    private static ParamInfo analyseTypeVariable(Type returnType, ParamInfo parentParamInfo) {
        System.out.println("分析泛型变量");
        TypeVariable<?> typeVariable = (TypeVariable<?>) returnType;
        return new ParamInfo();
    }

    public enum BaseType {
        /**
         * 基本类型
         */
        VOID,
        BOOLEAN,
        BYTE,
        SHORT,
        INT,
        LONG,
        FLOAT,
        DOUBLE,
        STRING,
        DATE,
        MONO,
        FLUX,
        MAP,
        LIST;

        /**
         * 以下类型均不再往下继续分析
         */
        private static final ImmutableMap<String, BaseType> IMMUTABLE_MAP = ImmutableMap.<String, BaseType>builder()
                .put(void.class.getSimpleName(), VOID)
                //1种真值类型
                .put(boolean.class.getSimpleName(), BOOLEAN)
                //4种整型
                .put(byte.class.getSimpleName(), BYTE)
                .put(short.class.getSimpleName(), SHORT)
                .put(int.class.getSimpleName(), INT)
                .put(long.class.getSimpleName(), LONG)
                //2种浮点
                .put(float.class.getSimpleName(), FLOAT)
                .put(double.class.getSimpleName(), DOUBLE)
                //1种字符
                .put(char.class.getSimpleName(), STRING)
                //9种基本类型包装
                .put(Void.class.getName(), VOID)
                .put(Boolean.class.getName(), BOOLEAN)
                .put(Byte.class.getName(), BYTE)
                .put(Short.class.getName(), SHORT)
                .put(Integer.class.getName(), INT)
                .put(Long.class.getName(), LONG)
                .put(Float.class.getName(), FLOAT)
                .put(Double.class.getName(), DOUBLE)
                //7种jdk包装类型
                .put(String.class.getName(), STRING)
                .put(Character.class.getName(), STRING)
                .put(Date.class.getName(), DATE)
                .put(Instant.class.getName(), DATE)
                .put(LocalDateTime.class.getName(), DATE)
                .put(LocalDate.class.getName(), DATE)
                .put(LocalTime.class.getName(), DATE)
                //2中响应式编程包装类型
                .put(Mono.class.getName(), MONO)
                .put(Flux.class.getName(), FLUX)
                //Map和List类型均不再往下分析了
                .put(Map.class.getName(),MAP)
                .put(List.class.getName(),LIST)
                .build();
    }
}
