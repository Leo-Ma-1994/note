package com.alibaba.fastjson.serializer;

import com.goodocom.bttek.BuildConfig;
import java.io.IOException;
import java.lang.reflect.Type;

public class AppendableSerializer implements ObjectSerializer {
    public static final AppendableSerializer instance = new AppendableSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object == null) {
            SerializeWriter out = serializer.getWriter();
            if (out.isEnabled(SerializerFeature.WriteNullStringAsEmpty)) {
                out.writeString(BuildConfig.FLAVOR);
            } else {
                out.writeNull();
            }
        } else {
            serializer.write(object.toString());
        }
    }
}
