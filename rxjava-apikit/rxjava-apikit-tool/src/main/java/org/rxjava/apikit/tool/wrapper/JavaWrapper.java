package org.rxjava.apikit.tool.wrapper;

import com.google.common.collect.ImmutableMap;
import org.rxjava.apikit.tool.generator.Context;
import org.rxjava.apikit.tool.info.CommonClassInfo;
import org.rxjava.apikit.tool.info.ClassTypeInfo;

import java.util.Date;
import java.util.List;

/**
 * @author happy
 */
class JavaWrapper<T extends CommonClassInfo> extends BuilderWrapper<T> {
    JavaWrapper(Context context, T classInfo, String rootPackage) {
        super(context, classInfo, rootPackage);
    }

    String toJavaTypeString(ClassTypeInfo typeInfo, boolean isWrap, boolean isArrayList, boolean isTypeArguments) {
        return toJavaTypeString(typeInfo, isWrap, isArrayList, isTypeArguments, isArrayList);
    }

    /**
     * 参数类型是否处理数组
     */
    private String toJavaTypeString(ClassTypeInfo typeInfo, boolean isWrap, boolean isArrayList, boolean isTypeArguments, boolean isChildArrayList) {
        StringBuilder sb = new StringBuilder();
        ClassTypeInfo.Type type = typeInfo.getType();
        if (type == ClassTypeInfo.Type.BYTE && typeInfo.isArray()) {
            sb.append("byte[]");
        } else if (isArrayList && typeInfo.isArray()) {
            toJavaArrayTypeString(typeInfo, sb, isWrap, true);
            return sb.toString();
        } else if (typeInfo.isOtherType()) {
            sb.append(typeInfo.getClassName());
        } else if (isWrap) {
            sb.append(toJavaWrapString(type));
        } else {
            sb.append(toJavaString(type));
        }
        List<ClassTypeInfo> typeArguments = typeInfo.getTypeArguments();
        if (!typeArguments.isEmpty() && isTypeArguments) {
            sb.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                ClassTypeInfo typeArgument = typeArguments.get(i);
                if (i > 0) {
                    sb.append(',');
                }
                sb.append(toJavaTypeString(typeArgument, true, isChildArrayList));
            }
            sb.append('>');
        }
        return sb.toString();
    }

    /**
     * 处理数组
     */
    private void toJavaArrayTypeString(ClassTypeInfo typeInfo, StringBuilder sb, boolean isWrap, boolean isArrayList) {
        sb.append("java.util.ArrayList");
        sb.append('<');
        sb.append(toJavaTypeString(typeInfo, true, false));
        sb.append('>');
    }

    public String toJavaTypeString(ClassTypeInfo typeInfo, boolean isWrap, boolean isArrayList) {
        return toJavaTypeString(typeInfo, isWrap, isArrayList, true);
    }

    private static String toJavaWrapString(ClassTypeInfo.Type type) {
        return TYPE_WRAP_MAP.get(type).getSimpleName();
    }

    private static final ImmutableMap<ClassTypeInfo.Type, Class> TYPE_WRAP_MAP
            = ImmutableMap.<ClassTypeInfo.Type, Class>builder()
            .put(ClassTypeInfo.Type.VOID, Void.class)
            .put(ClassTypeInfo.Type.BOOLEAN, Boolean.class)
            .put(ClassTypeInfo.Type.BYTE, Byte.class)
            .put(ClassTypeInfo.Type.SHORT, Short.class)
            .put(ClassTypeInfo.Type.INT, Integer.class)
            .put(ClassTypeInfo.Type.LONG, Long.class)
            .put(ClassTypeInfo.Type.FLOAT, Float.class)
            .put(ClassTypeInfo.Type.DOUBLE, Double.class)
            .put(ClassTypeInfo.Type.DATE, Date.class)
            .put(ClassTypeInfo.Type.STRING, String.class)
            .build();

    private static String toJavaString(ClassTypeInfo.Type type) {
        return TYPE_MAP.get(type).getSimpleName();
    }

    private static final ImmutableMap<ClassTypeInfo.Type, Class> TYPE_MAP
            = ImmutableMap.<ClassTypeInfo.Type, Class>builder()
            .put(ClassTypeInfo.Type.VOID, void.class)
            .put(ClassTypeInfo.Type.BOOLEAN, boolean.class)
            .put(ClassTypeInfo.Type.BYTE, byte.class)
            .put(ClassTypeInfo.Type.SHORT, short.class)
            .put(ClassTypeInfo.Type.INT, int.class)
            .put(ClassTypeInfo.Type.LONG, long.class)
            .put(ClassTypeInfo.Type.FLOAT, float.class)
            .put(ClassTypeInfo.Type.DOUBLE, double.class)
            .put(ClassTypeInfo.Type.DATE, Date.class)
            .put(ClassTypeInfo.Type.STRING, String.class)
            .build();
}
