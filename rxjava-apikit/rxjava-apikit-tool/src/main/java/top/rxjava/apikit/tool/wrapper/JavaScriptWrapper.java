package top.rxjava.apikit.tool.wrapper;

import com.google.common.collect.ImmutableMap;
import top.rxjava.apikit.tool.generator.Context;
import top.rxjava.apikit.tool.info.CommonClassInfo;
import top.rxjava.apikit.tool.info.ClassTypeInfo;

/**
 * @author happy 2019-05-09 23:04
 */
public class JavaScriptWrapper<T extends CommonClassInfo> extends BuilderWrapper<T> {

    public JavaScriptWrapper(Context context, T classInfo, String rootPackage) {
        super(context, classInfo, rootPackage);
    }

    public String toTypeString(ClassTypeInfo typeInfo) {
        return toTypeString(typeInfo, false);
    }

    public String toTypeString(ClassTypeInfo typeInfo, boolean isArray) {
        StringBuilder sb = new StringBuilder();
        ClassTypeInfo.TypeEnum type = typeInfo.getType();
        if (typeInfo.isCollection()) {
            ClassTypeInfo typeInfoArg = typeInfo.getTypeArguments().get(0);
            return toTypeString(typeInfoArg, true);
        } else if (type == ClassTypeInfo.TypeEnum.OTHER) {
            if (typeInfo.isGeneric()) {
                sb.append("Object");
            } else {
                sb.append(typeInfo.getClassName());
            }
        } else {
            sb.append(toJavaScriptString(type));
        }
        if (typeInfo.isArray() || isArray) {
            sb.append("[]");
        }
        return sb.toString();
    }

    public String toJavaScriptString(ClassTypeInfo.TypeEnum type) {
        return TYPE_MAP.get(type);
    }

    private static final ImmutableMap<ClassTypeInfo.TypeEnum, String> TYPE_MAP
            = ImmutableMap.<ClassTypeInfo.TypeEnum, String>builder()
            .put(ClassTypeInfo.TypeEnum.VOID, "void")
            .put(ClassTypeInfo.TypeEnum.BOOLEAN, "boolean")
            .put(ClassTypeInfo.TypeEnum.BYTE, "number")
            .put(ClassTypeInfo.TypeEnum.SHORT, "number")
            .put(ClassTypeInfo.TypeEnum.INT, "number")
            .put(ClassTypeInfo.TypeEnum.LONG, "number")
            .put(ClassTypeInfo.TypeEnum.FLOAT, "number")
            .put(ClassTypeInfo.TypeEnum.DOUBLE, "number")
            .put(ClassTypeInfo.TypeEnum.DATE, "Date")
            .put(ClassTypeInfo.TypeEnum.STRING, "string")
            .build();
}
