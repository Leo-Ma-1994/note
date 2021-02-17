package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

public class TimestampDeserializer extends AbstractDateDeserializer implements ObjectDeserializer {
    public static final TimestampDeserializer instance = new TimestampDeserializer();

    /* access modifiers changed from: protected */
    @Override // com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer
    public <T> T cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val) {
        if (val == null) {
            return null;
        }
        if (val instanceof Date) {
            return (T) new Timestamp(((Date) val).getTime());
        }
        if (val instanceof Number) {
            return (T) new Timestamp(((Number) val).longValue());
        }
        if (val instanceof String) {
            String strVal = (String) val;
            if (strVal.length() == 0) {
                return null;
            }
            try {
                return (T) new Timestamp(parser.getDateFormat().parse(strVal).getTime());
            } catch (ParseException e) {
                return (T) new Timestamp(Long.parseLong(strVal));
            }
        } else {
            throw new JSONException("parse error");
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 2;
    }
}
