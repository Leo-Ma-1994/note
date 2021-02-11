package com.alibaba.fastjson.support.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class FastJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {
    public static final Charset UTF8 = Charset.forName("UTF-8");
    private Charset charset = UTF8;
    private SerializerFeature[] features = new SerializerFeature[0];

    public FastJsonHttpMessageConverter() {
        super(new MediaType[]{new MediaType("application", "json", UTF8), new MediaType("application", "*+json", UTF8)});
    }

    /* access modifiers changed from: protected */
    public boolean supports(Class<?> cls) {
        return true;
    }

    public Charset getCharset() {
        return this.charset;
    }

    public void setCharset(Charset charset2) {
        this.charset = charset2;
    }

    public SerializerFeature[] getFeatures() {
        return this.features;
    }

    public void setFeatures(SerializerFeature... features2) {
        this.features = features2;
    }

    /* access modifiers changed from: protected */
    public Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        InputStream in = inputMessage.getBody();
        byte[] buf = new byte[1024];
        while (true) {
            int len = in.read(buf);
            if (len == -1) {
                byte[] bytes = baos.toByteArray();
                return JSON.parseObject(bytes, 0, bytes.length, this.charset.newDecoder(), clazz, new Feature[0]);
            } else if (len > 0) {
                baos.write(buf, 0, len);
            }
        }
    }

    /* access modifiers changed from: protected */
    public void writeInternal(Object obj, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        outputMessage.getBody().write(JSON.toJSONString(obj, this.features).getBytes(this.charset));
    }
}
