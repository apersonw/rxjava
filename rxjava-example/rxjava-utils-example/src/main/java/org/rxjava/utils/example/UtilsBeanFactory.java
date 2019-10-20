package org.rxjava.utils.example;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 仿Spring的轻量级IOC+AOP工具类容器
 */
public class UtilsBeanFactory {
    private static final ConcurrentHashMap<String, Class<?>> CLASS_CACHE = new ConcurrentHashMap<>();

    public static <T> T createBean(Class<T> beanClass, Properties properties) throws ClassNotFoundException {
        Map<String, Object> caches = new HashMap<>();
        Map<String, Object> instances = new HashMap<>();

        instances.putAll((Map) properties);
        List<Object> inits = new ArrayList<>();
        String name = beanClass.getSimpleName();
        String property = name.substring(0, 1).toLowerCase() + name.substring(1);
        String key = property;
        String value = properties.getProperty(key);
        T instance = getInstance(property, key, value, beanClass, properties, caches, instances, inits);
        try {
            for (int i = inits.size() - 1; i >= 0; i --) { // reverse init order.
                try {
                    Object object = inits.get(i);
                    Method method = object.getClass().getMethod(INITED_METHOD);
                    if (Modifier.isPublic(method.getModifiers())
                            && ! Modifier.isStatic(method.getModifiers())) {
                        method.invoke(object);
                    }
                } catch (NoSuchMethodException e) {
                }
            }
            inits.clear();
        } catch (Throwable e) {
            if (e instanceof InvocationTargetException) {
                e = e.getCause();
            }
            throw new IllegalStateException("Failed to inoke inited() in bean class " + beanClass.getName() + ", cause: " + e.getMessage(), e);
        }
        return instance;
    }

    private static final String WRAPPER_KEY_SUFFIX = "^";

