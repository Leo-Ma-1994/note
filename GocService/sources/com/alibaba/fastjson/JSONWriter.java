package com.alibaba.fastjson;

import androidx.core.view.PointerIconCompat;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.SerializeWriter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class JSONWriter implements Closeable, Flushable {
    private JSONStreamContext context;
    private JSONSerializer serializer = new JSONSerializer(this.writer);
    private SerializeWriter writer;

    public JSONWriter(Writer out) {
        this.writer = new SerializeWriter(out);
    }

    public void config(SerializerFeature feature, boolean state) {
        this.writer.config(feature, state);
    }

    public void startObject() {
        if (this.context != null) {
            beginStructure();
        }
        this.context = new JSONStreamContext(this.context, PointerIconCompat.TYPE_CONTEXT_MENU);
        this.writer.write('{');
    }

    public void endObject() {
        this.writer.write('}');
        endStructure();
    }

    public void writeKey(String key) {
        writeObject(key);
    }

    public void writeValue(Object object) {
        writeObject(object);
    }

    public void writeObject(String object) {
        beforeWrite();
        this.serializer.write(object);
        afterWriter();
    }

    public void writeObject(Object object) {
        beforeWrite();
        this.serializer.write(object);
        afterWriter();
    }

    public void startArray() {
        if (this.context != null) {
            beginStructure();
        }
        this.context = new JSONStreamContext(this.context, PointerIconCompat.TYPE_WAIT);
        this.writer.write('[');
    }

    private void beginStructure() {
        int state = this.context.getState();
        switch (state) {
            case PointerIconCompat.TYPE_CONTEXT_MENU /* 1001 */:
            case PointerIconCompat.TYPE_WAIT /* 1004 */:
                return;
            case PointerIconCompat.TYPE_HAND /* 1002 */:
                this.writer.write(':');
                return;
            case PointerIconCompat.TYPE_HELP /* 1003 */:
            default:
                throw new JSONException("illegal state : " + state);
            case 1005:
                this.writer.write(',');
                return;
        }
    }

    public void endArray() {
        this.writer.write(']');
        endStructure();
    }

    private void endStructure() {
        this.context = this.context.getParent();
        JSONStreamContext jSONStreamContext = this.context;
        if (jSONStreamContext != null) {
            int newState = -1;
            switch (jSONStreamContext.getState()) {
                case PointerIconCompat.TYPE_CONTEXT_MENU /* 1001 */:
                    newState = PointerIconCompat.TYPE_HAND;
                    break;
                case PointerIconCompat.TYPE_HAND /* 1002 */:
                    newState = PointerIconCompat.TYPE_HELP;
                    break;
                case PointerIconCompat.TYPE_WAIT /* 1004 */:
                    newState = 1005;
                    break;
            }
            if (newState != -1) {
                this.context.setState(newState);
            }
        }
    }

    private void beforeWrite() {
        JSONStreamContext jSONStreamContext = this.context;
        if (jSONStreamContext != null) {
            switch (jSONStreamContext.getState()) {
                case PointerIconCompat.TYPE_CONTEXT_MENU /* 1001 */:
                case PointerIconCompat.TYPE_WAIT /* 1004 */:
                default:
                    return;
                case PointerIconCompat.TYPE_HAND /* 1002 */:
                    this.writer.write(':');
                    return;
                case PointerIconCompat.TYPE_HELP /* 1003 */:
                    this.writer.write(',');
                    return;
                case 1005:
                    this.writer.write(',');
                    return;
            }
        }
    }

    private void afterWriter() {
        JSONStreamContext jSONStreamContext = this.context;
        if (jSONStreamContext != null) {
            int newState = -1;
            switch (jSONStreamContext.getState()) {
                case PointerIconCompat.TYPE_CONTEXT_MENU /* 1001 */:
                case PointerIconCompat.TYPE_HELP /* 1003 */:
                    newState = PointerIconCompat.TYPE_HAND;
                    break;
                case PointerIconCompat.TYPE_HAND /* 1002 */:
                    newState = PointerIconCompat.TYPE_HELP;
                    break;
                case PointerIconCompat.TYPE_WAIT /* 1004 */:
                    newState = 1005;
                    break;
            }
            if (newState != -1) {
                this.context.setState(newState);
            }
        }
    }

    @Override // java.io.Flushable
    public void flush() throws IOException {
        this.writer.flush();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.writer.close();
    }

    @Deprecated
    public void writeStartObject() {
        startObject();
    }

    @Deprecated
    public void writeEndObject() {
        endObject();
    }

    @Deprecated
    public void writeStartArray() {
        startArray();
    }

    @Deprecated
    public void writeEndArray() {
        endArray();
    }
}
