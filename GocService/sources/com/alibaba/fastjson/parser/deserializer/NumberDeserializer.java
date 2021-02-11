package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;

public class NumberDeserializer implements ObjectDeserializer {
    public static final NumberDeserializer instance = new NumberDeserializer();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        JSONLexer lexer = parser.getLexer();
        if (lexer.token() == 2) {
            if (clazz == Double.TYPE || clazz == Double.class) {
                String val = lexer.numberString();
                lexer.nextToken(16);
                return (T) Double.valueOf(Double.parseDouble(val));
            }
            long val2 = lexer.longValue();
            lexer.nextToken(16);
            return (clazz == Short.TYPE || clazz == Short.class) ? (T) Short.valueOf((short) ((int) val2)) : (clazz == Byte.TYPE || clazz == Byte.class) ? (T) Byte.valueOf((byte) ((int) val2)) : (val2 < -2147483648L || val2 > 2147483647L) ? (T) Long.valueOf(val2) : (T) Integer.valueOf((int) val2);
        } else if (lexer.token() != 3) {
            Object value = parser.parse();
            if (value == null) {
                return null;
            }
            return (clazz == Double.TYPE || clazz == Double.class) ? (T) TypeUtils.castToDouble(value) : (clazz == Short.TYPE || clazz == Short.class) ? (T) TypeUtils.castToShort(value) : (clazz == Byte.TYPE || clazz == Byte.class) ? (T) TypeUtils.castToByte(value) : (T) TypeUtils.castToBigDecimal(value);
        } else if (clazz == Double.TYPE || clazz == Double.class) {
            String val3 = lexer.numberString();
            lexer.nextToken(16);
            return (T) Double.valueOf(Double.parseDouble(val3));
        } else {
            T t = (T) lexer.decimalValue();
            lexer.nextToken(16);
            return (clazz == Short.TYPE || clazz == Short.class) ? (T) Short.valueOf(t.shortValue()) : (clazz == Byte.TYPE || clazz == Byte.class) ? (T) Byte.valueOf(t.byteValue()) : t;
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }
}
