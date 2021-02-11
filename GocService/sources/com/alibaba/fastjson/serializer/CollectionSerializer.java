package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeSet;

public class CollectionSerializer implements ObjectSerializer {
    public static final CollectionSerializer instance = new CollectionSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.getWriter();
        if (object != null) {
            Type elementType = (!serializer.isEnabled(SerializerFeature.WriteClassName) || !(fieldType instanceof ParameterizedType)) ? null : ((ParameterizedType) fieldType).getActualTypeArguments()[0];
            Collection<?> collection = (Collection) object;
            SerialContext context = serializer.getContext();
            serializer.setContext(context, object, fieldName, 0);
            if (serializer.isEnabled(SerializerFeature.WriteClassName)) {
                if (HashSet.class == collection.getClass()) {
                    out.append("Set");
                } else if (TreeSet.class == collection.getClass()) {
                    out.append("TreeSet");
                }
            }
            int i = 0;
            try {
                out.append('[');
                for (Object item : collection) {
                    int i2 = i + 1;
                    if (i != 0) {
                        out.append(',');
                    }
                    if (item == null) {
                        out.writeNull();
                    } else {
                        Class<?> clazz = item.getClass();
                        if (clazz == Integer.class) {
                            out.writeInt(((Integer) item).intValue());
                        } else if (clazz == Long.class) {
                            out.writeLong(((Long) item).longValue());
                            if (out.isEnabled(SerializerFeature.WriteClassName)) {
                                out.write('L');
                            }
                        } else {
                            serializer.getObjectWriter(clazz).write(serializer, item, Integer.valueOf(i2 - 1), elementType, 0);
                        }
                    }
                    i = i2;
                }
                out.append(']');
            } finally {
                serializer.setContext(context);
            }
        } else if (out.isEnabled(SerializerFeature.WriteNullListAsEmpty)) {
            out.write("[]");
        } else {
            out.writeNull();
        }
    }
}
