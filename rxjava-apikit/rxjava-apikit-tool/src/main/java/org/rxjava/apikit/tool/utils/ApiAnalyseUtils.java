package org.rxjava.apikit.tool.utils;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.ClassUtils;
import org.rxjava.apikit.tool.info.ParamInfo;
import org.rxjava.apikit.tool.info.TypeInfo;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author happy 2019/10/27 19:16
 */
public class ApiAnalyseUtils {

    /**
     * 分析输出参数类型
     */
    public static ParamInfo analyseParamInfo(Type returnType) {
        ParamInfo paramInfo = new ParamInfo();
        if (returnType instanceof Class) {
            Class cls = (Class) returnType;
            paramInfo.setName(cls.getName());
            paramInfo.setSimpleName(cls.getSimpleName());
            Package clsPackage = cls.getPackage();
            ParamInfo finalParamInfo = paramInfo;
            Optional.ofNullable(clsPackage).ifPresent(r -> finalParamInfo.setPackageName(r.getName()));

            //原始或包装类型
            if (ClassUtils.isPrimitiveOrWrapper(cls)) {
                paramInfo.setPrimitiveOrWrapper(true);
            } else if (cls.isArray()) {
                paramInfo.setArray(true);
            } else {
                //分析
                Field[] declaredFields = cls.getDeclaredFields();
                ParamType paramType = ParamType.map.get(cls.getName());
                //如果不等于null说明非基础类型，需要继续分析
                if (null == paramType) {
                    List<ParamInfo> childParamInfo = finalParamInfo.getChildParamInfo();
                    for (Field declaredField : declaredFields) {
                        Type genericType = declaredField.getGenericType();
                        ParamInfo fieldParamInnfo = analyseParamInfo(genericType);
                        childParamInfo.add(fieldParamInnfo);
                    }
                    finalParamInfo.setChildParamInfo(childParamInfo);
                }
            }
        } else if (returnType instanceof ParameterizedType) {
            ParameterizedType parameterzedType = (ParameterizedType) returnType;
            Type[] actualTypeArguments = parameterzedType.getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                paramInfo = analyseParamInfo(actualTypeArguments[0]);
            }
        } else if (returnType instanceof TypeVariable) {
            System.out.println(returnType);
        }
        return paramInfo;
    }

    public enum ParamType {
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
        DATE;

        private static final ImmutableMap<String, ParamType> map = ImmutableMap.<String, ParamType>builder()
                .put(void.class.getSimpleName(), VOID)
                .put(boolean.class.getSimpleName(), BOOLEAN)
                .put(byte.class.getSimpleName(), BYTE)
                .put(short.class.getSimpleName(), SHORT)
                .put(int.class.getSimpleName(), INT)
                .put(long.class.getSimpleName(), LONG)
                .put(float.class.getSimpleName(), FLOAT)
                .put(double.class.getSimpleName(), DOUBLE)
                .put(char.class.getSimpleName(), DOUBLE)

                .put(Void.class.getName(), VOID)
                .put(Boolean.class.getName(), BOOLEAN)
                .put(Byte.class.getName(), BYTE)
                .put(Short.class.getName(), SHORT)
                .put(Integer.class.getName(), INT)
                .put(Long.class.getName(), LONG)
                .put(Float.class.getName(), FLOAT)
                .put(Double.class.getName(), DOUBLE)

                .put(String.class.getName(), STRING)
                .put(Character.class.getName(), STRING)
                .put(Date.class.getName(), DATE)
                .put(Instant.class.getName(), DATE)
                .put(LocalDateTime.class.getName(), DATE)
                .put(LocalDate.class.getName(), DATE)
                .put(LocalTime.class.getName(), DATE)
                .build();
    }
}
