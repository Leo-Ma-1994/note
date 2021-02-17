package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class CollectionDeserializer implements ObjectDeserializer {
    public static final CollectionDeserializer instance = new CollectionDeserializer();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        Collection list;
        Type itemType;
        Type itemType2;
        if (parser.getLexer().token() == 8) {
            parser.getLexer().nextToken(16);
            return null;
        }
        Class<?> rawClass = getRawClass(type);
        if (rawClass == AbstractCollection.class) {
            list = (T) new ArrayList();
        } else if (rawClass.isAssignableFrom(HashSet.class)) {
            list = (T) new HashSet();
        } else if (rawClass.isAssignableFrom(LinkedHashSet.class)) {
            list = (T) new LinkedHashSet();
        } else if (rawClass.isAssignableFrom(TreeSet.class)) {
            list = (T) new TreeSet();
        } else if (rawClass.isAssignableFrom(ArrayList.class)) {
            list = (T) new ArrayList();
        } else if (rawClass.isAssignableFrom(EnumSet.class)) {
            if (type instanceof ParameterizedType) {
                itemType2 = ((ParameterizedType) type).getActualTypeArguments()[0];
            } else {
                itemType2 = Object.class;
            }
            list = (T) EnumSet.noneOf((Class) itemType2);
        } else {
            try {
                list = (T) ((Collection) rawClass.newInstance());
            } catch (Exception e) {
                throw new JSONException("create instane error, class " + rawClass.getName());
            }
        }
        if (type instanceof ParameterizedType) {
            itemType = ((ParameterizedType) type).getActualTypeArguments()[0];
        } else {
            itemType = Object.class;
        }
        parser.parseArray(itemType, list, fieldName);
        return (T) list;
    }

    public Class<?> getRawClass(Type type) {
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return getRawClass(((ParameterizedType) type).getRawType());
        }
        throw new JSONException("TODO");
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 14;
    }
}
