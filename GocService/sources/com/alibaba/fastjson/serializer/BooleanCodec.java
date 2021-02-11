package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

public class BooleanCodec implements ObjectSerializer, ObjectDeserializer {
    public static final BooleanCodec instance = new BooleanCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.getWriter();
        Boolean value = (Boolean) object;
        if (value == null) {
            if (out.isEnabled(SerializerFeature.WriteNullBooleanAsFalse)) {
                out.write("false");
            } else {
                out.writeNull();
            }
        } else if (value.booleanValue()) {
            out.write("true");
        } else {
            out.write("false");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        Boolean value;
        JSONLexer lexer = parser.getLexer();
        if (lexer.token() == 6) {
            lexer.nextToken(16);
            value = (T) Boolean.TRUE;
        } else if (lexer.token() == 7) {
            lexer.nextToken(16);
            value = (T) Boolean.FALSE;
        } else if (lexer.token() == 2) {
            int intValue = lexer.intValue();
            lexer.nextToken(16);
            value = intValue == 1 ? (T) Boolean.TRUE : (T) Boolean.FALSE;
        } else {
            Object value2 = parser.parse();
            if (value2 == null) {
                return null;
            }
            value = (T) TypeUtils.castToBoolean(value2);
        }
        return clazz == AtomicBoolean.class ? (T) new AtomicBoolean(value.booleanValue()) : (T) value;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 6;
    }
}
