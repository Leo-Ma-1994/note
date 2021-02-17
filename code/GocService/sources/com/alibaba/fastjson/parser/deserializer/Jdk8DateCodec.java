package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class Jdk8DateCodec implements ObjectSerializer, ObjectDeserializer {
    public static final Jdk8DateCodec instance = new Jdk8DateCodec();

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        JSONLexer lexer = parser.getLexer();
        if (lexer.token() == 4) {
            String text = lexer.stringVal();
            lexer.nextToken();
            if (type == LocalDateTime.class) {
                return (T) LocalDateTime.parse(text);
            }
            if (type == LocalDate.class) {
                return (T) LocalDate.parse(text);
            }
            if (type == LocalTime.class) {
                return (T) LocalTime.parse(text);
            }
            if (type == ZonedDateTime.class) {
                return (T) ZonedDateTime.parse(text);
            }
            if (type == OffsetDateTime.class) {
                return (T) OffsetDateTime.parse(text);
            }
            if (type == OffsetTime.class) {
                return (T) OffsetTime.parse(text);
            }
            if (type == ZoneId.class) {
                return (T) ZoneId.of(text);
            }
            if (type == Period.class) {
                return (T) Period.parse(text);
            }
            if (type == Duration.class) {
                return (T) Duration.parse(text);
            }
            if (type == Instant.class) {
                return (T) Instant.parse(text);
            }
            return null;
        }
        throw new UnsupportedOperationException();
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 4;
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        SerializeWriter out = serializer.getWriter();
        if (object == null) {
            out.writeNull();
        } else {
            out.writeString(object.toString());
        }
    }
}
