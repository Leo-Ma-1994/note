package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public final class ListSerializer implements ObjectSerializer {
    public static final ListSerializer instance = new ListSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public final void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerialContext context;
        Throwable th;
        SerializeWriter out;
        boolean writeClassName = serializer.isEnabled(SerializerFeature.WriteClassName);
        SerializeWriter out2 = serializer.getWriter();
        Type elementType = (!writeClassName || !(fieldType instanceof ParameterizedType)) ? null : ((ParameterizedType) fieldType).getActualTypeArguments()[0];
        if (object != null) {
            List<?> list = (List) object;
            if (list.size() == 0) {
                out2.append("[]");
                return;
            }
            SerialContext context2 = serializer.getContext();
            serializer.setContext(context2, object, fieldName, 0);
            try {
                char c = ',';
                if (out2.isEnabled(SerializerFeature.PrettyFormat)) {
                    out2.append('[');
                    serializer.incrementIndent();
                    int i = 0;
                    for (Object item : list) {
                        if (i != 0) {
                            try {
                                out2.append(c);
                            } catch (Throwable th2) {
                                th = th2;
                                context = context2;
                            }
                        }
                        serializer.println();
                        if (item == null) {
                            context = context2;
                            out = out2;
                            serializer.getWriter().writeNull();
                        } else if (serializer.containsReference(item)) {
                            serializer.writeReference(item);
                            context = context2;
                            out = out2;
                        } else {
                            ObjectSerializer itemSerializer = serializer.getObjectWriter(item.getClass());
                            serializer.setContext(new SerialContext(context2, object, fieldName, 0, 0));
                            context = context2;
                            out = out2;
                            try {
                                itemSerializer.write(serializer, item, Integer.valueOf(i), elementType, 0);
                            } catch (Throwable th3) {
                                th = th3;
                                serializer.setContext(context);
                                throw th;
                            }
                        }
                        i++;
                        context2 = context;
                        out2 = out;
                        c = ',';
                    }
                    serializer.decrementIdent();
                    serializer.println();
                    out2.append(']');
                    serializer.setContext(context2);
                    return;
                }
                context = context2;
                char c2 = ',';
                out2.append('[');
                int i2 = 0;
                ObjectSerializer itemSerializer2 = null;
                for (Object item2 : list) {
                    try {
                        if (i2 != 0) {
                            out2.append(c2);
                        }
                        if (item2 == null) {
                            out2.append("null");
                        } else {
                            Class<?> clazz = item2.getClass();
                            if (clazz == Integer.class) {
                                out2.writeInt(((Integer) item2).intValue());
                            } else if (clazz == Long.class) {
                                long val = ((Long) item2).longValue();
                                if (writeClassName) {
                                    out2.writeLongAndChar(val, 'L');
                                } else {
                                    out2.writeLong(val);
                                }
                            } else {
                                serializer.setContext(new SerialContext(context, object, fieldName, 0, 0));
                                if (serializer.containsReference(item2)) {
                                    serializer.writeReference(item2);
                                } else {
                                    ObjectSerializer itemSerializer3 = serializer.getObjectWriter(item2.getClass());
                                    itemSerializer3.write(serializer, item2, Integer.valueOf(i2), elementType, 0);
                                    itemSerializer2 = itemSerializer3;
                                }
                            }
                        }
                        i2++;
                        c2 = ',';
                    } catch (Throwable th4) {
                        th = th4;
                        serializer.setContext(context);
                        throw th;
                    }
                }
                out2.append(']');
                serializer.setContext(context);
            } catch (Throwable th5) {
                th = th5;
                context = context2;
                serializer.setContext(context);
                throw th;
            }
        } else if (out2.isEnabled(SerializerFeature.WriteNullListAsEmpty)) {
            out2.write("[]");
        } else {
            out2.writeNull();
        }
    }
}
