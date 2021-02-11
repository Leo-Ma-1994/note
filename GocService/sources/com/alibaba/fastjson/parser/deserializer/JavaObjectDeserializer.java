package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

public class JavaObjectDeserializer implements ObjectDeserializer {
    public static final JavaObjectDeserializer instance = new JavaObjectDeserializer();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        if (!(type instanceof GenericArrayType)) {
            return (T) parser.parse(fieldName);
        }
        Type componentType = ((GenericArrayType) type).getGenericComponentType();
        if (componentType instanceof TypeVariable) {
            componentType = ((TypeVariable) componentType).getBounds()[0];
        }
        List<Object> list = new ArrayList<>();
        parser.parseArray(componentType, list);
        if (!(componentType instanceof Class)) {
            return (T) list.toArray();
        }
        Class<?> componentClass = (Class) componentType;
        if (componentClass == Boolean.TYPE) {
            return (T) TypeUtils.cast((Object) list, (Class<Object>) boolean[].class, parser.getConfig());
        }
        if (componentClass == Short.TYPE) {
            return (T) TypeUtils.cast((Object) list, (Class<Object>) short[].class, parser.getConfig());
        }
        if (componentClass == Integer.TYPE) {
            return (T) TypeUtils.cast((Object) list, (Class<Object>) int[].class, parser.getConfig());
        }
        if (componentClass == Long.TYPE) {
            return (T) TypeUtils.cast((Object) list, (Class<Object>) long[].class, parser.getConfig());
        }
        if (componentClass == Float.TYPE) {
            return (T) TypeUtils.cast((Object) list, (Class<Object>) float[].class, parser.getConfig());
        }
        if (componentClass == Double.TYPE) {
            return (T) TypeUtils.cast((Object) list, (Class<Object>) double[].class, parser.getConfig());
        }
        T t = (T) ((Object[]) Array.newInstance(componentClass, list.size()));
        list.toArray(t);
        return t;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }
}
