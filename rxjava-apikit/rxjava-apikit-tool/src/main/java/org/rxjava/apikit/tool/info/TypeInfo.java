package org.rxjava.apikit.tool.info;

import com.google.common.collect.ImmutableMap;
import lombok.Data;
import org.apache.commons.lang3.ClassUtils;

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
 * 类型信息
 */
@Data
public class TypeInfo implements Cloneable{

    /**
     * 类型
     */
    private Type type;
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
    private List<TypeInfo> typeArguments = new ArrayList<>();
    /**
     * 是否数组
     */
    private boolean array;
    /**
     * 是否包内，也就是需要进行包转换
     */
    private boolean inside = false;
    /**
     * 是否是泛型的变量类型
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

    public TypeInfo() {
    }

    public TypeInfo(Type type, String packageName, String className, boolean array, List<TypeInfo> typeArguments, boolean inside, boolean generic) {
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

    public List<TypeInfo> getTypeArguments() {
        return typeArguments;
    }

    public static TypeInfo form(java.lang.reflect.Type type) {

        if (type instanceof Class) {
            Class cls = (Class) type;
            //判断是否枚举类
            if (cls.isEnum()) {
                TypeInfo typeInfo = new TypeInfo();
                typeInfo.setType(Type.form(cls));
                typeInfo.setPackageName(cls.getPackage().getName());
                typeInfo.setClassName(cls.getSimpleName());
                typeInfo.setArray(false);
                typeInfo.setTypeArguments(new ArrayList<>());
                typeInfo.setInside(false);
                typeInfo.setGeneric(false);
                typeInfo.setEnumClass(true);
                return typeInfo;
            } else if (ClassUtils.isPrimitiveOrWrapper(cls)) {
                return TypeInfo.formBaseType(cls.getName(), false);
            } else if (cls.isArray()) {
                TypeInfo typeInfo = form(cls.getComponentType());
                typeInfo.setArray(true);
                return typeInfo;
            } else {
                Type t = Type.form(cls);
                if (!t.isBaseType()) {
                    return new TypeInfo(t, cls.getPackage().getName(), cls.getSimpleName(), false, new ArrayList<>(), false, false);
                } else {
                    return TypeInfo.formBaseType(cls.getName(), false);
                }
            }
        } else if (type instanceof java.lang.reflect.ParameterizedType) {
            java.lang.reflect.ParameterizedType paramType = (java.lang.reflect.ParameterizedType) type;
            Class rawType = (Class) paramType.getRawType();

            TypeInfo typeInfo = form(rawType);
            java.lang.reflect.Type[] arguments = paramType.getActualTypeArguments();
            for (java.lang.reflect.Type typeArgument : arguments) {
                TypeInfo typeArgumentInfo = form(typeArgument);
                typeInfo.addArguments(typeArgumentInfo);
            }
            return typeInfo;
        } else if (type instanceof TypeVariable) {
            TypeVariable typeVar = (TypeVariable) type;
            return TypeInfo.formGeneric(typeVar.getName(), false);
        }
        throw new RuntimeException("暂时不支持的类型，分析失败:" + type);
    }

    public boolean isOtherType() {
        return type == Type.OTHER;
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

    public void addArguments(TypeInfo typeInfo) {
        typeArguments.add(typeInfo);
    }

    public static TypeInfo formGeneric(String name, boolean isArray) {
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.type = Type.OTHER;
        typeInfo.array = isArray;
        typeInfo.inside = false;
        typeInfo.generic = true;
        typeInfo.className = name;
        return typeInfo;
    }

    public static TypeInfo formBaseType(String name, boolean isArray) {
        TypeInfo typeInfo = new TypeInfo();
        typeInfo.type = Type.form(name);
        typeInfo.array = isArray;
        typeInfo.inside = false;

        if (!typeInfo.type.isBaseType()) {
            throw new RuntimeException("错误的类型,不是原始类型或原始包装类型:" + name);
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
    public TypeInfo clone() {
        try {
            return (TypeInfo) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    public void setArray(boolean array) {
        this.array = array;
    }

    public boolean isBytes() {
        return isArray() && type == Type.BYTE;
    }

    public boolean isString() {
        return type == Type.STRING;
    }

    /**
     * 0. void *(只在api返回值)*
     * 1. boolean
     * 2. byte *(8位有符号整数)*
     * 3. short *(16位有符号整数)*
     * 4. int *(32位有符号整数)*
     * 5. long *(64位有符号整数)*
     * 6. float *(32位浮点数)*
     * 7. double *(64位浮点数)*
     * 8. String
     * 9. Date
     * 10. enum 枚举类型
     * 11. Message类型
     * <p>
     * Message 和其他非上面声明类型都不属于basic type
     */
    public enum Type {
        /**
         * 无
         */
        VOID, BOOLEAN, BYTE, SHORT, INT, LONG, FLOAT,
        DOUBLE, STRING, DATE,
        OTHER;

        private static final ImmutableMap<String, Type> TYPE_MAP = ImmutableMap.<String, Type>builder()
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

        private static final ImmutableMap<Class, Type> TYPE_CLASS_MAP = ImmutableMap.<Class, Type>builder()
                .put(void.class, VOID)
                .put(boolean.class, BOOLEAN)
                .put(byte.class, BYTE)
                .put(short.class, SHORT)
                .put(int.class, INT)
                .put(long.class, LONG)
                .put(float.class, FLOAT)
                .put(double.class, DOUBLE)
                .put(char.class, STRING)

                .put(Void.class, VOID)
                .put(Boolean.class, BOOLEAN)
                .put(Byte.class, BYTE)
                .put(Short.class, SHORT)
                .put(Integer.class, INT)
                .put(Long.class, LONG)
                .put(Float.class, FLOAT)
                .put(Double.class, DOUBLE)

                .put(String.class, STRING)
                .put(Character.class, STRING)
                .put(Date.class, DATE)
                .put(Instant.class, DATE)
                .put(LocalDateTime.class, DATE)
                .put(LocalDate.class, DATE)
                .put(LocalTime.class, DATE)
                .build();

        private static final ImmutableMap<Type, Class> CLASS_MAP = ImmutableMap.<Type, Class>builder()
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
                .build();

        public static boolean isHasNull(Type type) {
            return type == STRING || type == DATE ||
                    type == OTHER;
        }

        public boolean isHasNull() {
            return isHasNull(this);
        }

        public static boolean isBaseType(Type type) {
            return type != OTHER;
        }

        public boolean isBaseType() {
            return isBaseType(this);
        }

        public Class toClass() {
            return CLASS_MAP.get(this);
        }

        public String getPrimitiveName() {
            Class aClass = CLASS_MAP.get(this);
            if (aClass != null) {
                Class<?> primitive = ClassUtils.wrapperToPrimitive(aClass);
                return primitive == null ? aClass.getSimpleName() : primitive.getSimpleName();
            } else {
                return null;
            }
        }

        public static Type form(String name) {
            Type type = TYPE_MAP.get(name);
            if (type == null) {
                return OTHER;
            }
            return type;
        }

        public static Type form(Class cls) {
            Type type = TYPE_CLASS_MAP.get(cls);
            if (type == null) {
                return OTHER;
            }
            return type;
        }
    }
}
