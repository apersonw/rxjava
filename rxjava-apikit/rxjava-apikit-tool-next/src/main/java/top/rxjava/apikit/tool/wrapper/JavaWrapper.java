package top.rxjava.apikit.tool.wrapper;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import top.rxjava.apikit.tool.Context;
import top.rxjava.apikit.tool.info.ClassTypeInfo;
import top.rxjava.apikit.tool.info.CommonClassInfo;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @author happy
 */
@Slf4j
class JavaWrapper<T extends CommonClassInfo> extends BuilderWrapper<T> {
    JavaWrapper(Context context, T classInfo, String rootPackage) {
        super(context, classInfo, rootPackage);
    }

    String toJavaTypeString(ClassTypeInfo classTypeInfo, boolean isWrap, boolean isArrayList, boolean isTypeArguments) {
        return toJavaTypeString(classTypeInfo, isWrap, isArrayList, isTypeArguments, isArrayList);
    }

    /**
     * 参数类型是否处理数组
     */
    private String toJavaTypeString(ClassTypeInfo classTypeInfo, boolean isWrap, boolean isArrayList, boolean isTypeArguments, boolean isChildArrayList) {
        StringBuilder sb = new StringBuilder();
        ClassTypeInfo.TypeEnum type = classTypeInfo.getType();
        if (type == ClassTypeInfo.TypeEnum.BYTE && classTypeInfo.isArray()) {
            sb.append("byte[]");
        // } else if (isArrayList && classTypeInfo.isArray()) {
        //     toJavaArrayTypeString(classTypeInfo, sb, isWrap, true);
        //     return sb.toString();
        } else if (classTypeInfo.isOtherType()) {
            sb.append(classTypeInfo.getClassName());
        } else if (isWrap) {
            sb.append(toJavaWrapString(type));
        } else {
            sb.append(toJavaString(type));
        }
        List<ClassTypeInfo> typeArguments = classTypeInfo.getTypeArguments();
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

    public String toJavaTypeString(ClassTypeInfo classTypeInfo, boolean isWrap, boolean isArrayList) {
        return toJavaTypeString(classTypeInfo, isWrap, isArrayList, true);
    }

    private static String toJavaWrapString(ClassTypeInfo.TypeEnum type) {
        return TYPE_WRAP_MAP.get(type).getSimpleName();
    }

    private static final ImmutableMap<ClassTypeInfo.TypeEnum, Class<?>> TYPE_WRAP_MAP
            = ImmutableMap.<ClassTypeInfo.TypeEnum, Class<?>>builder()
            .put(ClassTypeInfo.TypeEnum.VOID, Void.class)
            .put(ClassTypeInfo.TypeEnum.BOOLEAN, Boolean.class)
            .put(ClassTypeInfo.TypeEnum.BYTE, Byte.class)
            .put(ClassTypeInfo.TypeEnum.SHORT, Short.class)
            .put(ClassTypeInfo.TypeEnum.INT, Integer.class)
            .put(ClassTypeInfo.TypeEnum.LONG, Long.class)
            .put(ClassTypeInfo.TypeEnum.FLOAT, Float.class)
            .put(ClassTypeInfo.TypeEnum.DOUBLE, Double.class)
            .put(ClassTypeInfo.TypeEnum.DATE, Date.class)
            .put(ClassTypeInfo.TypeEnum.LocalDateTime, LocalDateTime.class)
            .put(ClassTypeInfo.TypeEnum.STRING, String.class)
            .put(ClassTypeInfo.TypeEnum.OBJECTID, ObjectId.class)
            .build();

    private static String toJavaString(ClassTypeInfo.TypeEnum type) {
        return TYPE_MAP.get(type).getSimpleName();
    }

    private static final ImmutableMap<ClassTypeInfo.TypeEnum, Class<?>> TYPE_MAP
            = ImmutableMap.<ClassTypeInfo.TypeEnum, Class<?>>builder()
            .put(ClassTypeInfo.TypeEnum.VOID, void.class)
            .put(ClassTypeInfo.TypeEnum.BOOLEAN, boolean.class)
            .put(ClassTypeInfo.TypeEnum.BYTE, byte.class)
            .put(ClassTypeInfo.TypeEnum.SHORT, short.class)
            .put(ClassTypeInfo.TypeEnum.INT, int.class)
            .put(ClassTypeInfo.TypeEnum.LONG, long.class)
            .put(ClassTypeInfo.TypeEnum.FLOAT, float.class)
            .put(ClassTypeInfo.TypeEnum.DOUBLE, double.class)
            .put(ClassTypeInfo.TypeEnum.DATE, Date.class)
            .put(ClassTypeInfo.TypeEnum.LocalDateTime, LocalDateTime.class)
            .put(ClassTypeInfo.TypeEnum.STRING, String.class)
            .put(ClassTypeInfo.TypeEnum.OBJECTID, ObjectId.class)
            .build();
}
