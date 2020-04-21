package org.rxjava.apikit.tool.wrapper;

import com.google.common.collect.ImmutableMap;
import org.rxjava.apikit.tool.generator.Context;
import org.rxjava.apikit.tool.info.CommonClassInfo;
import org.rxjava.apikit.tool.info.ClassTypeInfo;

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
        ClassTypeInfo.Type type = typeInfo.getType();
        if (typeInfo.isCollection()) {
            ClassTypeInfo typeInfoArg = typeInfo.getTypeArguments().get(0);
            return toTypeString(typeInfoArg, true);
        } else if (type == ClassTypeInfo.Type.OTHER) {
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

    public String toJavaScriptString(ClassTypeInfo.Type type) {
        return TYPE_MAP.get(type);
    }

    private static final ImmutableMap<ClassTypeInfo.Type, String> TYPE_MAP
            = ImmutableMap.<ClassTypeInfo.Type, String>builder()
            .put(ClassTypeInfo.Type.VOID, "void")
            .put(ClassTypeInfo.Type.BOOLEAN, "boolean")
            .put(ClassTypeInfo.Type.BYTE, "number")
            .put(ClassTypeInfo.Type.SHORT, "number")
            .put(ClassTypeInfo.Type.INT, "number")
            .put(ClassTypeInfo.Type.LONG, "number")
            .put(ClassTypeInfo.Type.FLOAT, "number")
            .put(ClassTypeInfo.Type.DOUBLE, "number")
            .put(ClassTypeInfo.Type.DATE, "Date")
            .put(ClassTypeInfo.Type.STRING, "string")
            .build();
}
