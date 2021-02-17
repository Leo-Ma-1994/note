package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class InetSocketAddressCodec implements ObjectSerializer, ObjectDeserializer {
    public static InetSocketAddressCodec instance = new InetSocketAddressCodec();

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        if (object == null) {
            serializer.writeNull();
            return;
        }
        SerializeWriter out = serializer.getWriter();
        InetSocketAddress address = (InetSocketAddress) object;
        InetAddress inetAddress = address.getAddress();
        out.write('{');
        if (inetAddress != null) {
            out.writeFieldName("address");
            serializer.write(inetAddress);
            out.write(',');
        }
        out.writeFieldName("port");
        out.writeInt(address.getPort());
        out.write('}');
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser parser, Type clazz, Object fieldName) {
        JSONLexer lexer = parser.getLexer();
        if (lexer.token() == 8) {
            lexer.nextToken();
            return null;
        }
        parser.accept(12);
        InetAddress address = null;
        int port = 0;
        while (true) {
            String key = lexer.stringVal();
            lexer.nextToken(17);
            if (key.equals("address")) {
                parser.accept(17);
                address = (InetAddress) parser.parseObject((Class<Object>) InetAddress.class);
            } else if (key.equals("port")) {
                parser.accept(17);
                if (lexer.token() == 2) {
                    port = lexer.intValue();
                    lexer.nextToken();
                } else {
                    throw new JSONException("port is not int");
                }
            } else {
                parser.accept(17);
                parser.parse();
            }
            if (lexer.token() == 16) {
                lexer.nextToken();
            } else {
                parser.accept(13);
                return (T) new InetSocketAddress(address, port);
            }
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }
}
