package org.rxjava.apikit.tool.info;

import com.google.common.collect.ImmutableMap;
import lombok.Data;
import org.apache.commons.lang3.ClassUtils;
import org.bson.types.ObjectId;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author happy
 * 类类型信息
 */
@Data
public class ClassTypeInfo implements Cloneable {
    /**
     * 类型
     */
    private TypeEnum type;
    /**
     * 包名
     */
    private String packageName;
    /**
     * 类名
     */
    private String className;
    /**
     * 参数类型
     */
    private List<ClassTypeInfo> typeArguments = new ArrayList<>();
    /**
     * 是否数组
     */
    private boolean array;
    /**
     * 是否包内，也就是需要进行包转换
     */
    private boolean inside = false;
    /**
     * 是否泛型变量
     */
    private boolean generic = false;
    /**
     * 是否枚举类
     */
    private boolean enumClass = false;

    /**
     * 是否对象
     */
    public boolean isObject() {
        return isOtherType() && getFullName().equals(Object.class.getName());
    }

    /**
     * 是否ObjectId
     */
    public boolean isObjectId() {
        return !isOtherType() || !getFullName().equals(ObjectId.class.getName());
    }

    public ClassTypeInfo() {
    }

    public ClassTypeInfo(TypeEnum type, String packageName, String className, boolean array, List<ClassTypeInfo> typeArguments, boolean inside, boolean generic) {
        this.type = type;
        this.packageName = packageName;
        this.className = className;
        this.array = array;
        this.typeArguments = typeArguments;
        this.inside = inside;
        this.generic = generic;
    }

    public String getFullName() {
        if (packageName == null) {
            return className;
        }
        return packageName + "." + className;
    }

    public List<ClassTypeInfo> getTypeArguments() {
        return typeArguments;
    }

    public static ClassTypeInfo form(Type type) {
        if (type instanceof Class) {
            //raw type：原始类型，对应Class,这里的Class不仅仅指平常所指的类，还包括数组、接口、注解、枚举等结构。
            //primitive types：基本类型，仍然对应Class
            Class<?> cls = (Class<?>) type;
            if (cls.isEnum()) {
                //枚举类型
                ClassTypeInfo classTypeInfo = new ClassTypeInfo();
                classTypeInfo.setType(TypeEnum.OTHER);
                classTypeInfo.setPackageName(cls.getPackage().getName());
                classTypeInfo.setClassName(cls.getSimpleName());
                classTypeInfo.setArray(false);
                classTypeInfo.setTypeArguments(new ArrayList<>());
                classTypeInfo.setInside(false);
                classTypeInfo.setGeneric(false);
                classTypeInfo.setEnumClass(true);
                return classTypeInfo;
            } else if (ClassUtils.isPrimitiveOrWrapper(cls)) {
                //原始类型（int，boolean等）或包装类型（String，Integer等）
                return ClassTypeInfo.formBaseType(cls);
            } else if (cls.isArray()) {
                //若是数组，则分析数组中的组件类型
                ClassTypeInfo typeInfo = form(cls.getComponentType());
                typeInfo.setArray(true);
                return typeInfo;
            } else {
                TypeEnum t = TypeEnum.form(cls);
                if (!t.isBaseType()) {
                    return new ClassTypeInfo(t, cls.getPackage().getName(), cls.getSimpleName(), false, new ArrayList<>(), false, false);
                } else {
                    return ClassTypeInfo.formBaseType(cls);
                }
            }
        } else if (type instanceof ParameterizedType) {
            //泛型分析
            //ParameterizedType：即常说的泛型，如：List<T>、Map<Integer, String>、List<? extends Number>。
            ParameterizedType paramType = (ParameterizedType) type;
            Class<?> rawType = (Class<?>) paramType.getRawType();

            ClassTypeInfo classTypeInfo = form(rawType);
            Type[] arguments = paramType.getActualTypeArguments();
            for (Type typeArgument : arguments) {
                ClassTypeInfo typeArgumentInfo = form(typeArgument);
                classTypeInfo.addArguments(typeArgumentInfo);
            }
            return classTypeInfo;
        } else if (type instanceof TypeVariable) {
            //TypeVariable:类型变量，如参数化类型中的E、K等类型变量，表示泛指任何类。
            TypeVariable<?> typeVar = (TypeVariable<?>) type;
            return ClassTypeInfo.formGeneric(typeVar.getName(), false);
        }
        throw new RuntimeException("暂不支持的类型，分析失败:" + type);
    }

