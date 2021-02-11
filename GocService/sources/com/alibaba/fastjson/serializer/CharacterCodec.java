package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import com.goodocom.bttek.BuildConfig;
import java.io.IOException;
import java.lang.reflect.Type;

public class CharacterCodec implements ObjectSerializer, ObjectDeserializer {
    public static final CharacterCodec instance = new CharacterCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.getWriter();
        Character value = (Character) object;
        if (value == null) {
            out.writeString(BuildConfig.FLAVOR);
        } else if (value.charValue() == 0) {
            out.writeString("\u0000");
        } else {
            out.writeString(value.toString());
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        Object value = parser.parse();
        if (value == null) {
            return null;
        }
        return (T) TypeUtils.castToChar(value);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }
}
