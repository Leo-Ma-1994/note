package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class ArrayDeserializer implements ObjectDeserializer {
    public static final ArrayDeserializer instance = new ArrayDeserializer();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Class componentClass;
        JSONLexer lexer = parser.getLexer();
        if (lexer.token() == 8) {
            lexer.nextToken(16);
            return null;
        } else if (lexer.token() == 4) {
            T t = (T) lexer.bytesValue();
            lexer.nextToken(16);
            return t;
        } else {
            if (type instanceof GenericArrayType) {
                Type componentType = ((GenericArrayType) type).getGenericComponentType();
                if (componentType instanceof TypeVariable) {
                    TypeVariable typeVar = (TypeVariable) componentType;
                    Type objType = parser.getContext().getType();
                    if (objType instanceof ParameterizedType) {
                        ParameterizedType objParamType = (ParameterizedType) objType;
                        Type objRawType = objParamType.getRawType();
                        Type actualType = null;
                        if (objRawType instanceof Class) {
                            TypeVariable[] objTypeParams = ((Class) objRawType).getTypeParameters();
                            for (int i = 0; i < objTypeParams.length; i++) {
                                if (objTypeParams[i].getName().equals(typeVar.getName())) {
                                    actualType = objParamType.getActualTypeArguments()[i];
                                }
                            }
                        }
                        if (actualType instanceof Class) {
                            componentClass = (Class) actualType;
                        } else {
                            componentClass = Object.class;
                        }
                    } else {
                        componentClass = Object.class;
                    }
                } else {
                    componentClass = (Class) componentType;
                }
            } else {
                componentClass = ((Class) type).getComponentType();
            }
            JSONArray array = new JSONArray();
            parser.parseArray(componentClass, array, fieldName);
            return (T) toObjectArray(parser, componentClass, array);
        }
    }

    private <T> T toObjectArray(DefaultJSONParser parser, Class<?> componentType, JSONArray array) {
        Object element;
        if (array == null) {
            return null;
        }
        int size = array.size();
        T t = (T) Array.newInstance(componentType, size);
        for (int i = 0; i < size; i++) {
            Object value = array.get(i);
            if (value == array) {
                Array.set(t, i, t);
            } else if (componentType.isArray()) {
                if (componentType.isInstance(value)) {
                    element = value;
                } else {
                    element = toObjectArray(parser, componentType, (JSONArray) value);
                }
                Array.set(t, i, element);
            } else {
                Object element2 = null;
                if (value instanceof JSONArray) {
                    boolean contains = false;
                    JSONArray valueArray = (JSONArray) value;
                    int valueArraySize = valueArray.size();
                    for (int y = 0; y < valueArraySize; y++) {
                        if (valueArray.get(y) == array) {
                            valueArray.set(i, t);
                            contains = true;
                        }
                    }
                    if (contains) {
                        element2 = valueArray.toArray();
                    }
                }
                if (element2 == null) {
                    element2 = TypeUtils.cast(value, (Class<Object>) componentType, parser.getConfig());
                }
                Array.set(t, i, element2);
            }
        }
        array.setRelatedArray(t);
        array.setComponentType(componentType);
        return t;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 14;
    }
}
