package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class ArraySerializer implements ObjectSerializer {
    private final ObjectSerializer compObjectSerializer;
    private final Class<?> componentType;

    public ArraySerializer(Class<?> componentType2, ObjectSerializer compObjectSerializer2) {
        this.componentType = componentType2;
        this.compObjectSerializer = compObjectSerializer2;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public final void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.getWriter();
        if (object != null) {
            Object[] array = (Object[]) object;
            int size = array.length;
            SerialContext context = serializer.getContext();
            serializer.setContext(context, object, fieldName, 0);
            try {
                out.append('[');
                for (int i = 0; i < size; i++) {
                    if (i != 0) {
                        out.append(',');
                    }
                    Object item = array[i];
                    if (item == null) {
                        out.append("null");
                    } else if (item.getClass() == this.componentType) {
                        this.compObjectSerializer.write(serializer, item, Integer.valueOf(i), null, 0);
                    } else {
                        serializer.getObjectWriter(item.getClass()).write(serializer, item, Integer.valueOf(i), null, 0);
                    }
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
