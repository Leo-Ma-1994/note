package com.alibaba.fastjson;

import androidx.core.view.PointerIconCompat;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONReaderScanner;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.Map;

public class JSONReader implements Closeable {
    private JSONStreamContext context;
    private final DefaultJSONParser parser;

    public JSONReader(Reader reader) {
        this(new JSONReaderScanner(reader));
    }

    public JSONReader(JSONLexer lexer) {
        this(new DefaultJSONParser(lexer));
    }

    public JSONReader(DefaultJSONParser parser2) {
        this.parser = parser2;
    }

    public void config(Feature feature, boolean state) {
        this.parser.config(feature, state);
    }

    public void startObject() {
        if (this.context == null) {
            this.context = new JSONStreamContext(null, PointerIconCompat.TYPE_CONTEXT_MENU);
        } else {
            startStructure();
            this.context = new JSONStreamContext(this.context, PointerIconCompat.TYPE_CONTEXT_MENU);
        }
        this.parser.accept(12, 18);
    }

    public void endObject() {
        this.parser.accept(13);
        endStructure();
    }

    public void startArray() {
        if (this.context == null) {
            this.context = new JSONStreamContext(null, PointerIconCompat.TYPE_WAIT);
        } else {
            startStructure();
            this.context = new JSONStreamContext(this.context, PointerIconCompat.TYPE_WAIT);
        }
        this.parser.accept(14);
    }

    public void endArray() {
        this.parser.accept(15);
        endStructure();
    }

    private void startStructure() {
        switch (this.context.getState()) {
            case PointerIconCompat.TYPE_CONTEXT_MENU /* 1001 */:
            case PointerIconCompat.TYPE_WAIT /* 1004 */:
                return;
            case PointerIconCompat.TYPE_HAND /* 1002 */:
                this.parser.accept(17);
                return;
            case PointerIconCompat.TYPE_HELP /* 1003 */:
            case 1005:
                this.parser.accept(16);
                return;
            default:
                throw new JSONException("illegal state : " + this.context.getState());
        }
    }

    private void endStructure() {
        this.context = this.context.getParent();
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

    public boolean hasNext() {
        if (this.context != null) {
            int token = this.parser.getLexer().token();
            int state = this.context.getState();
            switch (state) {
                case PointerIconCompat.TYPE_CONTEXT_MENU /* 1001 */:
                case PointerIconCompat.TYPE_HELP /* 1003 */:
                    if (token != 13) {
                        return true;
                    }
                    return false;
                case PointerIconCompat.TYPE_HAND /* 1002 */:
                default:
                    throw new JSONException("illegal state : " + state);
                case PointerIconCompat.TYPE_WAIT /* 1004 */:
                case 1005:
                    if (token != 15) {
                        return true;
                    }
                    return false;
            }
        } else {
            throw new JSONException("context is null");
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        IOUtils.close(this.parser);
    }

    public Integer readInteger() {
        Object object;
        if (this.context == null) {
            object = this.parser.parse();
        } else {
            readBefore();
            object = this.parser.parse();
            readAfter();
        }
        return TypeUtils.castToInt(object);
    }

    public Long readLong() {
        Object object;
        if (this.context == null) {
            object = this.parser.parse();
        } else {
            readBefore();
            object = this.parser.parse();
            readAfter();
        }
        return TypeUtils.castToLong(object);
    }

    public String readString() {
        Object object;
        if (this.context == null) {
            object = this.parser.parse();
        } else {
            readBefore();
            object = this.parser.parse();
            readAfter();
        }
        return TypeUtils.castToString(object);
    }

    public <T> T readObject(TypeReference<T> typeRef) {
        return (T) readObject(typeRef.getType());
    }

    public <T> T readObject(Type type) {
        if (this.context == null) {
            return (T) this.parser.parseObject(type);
        }
        readBefore();
        T object = (T) this.parser.parseObject(type);
        readAfter();
        return object;
    }

    public <T> T readObject(Class<T> type) {
        if (this.context == null) {
            return (T) this.parser.parseObject((Class<Object>) type);
        }
        readBefore();
        T object = (T) this.parser.parseObject((Class<Object>) type);
        readAfter();
        return object;
    }

    public void readObject(Object object) {
        if (this.context == null) {
            this.parser.parseObject(object);
            return;
        }
        readBefore();
        this.parser.parseObject(object);
        readAfter();
    }

    public Object readObject() {
        Object object;
        if (this.context == null) {
            return this.parser.parse();
        }
        readBefore();
        int state = this.context.getState();
        if (state == 1001 || state == 1003) {
            object = this.parser.parseKey();
        } else {
            object = this.parser.parse();
        }
        readAfter();
        return object;
    }

    public Object readObject(Map object) {
        if (this.context == null) {
            return this.parser.parseObject(object);
        }
        readBefore();
        Object value = this.parser.parseObject(object);
        readAfter();
        return value;
    }

    private void readBefore() {
        int state = this.context.getState();
        switch (state) {
            case PointerIconCompat.TYPE_CONTEXT_MENU /* 1001 */:
            case PointerIconCompat.TYPE_WAIT /* 1004 */:
                return;
            case PointerIconCompat.TYPE_HAND /* 1002 */:
                this.parser.accept(17);
                return;
            case PointerIconCompat.TYPE_HELP /* 1003 */:
                this.parser.accept(16, 18);
                return;
            case 1005:
                this.parser.accept(16);
                return;
            default:
                throw new JSONException("illegal state : " + state);
        }
    }

    private void readAfter() {
        int state = this.context.getState();
        int newStat = -1;
        switch (state) {
            case PointerIconCompat.TYPE_CONTEXT_MENU /* 1001 */:
                newStat = PointerIconCompat.TYPE_HAND;
                break;
            case PointerIconCompat.TYPE_HAND /* 1002 */:
                newStat = PointerIconCompat.TYPE_HELP;
                break;
            case PointerIconCompat.TYPE_HELP /* 1003 */:
                newStat = PointerIconCompat.TYPE_HAND;
                break;
            case PointerIconCompat.TYPE_WAIT /* 1004 */:
                newStat = 1005;
                break;
            case 1005:
                break;
            default:
                throw new JSONException("illegal state : " + state);
        }
        if (newStat != -1) {
            this.context.setState(newStat);
        }
    }
}
