package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class IntegerCodec implements ObjectSerializer, ObjectDeserializer {
    public static IntegerCodec instance = new IntegerCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.getWriter();
        Number value = (Number) object;
        if (value != null) {
            out.writeInt(value.intValue());
            if (serializer.isEnabled(SerializerFeature.WriteClassName)) {
                Class<?> clazz = value.getClass();
                if (clazz == Byte.class) {
                    out.write('B');
                } else if (clazz == Short.class) {
                    out.write('S');
                }
            }
        } else if (out.isEnabled(SerializerFeature.WriteNullNumberAsZero)) {
            out.write('0');
        } else {
            out.writeNull();
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        Integer decimalValue;
        JSONLexer lexer = parser.getLexer();
        if (lexer.token() == 8) {
            lexer.nextToken(16);
            return null;
        }
        if (lexer.token() == 2) {
            int val = lexer.intValue();
            lexer.nextToken(16);
            decimalValue = (T) Integer.valueOf(val);
        } else if (lexer.token() == 3) {
            BigDecimal decimalValue2 = lexer.decimalValue();
            lexer.nextToken(16);
            decimalValue = (T) Integer.valueOf(decimalValue2.intValue());
        } else {
            decimalValue = (T) TypeUtils.castToInt(parser.parse());
        }
        return clazz == AtomicInteger.class ? (T) new AtomicInteger(decimalValue.intValue()) : (T) decimalValue;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }
}
