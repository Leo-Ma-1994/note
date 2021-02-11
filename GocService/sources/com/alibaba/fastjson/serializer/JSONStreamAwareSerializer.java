package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONStreamAware;
import java.io.IOException;
import java.lang.reflect.Type;

public class JSONStreamAwareSerializer implements ObjectSerializer {
    public static JSONStreamAwareSerializer instance = new JSONStreamAwareSerializer();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        ((JSONStreamAware) object).writeJSONString(serializer.getWriter());
    }
}
