package org.rxjava.apikit.tool.wrapper;

import org.apache.commons.lang3.StringUtils;
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

public class ApidocParamClassWrapper extends JavaScriptWrapper<ParamClassInfo> {
    public ApidocParamClassWrapper(Context context, ParamClassInfo paramClassInfo, String rootPackage) {
        super(context, paramClassInfo, rootPackage);
    }

    private Flux<PropertyInfo> getUpper() {
        return Mono
                .justOrEmpty(classInfo.getSuperType())
                .map(ClassTypeInfo::getFullName)
                .filter(fullName -> context.getParamWrapper(fullName) != null)
                .map(fullName -> context.getParamWrapper(fullName))
                .flatMapMany(w -> {
                    ClassTypeInfo superType = w.getClassInfo().getSuperType();
                    Flux<PropertyInfo> flux = Flux.fromIterable(w.getClassInfo().getProperties());
                    if (superType != null) {
                        flux.mergeWith(getUpper());
                    }
                    return flux;
                });
    }

    public List<PropertyInfo> getProperties() {
        return Flux
                .fromIterable(classInfo.getProperties())
                .mergeWith(getUpper())
                .distinct(FieldInfo::getFieldName)
                .collectList()
                .block();
    }

    public String getImports() {
        StringBuilder sb = new StringBuilder();
        Flux
                .fromIterable(classInfo.getProperties())
                .mergeWith(getUpper())
                .distinct(FieldInfo::getFieldName)
                .map(FieldInfo::getClassTypeInfo)
                .flatMapIterable(type -> {
                    List<ClassTypeInfo> types = new ArrayList<>();
                    findTypes(type, types);
                    if (classInfo.getSuperType() != null) {
                        findTypes(classInfo.getSuperType(), types);
                    }
                    return types;
                })
                .filter(typeInfo -> typeInfo.getType().equals(ClassTypeInfo.TypeEnum.OTHER))
                .filter(typeInfo -> !typeInfo.isCollection())
                .filter(typeInfo -> !typeInfo.isGeneric())
                .map(ClassTypeInfo::getFullName)
                .distinct()
                .sort(Comparator.naturalOrder())
                .filter(fullName -> context.getParamWrapper(fullName) != null)
                .map(fullName -> context.getParamWrapper(fullName))
                .doOnNext(r -> {
                    String distPackage = getDistPackage();
                    String proTypeName = r.getDistClassName();

                    if (r.getFullDistPackage().equals(distPackage)) {
                        sb.append("import ")
                                .append(proTypeName)
                                .append(" from './").append(proTypeName).append("'\n");
                    } else {
                        int level = distPackage.split("\\.").length;
                        sb.append("import ")
                                .append(proTypeName)
                                .append(" from '")
                                .append(StringUtils.repeat("../", level))
                                .append(r.getDistPackage())
                                .append("/")
                                .append(proTypeName)
                                .append("'\n");
                    }
                })
                .collectList()
                .block();

        return sb.toString();
    }

    public String toObjectArgs() {
        StringBuilder sb = new StringBuilder();
        List<PropertyInfo> properties = classInfo.getProperties();
        for (int i = 0; i < properties.size(); i++) {
            PropertyInfo info = properties.get(i);
            if (i > 0) {
                sb.append(',');
            }
            sb.append(info.getFieldName());
        }
        return sb.toString();
    }

    public String toObjectArgsType() {
        StringBuilder sb = new StringBuilder();
        List<PropertyInfo> properties = classInfo.getProperties();
        for (int i = 0; i < properties.size(); i++) {
            PropertyInfo info = properties.get(i);
            if (i > 0) {
                sb.append(',');
            }
            sb.append(info.getFieldName());
            sb.append(":");
            sb.append(toTypeString(info.getClassTypeInfo()));
        }
        return sb.toString();
    }
}
