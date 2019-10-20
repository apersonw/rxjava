package org.rxjava.apikit.tool.wrapper;

import com.google.common.collect.ImmutableMap;
import org.rxjava.apikit.tool.generator.Context;
import org.rxjava.apikit.tool.info.ClassInfo;
import org.rxjava.apikit.tool.info.TypeInfo;

/**
 * @author happy 2019-05-09 23:04
 */
public class JavaScriptWrapper<T extends ClassInfo> extends BuilderWrapper<T> {

    public JavaScriptWrapper(Context context, T classInfo, String rootPackage) {
        super(context, classInfo, rootPackage);
    }

    public String toTypeString(TypeInfo typeInfo) {
        return toTypeString(typeInfo, false);
    }

    public String toTypeString(TypeInfo typeInfo, boolean isArray) {
        StringBuilder sb = new StringBuilder();
        TypeInfo.Type type = typeInfo.getType();
        if (typeInfo.isCollection()) {
            TypeInfo typeInfoArg = typeInfo.getTypeArguments().get(0);
            return toTypeString(typeInfoArg, true);
        } else if (type == TypeInfo.Type.OTHER) {
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

    public String toJavaScriptString(TypeInfo.Type type) {
        return TYPE_MAP.get(type);
    }

    private static final ImmutableMap<TypeInfo.Type, String> TYPE_MAP
            = ImmutableMap.<TypeInfo.Type, String>builder()
            .put(TypeInfo.Type.VOID, "void")
            .put(TypeInfo.Type.BOOLEAN, "boolean")
            .put(TypeInfo.Type.BYTE, "number")
            .put(TypeInfo.Type.SHORT, "number")
            .put(TypeInfo.Type.INT, "number")
            .put(TypeInfo.Type.LONG, "number")
            .put(TypeInfo.Type.FLOAT, "number")
            .put(TypeInfo.Type.DOUBLE, "number")
            .put(TypeInfo.Type.DATE, "Date")
            .put(TypeInfo.Type.STRING, "string")
            .build();
}