    public boolean isOtherType() {
        return type == TypeEnum.OTHER;
    }

    public boolean isCollection() {
        try {
            String fullName = getFullName();
            if (fullName != null) {
                Class<?> cls = Class.forName(fullName);
                return Collection.class.isAssignableFrom(cls);
            }
            return false;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public void addArguments(ClassTypeInfo typeInfo) {
        typeArguments.add(typeInfo);
    }

    public static ClassTypeInfo formGeneric(String name, boolean isArray) {
        ClassTypeInfo typeInfo = new ClassTypeInfo();
        typeInfo.type = TypeEnum.OTHER;
        typeInfo.array = isArray;
        typeInfo.inside = false;
        typeInfo.generic = true;
        typeInfo.className = name;
        return typeInfo;
    }

    /**
     * 基本类型
     */
    public static ClassTypeInfo formBaseType(Class<?> cls) {
        ClassTypeInfo typeInfo = new ClassTypeInfo();
        typeInfo.type = TypeEnum.form(cls);

        if (!typeInfo.type.isBaseType()) {
            throw new RuntimeException("错误的类型,不是原始类型或原始包装类型:" + cls.getName());
        }
        return typeInfo;
    }

    public Class<?> toClass() throws ClassNotFoundException {
        if (type.isBaseType()) {
            return type.toClass();
        } else {
            return Class.forName(getFullName());
        }
    }

    @Override
    public ClassTypeInfo clone() {
        try {
            return (ClassTypeInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setArray(boolean array) {
        this.array = array;
    }

    public boolean isBytes() {
        return isArray() && type == TypeEnum.BYTE;
    }

    public boolean isString() {
        return type == TypeEnum.STRING;
    }

    /**
     * 类型枚举值
     */
    public enum TypeEnum {
        /**
         * 枚举值
         */
        VOID, BOOLEAN, BYTE, SHORT, INT, LONG, FLOAT,
        DOUBLE, STRING, DATE,
        OBJECTID,
        OTHER;

        private static final ImmutableMap<Class<?>, TypeEnum> TYPE_CLASS_MAP = ImmutableMap.<Class<?>, TypeEnum>builder()
                //基本类型
                .put(void.class, VOID)
                .put(boolean.class, BOOLEAN)
                .put(byte.class, BYTE)
                .put(short.class, SHORT)
                .put(int.class, INT)
                .put(long.class, LONG)
                .put(float.class, FLOAT)
                .put(double.class, DOUBLE)
                .put(char.class, STRING)
                //基本包装类型
                .put(Void.class, VOID)
                .put(Boolean.class, BOOLEAN)
                .put(Byte.class, BYTE)
                .put(Short.class, SHORT)
                .put(Integer.class, INT)
                .put(Long.class, LONG)
                .put(Float.class, FLOAT)
                .put(Double.class, DOUBLE)
                //系统包装类型
                .put(String.class, STRING)
                .put(Character.class, STRING)
                .put(Date.class, DATE)
                .put(ObjectId.class, OBJECTID)
                .put(Instant.class, DATE)
                .put(LocalDateTime.class, DATE)
                .put(LocalDate.class, DATE)
                .put(LocalTime.class, DATE)
                .build();

        private static final ImmutableMap<TypeEnum, Class<?>> CLASS_MAP = ImmutableMap.<TypeEnum, Class<?>>builder()
                .put(VOID, Void.class)
                .put(BOOLEAN, Boolean.class)
                .put(BYTE, Byte.class)
                .put(SHORT, Short.class)
                .put(INT, Integer.class)
                .put(LONG, Long.class)
                .put(FLOAT, Float.class)
                .put(DOUBLE, Double.class)
                .put(STRING, String.class)
                .put(DATE, Date.class)
                .put(OBJECTID, ObjectId.class)
                .build();

        public boolean isHasNull() {
            return this == STRING || this == DATE ||
                    this == OTHER;
        }

        public boolean isBaseType() {
            return this != OTHER;
        }

        public Class<?> toClass() {
            return CLASS_MAP.get(this);
        }

        public static TypeEnum form(Class<?> cls) {
            TypeEnum type = TYPE_CLASS_MAP.get(cls);
            if (type == null) {
                return OTHER;
            }
            return type;
        }
    }
}
