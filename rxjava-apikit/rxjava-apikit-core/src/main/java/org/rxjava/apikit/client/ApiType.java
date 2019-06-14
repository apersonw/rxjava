package org.rxjava.apikit.client;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author happy
 */
public class ApiType implements ParameterizedType {
    private final Type[] types;
    private final Type raw;

    ApiType(Type raw, Type... types) {
        this.raw = raw;
        this.types = types;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return this.types;
    }

    @Override
    public Type getRawType() {
        return this.raw;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }
}