package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class FloatArraySerializer implements ObjectSerializer {
    public static final FloatArraySerializer instance = new FloatArraySerializer();

    /* JADX INFO: Multiple debug info for r1v4 float: [D('item' float), D('i' int)] */
    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public final void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.getWriter();
        if (object != null) {
            float[] array = (float[]) object;
            int end = array.length - 1;
            if (end == -1) {
                out.append("[]");
                return;
            }
            out.append('[');
            for (int i = 0; i < end; i++) {
                float item = array[i];
                if (Float.isNaN(item)) {
                    out.writeNull();
                } else {
                    out.append((CharSequence) Float.toString(item));
                }
                out.append(',');
            }
            float item2 = array[end];
            if (Float.isNaN(item2)) {
                out.writeNull();
            } else {
                out.append((CharSequence) Float.toString(item2));
            }
            out.append(']');
        } else if (out.isEnabled(SerializerFeature.WriteNullListAsEmpty)) {
            out.write("[]");
        } else {
            out.writeNull();
        }
    }
}
