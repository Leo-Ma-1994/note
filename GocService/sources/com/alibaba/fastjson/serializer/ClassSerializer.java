package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class ClassSerializer implements ObjectSerializer {
    public static final ClassSerializer instance = new ClassSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        serializer.getWriter().writeString(((Class) object).getName());
    }
}
