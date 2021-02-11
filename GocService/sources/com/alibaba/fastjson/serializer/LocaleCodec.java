package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Locale;

public class LocaleCodec implements ObjectSerializer, ObjectDeserializer {
    public static final LocaleCodec instance = new LocaleCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object == null) {
            serializer.writeNull();
        } else {
            serializer.write(((Locale) object).toString());
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        String text = (String) parser.parse();
        if (text == null) {
            return null;
        }
        String[] items = text.split("_");
        return items.length == 1 ? (T) new Locale(items[0]) : items.length == 2 ? (T) new Locale(items[0], items[1]) : (T) new Locale(items[0], items[1], items[2]);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }
}
