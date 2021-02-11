package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateDeserializer extends AbstractDateDeserializer implements ObjectDeserializer {
    public static final DateDeserializer instance = new DateDeserializer();

    /* JADX DEBUG: Multi-variable search result rejected for r10v0, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    /* access modifiers changed from: protected */
    @Override // com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer
    public <T> T cast(DefaultJSONParser parser, Type clazz, Object fieldName, Object val) {
        if (val == 0) {
            return null;
        }
        if (val instanceof Date) {
            return val;
        }
        if (val instanceof Number) {
            return (T) new Date(((Number) val).longValue());
        }
        if (val instanceof String) {
            String strVal = (String) val;
            if (strVal.length() == 0) {
                return null;
            }
            JSONScanner dateLexer = new JSONScanner(strVal);
            try {
                if (dateLexer.scanISO8601DateIfMatch(false)) {
                    T t = (T) dateLexer.getCalendar();
                    if (clazz == Calendar.class) {
                        return t;
                    }
                    T t2 = (T) t.getTime();
                    dateLexer.close();
                    return t2;
                }
                dateLexer.close();
                try {
                    return (T) parser.getDateFormat().parse(strVal);
                } catch (ParseException e) {
                    return (T) new Date(Long.parseLong(strVal));
                }
            } finally {
                dateLexer.close();
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
