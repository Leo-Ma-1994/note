package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class ObjectArraySerializer implements ObjectSerializer {
    public static final ObjectArraySerializer instance = new ObjectArraySerializer();

    /* JADX INFO: Multiple debug info for r0v8 java.lang.Object: [D('i' int), D('item' java.lang.Object)] */
    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public final void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        Class<?> preClazz;
        char c;
        SerializeWriter out = serializer.getWriter();
        Object[] array = (Object[]) object;
        if (object != null) {
            int size = array.length;
            int end = size - 1;
            if (end == -1) {
                out.append("[]");
                return;
            }
            SerialContext context = serializer.getContext();
            serializer.setContext(context, object, fieldName, 0);
            try {
                out.append('[');
                if (out.isEnabled(SerializerFeature.PrettyFormat)) {
                    serializer.incrementIndent();
                    serializer.println();
                    for (int i = 0; i < size; i++) {
                        if (i != 0) {
                            out.write(',');
                            serializer.println();
                        }
                        serializer.write(array[i]);
                    }
                    serializer.decrementIdent();
                    serializer.println();
                    out.write(']');
                    return;
                }
                Class<?> preClazz2 = null;
                ObjectSerializer preWriter = null;
                for (int i2 = 0; i2 < end; i2++) {
                    Object item = array[i2];
                    if (item == null) {
                        out.append("null,");
                    } else {
                        if (serializer.containsReference(item)) {
                            serializer.writeReference(item);
                            preClazz = preClazz2;
                            c = ',';
                        } else {
                            Class<?> clazz = item.getClass();
                            if (clazz == preClazz2) {
                                preClazz = preClazz2;
                                c = ',';
                                preWriter.write(serializer, item, null, null, 0);
                            } else {
                                c = ',';
                                preClazz = clazz;
                                ObjectSerializer preWriter2 = serializer.getObjectWriter(clazz);
                                preWriter2.write(serializer, item, null, null, 0);
                                preWriter = preWriter2;
                            }
                        }
                        out.append(c);
                        preClazz2 = preClazz;
                    }
                }
                Object item2 = array[end];
                if (item2 == null) {
                    out.append("null]");
                } else {
                    if (serializer.containsReference(item2)) {
                        serializer.writeReference(item2);
                    } else {
                        serializer.writeWithFieldName(item2, Integer.valueOf(end));
                    }
                    out.append(']');
                }
                serializer.setContext(context);
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
