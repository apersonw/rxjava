package org.rxjava.apikit.tool.wrapper;

import org.apache.commons.collections4.CollectionUtils;
import org.rxjava.apikit.tool.generator.Context;
import org.rxjava.apikit.tool.info.FieldInfo;
import org.rxjava.apikit.tool.info.ParamClassInfo;
import org.rxjava.apikit.tool.info.PropertyInfo;
import org.rxjava.apikit.tool.info.ClassTypeInfo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author happy
 * Java参数类信息包装器
 */
public class JavaParamClassWrapper extends JavaWrapper<ParamClassInfo> {
    public JavaParamClassWrapper(Context context, ParamClassInfo paramClassInfo, String rootPackage) {
        super(context, paramClassInfo, rootPackage);
    }

    private Flux<PropertyInfo> getSupper() {
        return Mono
                .justOrEmpty(classInfo.getSuperType())
                .map(ClassTypeInfo::getFullName)
                .filter(fullName -> context.getMessageWrapper(fullName) != null)
                .map(fullName -> context.getMessageWrapper(fullName))
                .flatMapMany(w -> {
                    ClassTypeInfo superType = w.getClassInfo().getSuperType();
                    Flux<PropertyInfo> flux = Flux.fromIterable(w.getClassInfo().getProperties());
                    if (superType != null) {
                        flux.mergeWith(getSupper());
                    }
                    return flux;
                });
    }

    /**
     * 导入相关依赖类
     */
    public String imports() {
        StringBuilder sb = new StringBuilder();

        Flux
                .fromIterable(classInfo.getProperties())
                .mergeWith(getSupper())
                .distinct(FieldInfo::getFieldName)
                .map(FieldInfo::getTypeInfo)
                .flatMapIterable(type -> {
                    List<ClassTypeInfo> types = new ArrayList<>();
                    findTypes(type, types);
                    return types;
                })
                .filter(typeInfo -> typeInfo.getType().equals(ClassTypeInfo.Type.OTHER))
                .filter(typeInfo -> !typeInfo.isCollection())
                .filter(typeInfo -> !typeInfo.isGeneric())
                .map(ClassTypeInfo::getFullName)
                .distinct()
                .sort(Comparator.naturalOrder())
                .filter(fullName -> context.getMessageWrapper(fullName) != null)
                .map(fullName -> context.getMessageWrapper(fullName))
                .filter(w -> !w.getDistPackage().equals(getDistPackage()))
                .doOnNext(r -> sb.append("import ").append(r.getDistPackage()).append(".").append(r.getDistClassName()).append(";\n"))
                .collectList()
                .block();

        return sb.toString();
    }

    public String typeParameters() {
        List<String> typeParameters = classInfo.getTypeParameters();

        if (CollectionUtils.isNotEmpty(typeParameters)) {
            StringBuilder sb = new StringBuilder("<");
            for (String typeParameter : typeParameters) {
                if (sb.length() > 1) {
                    sb.append(",");
                }
                sb.append(typeParameter);
            }
            sb.append(">");
            return sb.toString();
        } else {
            return "";
        }
    }

    public String superInfo() {
        ClassTypeInfo superType = classInfo.getSuperType();

        if (superType != null) {
            return "extends " + toJavaTypeString(superType, false, true);
        } else {
            return "";
        }
    }

    public String encodeCode(String start, String parentName) {
        if (classInfo.hasGenerics()) {
            return "throw new RuntimeException(\"不支持泛型\");";
        }
        StringBuilder sb = new StringBuilder();
        for (PropertyInfo attr : classInfo.getProperties()) {
            sb.append('\n');
            ClassTypeInfo sourceTypeInfo = attr.getTypeInfo();
            ClassTypeInfo typeInfo = sourceTypeInfo;
            String name = attr.getFieldName();
            if (typeInfo.isCollection()) {
                if (CollectionUtils.isEmpty(typeInfo.getTypeArguments())) {
                    throw new RuntimeException("List 类型参数不明确:" + attr.getTypeInfo());
                }
                ClassTypeInfo childTypeInfo = typeInfo.getTypeArguments().get(0);
                typeInfo = childTypeInfo.clone();
                typeInfo.setArray(true);
            }
            if (typeInfo.isArray()) {
                if (sourceTypeInfo.isBytes()) {
                    sb.append(start).append(" if (").append(name).append(" != null && (").append(name).append(".length > 0)) {\n");
                    sb.append("$list.add(new SimpleImmutableEntry<>(" + parentName + " + \"")
                            .append(name).append("\", ")
                            .append(name)
                            .append("));\n");
                    sb.append(start).append("}\n");
                } else if (typeInfo.isOtherType()) {
                    sb.append(start).append(" if (").append(name).append(" != null && (!").append(name).append(".isEmpty())) {\n");
                    sb.append(start).append("for (int i = 0; i < ").append(name).append(".size(); i++) {\n");
                    sb.append(start).append("    ").append(name).append(".get(i).encode(").append(parentName).append(" + \"")
                            .append(name).append("\" + \"[\" + i + \"].\", $list);\n");
                    sb.append(start).append("    }\n");
                    sb.append(start).append("}\n");
                } else if (typeInfo.isString()) {
                    sb.append(start).append(" if (").append(name).append(" != null && (!").append(name).append(".isEmpty())) {\n");
                    sb.append(start).append("for (int i = 0; i < ").append(name).append(".size(); i++) {\n");
                    sb.append("$list.add(new SimpleImmutableEntry<>(" + parentName + " + \"")
                            .append(name).append("\", ")
                            .append(name)
                            .append(".get(i)));\n");
                    sb.append(start).append("    }\n");
                    sb.append(start).append("}\n");
                } else {
                    sb.append(start).append(" if (").append(name).append(" != null && (!").append(name).append(".isEmpty())) {\n");
                    sb.append("$list.add(new SimpleImmutableEntry<>(" + parentName + " + \"")
                            .append(name).append("\", ")
                            .append(name)
                            .append("));\n");
                    sb.append(start).append("}\n");
                }
            } else if (typeInfo.isOtherType()) {
                sb.append(start).append(" if (").append(name).append(" != null) {\n");
                sb.append(start).append("    ").append(name).append(".encode(").append(parentName).append(" + \"").append(name).append(".\", $list);");
                sb.append(start).append("}\n");
            } else if (typeInfo.getType().isHasNull()) {
                sb.append(start).append("if (").append(name).append(" != null) {\n");
                getEncodeCodeItemBase(start, sb, name, parentName);
                sb.append(start).append("}\n");
            } else {
                getEncodeCodeItemBase(start, sb, name, parentName);
            }
        }
        sb.append(start).append("return $list;");
        return sb.toString();
    }

    /**
     * 基本类型
     */
    private void getEncodeCodeItemBase(String start, StringBuilder sb, String name, String parentName) {
        sb.append(start)
                .append("    ")
                .append("$list.add(new SimpleImmutableEntry<>(")
                .append(parentName).append(" + \"")
                .append(name).append("\",")
                .append(name)
                .append("));\n");
    }
}
