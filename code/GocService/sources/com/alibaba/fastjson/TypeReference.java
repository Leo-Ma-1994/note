package com.alibaba.fastjson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class TypeReference<T> {
    public static final Type LIST_STRING = new TypeReference<List<String>>() {
        /* class com.alibaba.fastjson.TypeReference.AnonymousClass1 */
    }.getType();
    private final Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    protected TypeReference() {
    }

    public Type getType() {
        return this.type;
    }
}