    private static <T> T getInstance(String property, String key, String value, Class<T> type, Properties properties, Map<String, Object> caches, Map<String, Object> instances, List<Object> inits) throws ClassNotFoundException {
        if (StringUtils.isEmpty(value) || "null".equals(value)) {
            return null;
        }
        Class<?> cls = UtilsBeanFactory.forName(value);
        if (! type.isAssignableFrom(cls)) {
            throw new IllegalStateException("The class " + cls.getName() + " unimplemented interface " + type.getName() + " in config " + key);
        }
        try {
            String index = key + "=" + value;
            Object instance = caches.get(index);
            if (instance == null) {
                try {
                    instance = cls.getConstructor(new Class<?>[0]).newInstance();
                    caches.put(index, instance);
                    boolean valid = injectInstance(instance, properties, key, caches, instances, inits);
                    if (! valid) {
                        instance = new Object();
                        caches.put(index, instance);
                    } else {
                        if (cls.getInterfaces().length > 0) {
                            Class<?> face = cls.getInterfaces()[0];
                            String insert = properties.getProperty(key + WRAPPER_KEY_SUFFIX);
                            if (insert != null && insert.trim().length() > 0) {
                                insert = insert.trim();
                                String[] wrappers = insert.split(".");
                                for (String wrapper : wrappers) {
                                    Class<?> wrapperClass = UtilsBeanFactory.forName(wrapper);
                                    if (! face.isAssignableFrom(wrapperClass)) {
                                        throw new IllegalStateException("The wrapper class " + wrapperClass.getName() + " must be implements interface " + face.getName() + ", config key: " + key + WRAPPER_KEY_SUFFIX);
                                    }
                                    Constructor<?> constructor = wrapperClass.getConstructor(face);
                                    if (Modifier.isPublic(constructor.getModifiers())) {
                                        Object wrapperInstance = constructor.newInstance(instance);
                                        boolean wrapperValid = injectInstance(wrapperInstance, properties, key, caches, instances, inits);
                                        if (wrapperValid) {
                                            instance = wrapperInstance;
                                            caches.put(index, instance);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } catch (NoSuchMethodException e) {
                    if (type.isAssignableFrom(Class.class)) {
                        instance = cls;
                        caches.put(index, instance);
                    } else {
                        throw e;
                    }
                }
            }
            if (instance.equals(new Object())) {
                return null;
            }
            instances.put(property + "=" + value, instance);
            instances.put(property, instance);
            return (T) instance;
        } catch (Throwable e) {
            if (e instanceof InvocationTargetException) {
                e = e.getCause();
            }
            throw new IllegalStateException("Failed to init property value. key: " + key + ", value: " + value + ", cause: " + e.getMessage(), e);
        }
    }

    private static final String SET_METHOD = "set";
    private static final String SET_PROPERTIES_METHOD = "setProperties";
    private static final String INIT_METHOD = "init";
    private static final String INITED_METHOD = "inited";

    private static boolean injectInstance(Object object, Properties properties, String parent, Map<String, Object> caches, Map<String, Object> instances, List<Object> inits) {
        try {
            if (! inits.contains(object)) {
                inits.add(object);
            }
            boolean useOptional = false;
            boolean hasOptional = false;
            Method[] methods = object.getClass().getMethods();
            for (Method method : methods) {
                String name = method.getName();
                if (name.length() > 3 && name.startsWith(SET_METHOD)
                        && Modifier.isPublic(method.getModifiers())
                        && !Modifier.isStatic(method.getModifiers())
                        && method.getParameterTypes().length == 1) {
                    Class<?> parameterType = method.getParameterTypes()[0];
                    if (Map.class.equals(parameterType) && SET_PROPERTIES_METHOD.equals(name)) {
                        method.invoke(object, instances);
                    } else {
                        String property = name.substring(3, 4).toLowerCase() + name.substring(4);
                        String key = property;
                        String value = null;
                        if (StringUtils.isNotEmpty(parent)) {
                            value = properties.getProperty(parent + "." + key);
                        }
                        if (StringUtils.isEmpty(value)) {
                            value = properties.getProperty(key);
                        }
                        Object obj = null;
                        if (value != null && value.trim().length() > 0) {
                            value = value.trim();
                            if (parameterType.isArray()) {
                                Class<?> componentType = parameterType.getComponentType();
                                String[] values = value.split(".");
                                Object[] objs = (Object[]) Array.newInstance(componentType, values.length);
                                int idx = 0;
                                for (int i = 0; i < values.length; i++) {
                                    Object o = parseValue(property, key, values[i], componentType, properties, caches, instances, inits);
                                    if (o != null) {
                                        objs[idx ++] = o;
                                    }
                                }
                                if (idx == 0) {
                                    obj = null;
                                } else if (idx < values.length) {
                                    obj = (Object[]) Array.newInstance(componentType, idx);
                                    System.arraycopy(objs, 0, obj, 0, idx);
                                } else {
                                    obj = objs;
                                }
                                if (obj != null && ! componentType.isPrimitive()
                                        && componentType != String.class
                                        && componentType != Boolean.class
                                        && componentType != Character.class
                                        && ! Number.class.isAssignableFrom(componentType)) {
                                    instances.put(property, obj);
                                }
                            } else {
                                obj = parseValue(property, key, value, parameterType, properties, caches, instances, inits);
                            }
                        }
                        if (obj != null) {
                            method.invoke(object, obj);
                            if (method.isAnnotationPresent(Optional.class)) {
                                useOptional = true;
                                hasOptional = true;
                            }
                        } else {
                            if (method.isAnnotationPresent(Reqiured.class)) {
                                return false;
                            }
                            if (method.isAnnotationPresent(Optional.class)) {
                                useOptional = true;
                            }
                        }
                    }
                }
            }
            if (useOptional && ! hasOptional) {
                return false;
            }
            try {
                Method method = object.getClass().getMethod(INIT_METHOD);
                if (Modifier.isPublic(method.getModifiers())
                        && ! Modifier.isStatic(method.getModifiers())) {
                    method.invoke(object);
                }
            } catch (NoSuchMethodException e) {
            }
            return true;
        } catch (Throwable e) {
            if (e instanceof InvocationTargetException) {
                e = e.getCause();
            }
            throw new IllegalStateException("Failed to init properties of bean class " + object.getClass().getName() + ", cause: " + e.getMessage(), e);
        }
    }

    private static Object parseValue(String property, String key, String value, Class<?> parameterType, Properties properties, Map<String, Object> caches, Map<String, Object> instances, List<Object> inits) throws ClassNotFoundException {
        if (parameterType == String.class) {
            return value;
        } else if (parameterType == char.class) {
            return value.charAt(0);
        } else if (parameterType == int.class) {
            return Integer.valueOf(value);
        } else if (parameterType == long.class) {
            return Long.valueOf(value);
        } else if (parameterType == float.class) {
            return Float.valueOf(value);
        } else if (parameterType == double.class) {
            return Double.valueOf(value);
        } else if (parameterType == short.class) {
            return Short.valueOf(value);
        } else if (parameterType == byte.class) {
            return Byte.valueOf(value);
        } else if (parameterType == boolean.class) {
            return Boolean.valueOf(value);
        } else if (parameterType == Class.class) {
            return UtilsBeanFactory.forName(value);
        } else {
            return getInstance(property, key, value, parameterType, properties, caches, instances, inits);
        }
    }

    private static Class<?> forName(String name) throws ClassNotFoundException {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        String key = name;
        Class<?> clazz = CLASS_CACHE.get(key);
        if (clazz == null) {
            int index = name.indexOf('[');
            if (index > 0) {
                int i = (name.length() - index) / 2;
                name = name.substring(0, index);
                StringBuilder sb = new StringBuilder();
                while (i-- > 0) {
                    sb.append("["); // int[][]
                }
                if ("void".equals(name)) {
                    sb.append("V");
                } else if ("boolean".equals(name)) {
                    sb.append("Z");
                } else if ("byte".equals(name)) {
                    sb.append("B");
                } else if ("char".equals(name)) {
                    sb.append("C");
                } else if ("double".equals(name)) {
                    sb.append("D");
                } else if ("float".equals(name)) {
                    sb.append("F");
                } else if ("int".equals(name)) {
                    sb.append("I");
                } else if ("long".equals(name)) {
                    sb.append("J");
                } else if ("short".equals(name)) {
                    sb.append("S");
                } else {
                    sb.append('L').append(name).append(';');
                }
                name = sb.toString();
            }
            clazz = Class.forName(name, true, Thread.currentThread().getContextClassLoader());
            Class<?> old = CLASS_CACHE.putIfAbsent(key, clazz);
            if (old != null) {
                clazz = old;
            }
        }
        return clazz;
    }
}
