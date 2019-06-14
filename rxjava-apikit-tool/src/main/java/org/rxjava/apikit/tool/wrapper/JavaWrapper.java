package org.rxjava.apikit.tool.wrapper;

import com.google.common.collect.ImmutableMap;
import org.rxjava.apikit.tool.generator.Context;
import org.rxjava.apikit.tool.info.ClassInfo;
import org.rxjava.apikit.tool.info.TypeInfo;

import java.util.Date;
import java.util.List;

/**
 * @author happy
 */
class JavaWrapper<T extends ClassInfo> extends BuilderWrapper<T> {
    JavaWrapper(Context context, T classInfo, String rootPackage) {
        super(context, classInfo, rootPackage);
    }

    String toJavaTypeString(TypeInfo typeInfo, boolean isWrap, boolean isArrayList, boolean isTypeArguments) {
        return toJavaTypeString(typeInfo, isWrap, isArrayList, isTypeArguments, isArrayList);
    }

    /**
     * 参数类型是否处理数组
     */
    private String toJavaTypeString(TypeInfo typeInfo, boolean isWrap, boolean isArrayList, boolean isTypeArguments, boolean isChildArrayList) {
        StringBuilder sb = new StringBuilder();
        TypeInfo.Type type = typeInfo.getType();
        if (type == TypeInfo.Type.BYTE && typeInfo.isArray()) {
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
        List<TypeInfo> typeArguments = typeInfo.getTypeArguments();
        if (!typeArguments.isEmpty() && isTypeArguments) {
            sb.append('<');
            for (int i = 0; i < typeArguments.size(); i++) {
                TypeInfo typeArgument = typeArguments.get(i);
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
    private void toJavaArrayTypeString(TypeInfo typeInfo, StringBuilder sb, boolean isWrap, boolean isArrayList) {
        sb.append("java.util.ArrayList");
        sb.append('<');
        sb.append(toJavaTypeString(typeInfo, true, false));
        sb.append('>');
    }

    public String toJavaTypeString(TypeInfo typeInfo, boolean isWrap, boolean isArrayList) {
        return toJavaTypeString(typeInfo, isWrap, isArrayList, true);
    }

    private static String toJavaWrapString(TypeInfo.Type type) {
        return TYPE_WRAP_MAP.get(type).getSimpleName();
    }

    private static final ImmutableMap<TypeInfo.Type, Class> TYPE_WRAP_MAP
            = ImmutableMap.<TypeInfo.Type, Class>builder()
            .put(TypeInfo.Type.VOID, Void.class)
            .put(TypeInfo.Type.BOOLEAN, Boolean.class)
            .put(TypeInfo.Type.BYTE, Byte.class)
            .put(TypeInfo.Type.SHORT, Short.class)
            .put(TypeInfo.Type.INT, Integer.class)
            .put(TypeInfo.Type.LONG, Long.class)
            .put(TypeInfo.Type.FLOAT, Float.class)
            .put(TypeInfo.Type.DOUBLE, Double.class)
            .put(TypeInfo.Type.DATE, Date.class)
            .put(TypeInfo.Type.STRING, String.class)
            .build();

    private static String toJavaString(TypeInfo.Type type) {
        return TYPE_MAP.get(type).getSimpleName();
    }

    private static final ImmutableMap<TypeInfo.Type, Class> TYPE_MAP
            = ImmutableMap.<TypeInfo.Type, Class>builder()
            .put(TypeInfo.Type.VOID, void.class)
            .put(TypeInfo.Type.BOOLEAN, boolean.class)
            .put(TypeInfo.Type.BYTE, byte.class)
            .put(TypeInfo.Type.SHORT, short.class)
            .put(TypeInfo.Type.INT, int.class)
            .put(TypeInfo.Type.LONG, long.class)
            .put(TypeInfo.Type.FLOAT, float.class)
            .put(TypeInfo.Type.DOUBLE, double.class)
            .put(TypeInfo.Type.DATE, Date.class)
            .put(TypeInfo.Type.STRING, String.class)
            .build();
}
