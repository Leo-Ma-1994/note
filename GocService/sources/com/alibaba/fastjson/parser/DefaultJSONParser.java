package com.alibaba.fastjson.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.deserializer.ASMJavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.CollectionResolveFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ExtraProcessor;
import com.alibaba.fastjson.parser.deserializer.ExtraTypeProvider;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ListResolveFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.MapResolveFieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.IntegerCodec;
import com.alibaba.fastjson.serializer.LongCodec;
import com.alibaba.fastjson.serializer.StringCodec;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.Closeable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class DefaultJSONParser extends AbstractJSONParser implements Closeable {
    public static final int NONE = 0;
    public static final int NeedToResolve = 1;
    public static final int TypeNameRedirect = 2;
    private static final Set<Class<?>> primitiveClasses = new HashSet();
    protected ParserConfig config;
    protected ParseContext context;
    private ParseContext[] contextArray;
    private int contextArrayIndex;
    private DateFormat dateFormat;
    private String dateFormatPattern;
    private List<ExtraProcessor> extraProcessors;
    private List<ExtraTypeProvider> extraTypeProviders;
    protected final Object input;
    protected final JSONLexer lexer;
    private int resolveStatus;
    private List<ResolveTask> resolveTaskList;
    protected final SymbolTable symbolTable;

    static {
        primitiveClasses.add(Boolean.TYPE);
        primitiveClasses.add(Byte.TYPE);
        primitiveClasses.add(Short.TYPE);
        primitiveClasses.add(Integer.TYPE);
        primitiveClasses.add(Long.TYPE);
        primitiveClasses.add(Float.TYPE);
        primitiveClasses.add(Double.TYPE);
        primitiveClasses.add(Boolean.class);
        primitiveClasses.add(Byte.class);
        primitiveClasses.add(Short.class);
        primitiveClasses.add(Integer.class);
        primitiveClasses.add(Long.class);
        primitiveClasses.add(Float.class);
        primitiveClasses.add(Double.class);
        primitiveClasses.add(BigInteger.class);
        primitiveClasses.add(BigDecimal.class);
        primitiveClasses.add(String.class);
    }

    public String getDateFomartPattern() {
        return this.dateFormatPattern;
    }

    public DateFormat getDateFormat() {
        if (this.dateFormat == null) {
            this.dateFormat = new SimpleDateFormat(this.dateFormatPattern);
        }
        return this.dateFormat;
    }

    public void setDateFormat(String dateFormat2) {
        this.dateFormatPattern = dateFormat2;
        this.dateFormat = null;
    }

    public void setDateFomrat(DateFormat dateFormat2) {
        this.dateFormat = dateFormat2;
    }

    public DefaultJSONParser(String input2) {
        this(input2, ParserConfig.getGlobalInstance(), JSON.DEFAULT_PARSER_FEATURE);
    }

    public DefaultJSONParser(String input2, ParserConfig config2) {
        this(input2, new JSONScanner(input2, JSON.DEFAULT_PARSER_FEATURE), config2);
    }

    public DefaultJSONParser(String input2, ParserConfig config2, int features) {
        this(input2, new JSONScanner(input2, features), config2);
    }

    public DefaultJSONParser(char[] input2, int length, ParserConfig config2, int features) {
        this(input2, new JSONScanner(input2, length, features), config2);
    }

    public DefaultJSONParser(JSONLexer lexer2) {
        this(lexer2, ParserConfig.getGlobalInstance());
    }

    public DefaultJSONParser(JSONLexer lexer2, ParserConfig config2) {
        this((Object) null, lexer2, config2);
    }

    public DefaultJSONParser(Object input2, JSONLexer lexer2, ParserConfig config2) {
        this.dateFormatPattern = JSON.DEFFAULT_DATE_FORMAT;
        this.contextArray = new ParseContext[8];
        this.contextArrayIndex = 0;
        this.resolveStatus = 0;
        this.extraTypeProviders = null;
        this.extraProcessors = null;
        this.lexer = lexer2;
        this.input = input2;
        this.config = config2;
        this.symbolTable = config2.getSymbolTable();
        lexer2.nextToken(12);
    }

    public SymbolTable getSymbolTable() {
        return this.symbolTable;
    }

    public String getInput() {
        Object obj = this.input;
        if (obj instanceof char[]) {
            return new String((char[]) obj);
        }
        return obj.toString();
    }

    /* JADX DEBUG: Failed to insert an additional move for type inference into block B:83:0x01f4 */
    public final Object parseObject(Map object, Object fieldName) {
        Object key;
        Number number;
        Object key2;
        JSONLexer lexer2 = this.lexer;
        if (lexer2.token() == 8) {
            lexer2.next();
            return null;
        } else if (lexer2.token() == 12 || lexer2.token() == 16) {
            ParseContext context2 = getContext();
            boolean setContextFlag = false;
            while (true) {
                try {
                    lexer2.skipWhitespace();
                    char ch = lexer2.getCurrent();
                    if (isEnabled(Feature.AllowArbitraryCommas)) {
                        while (ch == ',') {
                            lexer2.next();
                            lexer2.skipWhitespace();
                            ch = lexer2.getCurrent();
                        }
                    }
                    boolean isObjectKey = false;
                    if (ch == '\"') {
                        Object key3 = lexer2.scanSymbol(this.symbolTable, '\"');
                        lexer2.skipWhitespace();
                        key = key3;
                        if (lexer2.getCurrent() != ':') {
                            throw new JSONException("expect ':' at " + lexer2.pos() + ", name " + key3);
                        }
                    } else if (ch == '}') {
                        lexer2.next();
                        lexer2.resetStringPosition();
                        lexer2.nextToken();
                        setContext(context2);
                        return object;
                    } else if (ch == '\'') {
                        if (isEnabled(Feature.AllowSingleQuotes)) {
                            Object scanSymbol = lexer2.scanSymbol(this.symbolTable, '\'');
                            lexer2.skipWhitespace();
                            key = scanSymbol;
                            if (lexer2.getCurrent() != ':') {
                                throw new JSONException("expect ':' at " + lexer2.pos());
                            }
                        } else {
                            throw new JSONException("syntax error");
                        }
                    } else if (ch == 26) {
                        throw new JSONException("syntax error");
                    } else if (ch == ',') {
                        throw new JSONException("syntax error");
                    } else if ((ch >= '0' && ch <= '9') || ch == '-') {
                        lexer2.resetStringPosition();
                        lexer2.scanNumber();
                        if (lexer2.token() == 2) {
                            key2 = lexer2.integerValue();
                        } else {
                            key2 = lexer2.decimalValue(true);
                        }
                        key = key2;
                        if (lexer2.getCurrent() != ':') {
                            throw new JSONException("expect ':' at " + lexer2.pos() + ", name " + key2);
                        }
                    } else if (ch == '{' || ch == '[') {
                        lexer2.nextToken();
                        key = parse();
                        isObjectKey = true;
                    } else if (isEnabled(Feature.AllowUnQuotedFieldNames)) {
                        Object scanSymbolUnQuoted = lexer2.scanSymbolUnQuoted(this.symbolTable);
                        lexer2.skipWhitespace();
                        char ch2 = lexer2.getCurrent();
                        key = scanSymbolUnQuoted;
                        if (ch2 != ':') {
                            throw new JSONException("expect ':' at " + lexer2.pos() + ", actual " + ch2);
                        }
                    } else {
                        throw new JSONException("syntax error");
                    }
                    if (!isObjectKey) {
                        lexer2.next();
                        lexer2.skipWhitespace();
                    }
                    char ch3 = lexer2.getCurrent();
                    lexer2.resetStringPosition();
                    if (key == JSON.DEFAULT_TYPE_KEY) {
                        String typeName = lexer2.scanSymbol(this.symbolTable, '\"');
                        Class<?> clazz = TypeUtils.loadClass(typeName);
                        if (clazz == null) {
                            object.put(JSON.DEFAULT_TYPE_KEY, typeName);
                        } else {
                            lexer2.nextToken(16);
                            if (lexer2.token() == 13) {
                                lexer2.nextToken(16);
                                Object instance = null;
                                try {
                                    ObjectDeserializer deserializer = this.config.getDeserializer(clazz);
                                    if (deserializer instanceof ASMJavaBeanDeserializer) {
                                        instance = ((ASMJavaBeanDeserializer) deserializer).createInstance(this, clazz);
                                    } else if (deserializer instanceof JavaBeanDeserializer) {
                                        instance = ((JavaBeanDeserializer) deserializer).createInstance(this, clazz);
                                    }
                                    if (instance == null) {
                                        if (clazz == Cloneable.class) {
                                            instance = new HashMap();
                                        } else {
                                            instance = clazz.newInstance();
                                        }
                                    }
                                    return instance;
                                } catch (Exception e) {
                                    throw new JSONException("create instance error", e);
                                }
                            } else {
                                setResolveStatus(2);
                                if (this.context != null && !(fieldName instanceof Integer)) {
                                    popContext();
                                }
                                Object deserialze = this.config.getDeserializer(clazz).deserialze(this, clazz, fieldName);
                                setContext(context2);
                                return deserialze;
                            }
                        }
                    } else if (key == "$ref") {
                        lexer2.nextToken(4);
                        if (lexer2.token() == 4) {
                            String ref = lexer2.stringVal();
                            lexer2.nextToken(13);
                            Object refValue = null;
                            if ("@".equals(ref)) {
                                if (getContext() != null) {
                                    ParseContext thisContext = getContext();
                                    Object thisObj = thisContext.getObject();
                                    if ((thisObj instanceof Object[]) || (thisObj instanceof Collection)) {
                                        refValue = thisObj;
                                    } else if (thisContext.getParentContext() != null) {
                                        refValue = thisContext.getParentContext().getObject();
                                    }
                                }
                            } else if ("..".equals(ref)) {
                                ParseContext parentContext = context2.getParentContext();
                                if (parentContext.getObject() != null) {
                                    refValue = parentContext.getObject();
                                } else {
                                    addResolveTask(new ResolveTask(parentContext, ref));
                                    setResolveStatus(1);
                                }
                            } else if ("$".equals(ref)) {
                                ParseContext rootContext = context2;
                                while (rootContext.getParentContext() != null) {
                                    rootContext = rootContext.getParentContext();
                                }
                                if (rootContext.getObject() != null) {
                                    refValue = rootContext.getObject();
                                } else {
                                    addResolveTask(new ResolveTask(rootContext, ref));
                                    setResolveStatus(1);
                                }
                            } else {
                                addResolveTask(new ResolveTask(context2, ref));
                                setResolveStatus(1);
                            }
                            if (lexer2.token() == 13) {
                                lexer2.nextToken(16);
                                setContext(context2);
                                return refValue;
                            }
                            throw new JSONException("syntax error");
                        }
                        throw new JSONException("illegal ref, " + JSONToken.name(lexer2.token()));
                    } else {
                        if (!setContextFlag) {
                            setContext(object, fieldName);
                            setContextFlag = true;
                        }
                        String str = key;
                        if (object.getClass() == JSONObject.class) {
                            str = key == null ? "null" : key.toString();
                        }
                        if (ch3 == '\"') {
                            lexer2.scanString();
                            String strValue = lexer2.stringVal();
                            String str2 = strValue;
                            if (lexer2.isEnabled(Feature.AllowISO8601DateFormat)) {
                                JSONScanner iso8601Lexer = new JSONScanner(strValue);
                                if (iso8601Lexer.scanISO8601DateIfMatch()) {
                                    str2 = iso8601Lexer.getCalendar().getTime();
                                }
                                iso8601Lexer.close();
                            }
                            object.put(str, str2);
                        } else if ((ch3 >= '0' && ch3 <= '9') || ch3 == '-') {
                            lexer2.scanNumber();
                            if (lexer2.token() == 2) {
                                number = lexer2.integerValue();
                            } else {
                                number = lexer2.decimalValue(isEnabled(Feature.UseBigDecimal));
                            }
                            object.put(str, number);
                        } else if (ch3 == '[') {
                            lexer2.nextToken();
                            JSONArray list = new JSONArray();
                            parseArray(list, str);
                            object.put(str, list);
                            if (lexer2.token() == 13) {
                                lexer2.nextToken();
                                setContext(context2);
                                return object;
                            } else if (lexer2.token() != 16) {
                                throw new JSONException("syntax error");
                            }
                        } else if (ch3 == '{') {
                            lexer2.nextToken();
                            boolean parentIsArray = fieldName != null && fieldName.getClass() == Integer.class;
                            JSONObject input2 = new JSONObject();
                            ParseContext ctxLocal = null;
                            if (!parentIsArray) {
                                ctxLocal = setContext(context2, input2, str);
                            }
                            Object obj = parseObject(input2, str);
                            if (!(ctxLocal == null || input2 == obj)) {
                                ctxLocal.setObject(object);
                            }
                            checkMapResolve(object, str.toString());
                            if (object.getClass() == JSONObject.class) {
                                object.put(str.toString(), obj);
                            } else {
                                object.put(str, obj);
                            }
                            if (parentIsArray) {
                                setContext(context2, obj, str);
                            }
                            if (lexer2.token() == 13) {
                                lexer2.nextToken();
                                setContext(context2);
                                setContext(context2);
                                return object;
                            } else if (lexer2.token() != 16) {
                                throw new JSONException("syntax error, " + lexer2.tokenName());
                            }
                        } else {
                            lexer2.nextToken();
                            Object value = parse();
                            String str3 = str;
                            if (object.getClass() == JSONObject.class) {
                                str3 = str.toString();
                            }
                            object.put(str3, value);
                            if (lexer2.token() == 13) {
                                lexer2.nextToken();
                                setContext(context2);
                                return object;
                            } else if (lexer2.token() != 16) {
                                throw new JSONException("syntax error, position at " + lexer2.pos() + ", name " + ((Object) str3));
                            }
                        }
                        lexer2.skipWhitespace();
                        char ch4 = lexer2.getCurrent();
                        if (ch4 == ',') {
                            lexer2.next();
                        } else if (ch4 == '}') {
                            lexer2.next();
                            lexer2.resetStringPosition();
                            lexer2.nextToken();
                            setContext(object, fieldName);
                            setContext(context2);
                            return object;
                        } else {
                            throw new JSONException("syntax error, position at " + lexer2.pos() + ", name " + ((Object) str));
                        }
                    }
                } finally {
                    setContext(context2);
                }
            }
        } else {
            throw new JSONException("syntax error, expect {, actual " + lexer2.tokenName());
        }
    }

    public ParserConfig getConfig() {
        return this.config;
    }

    public void setConfig(ParserConfig config2) {
        this.config = config2;
    }

    public <T> T parseObject(Class<T> clazz) {
        return (T) parseObject((Type) clazz);
    }

    public <T> T parseObject(Type type) {
        if (this.lexer.token() == 8) {
            this.lexer.nextToken();
            return null;
        }
        if (this.lexer.token() == 4) {
            type = TypeUtils.unwrap(type);
            if (type == byte[].class) {
                T t = (T) this.lexer.bytesValue();
                this.lexer.nextToken();
                return t;
            } else if (type == char[].class) {
                String strVal = this.lexer.stringVal();
                this.lexer.nextToken();
                return (T) strVal.toCharArray();
            }
        }
        try {
            return (T) this.config.getDeserializer(type).deserialze(this, type, null);
        } catch (JSONException e) {
            throw e;
        } catch (Throwable e2) {
            throw new JSONException(e2.getMessage(), e2);
        }
    }

    public <T> List<T> parseArray(Class<T> clazz) {
        List<T> array = new ArrayList<>();
        parseArray((Class<?>) clazz, (Collection) array);
        return array;
    }

    public void parseArray(Class<?> clazz, Collection array) {
        parseArray((Type) clazz, array);
    }

    public void parseArray(Type type, Collection array) {
        parseArray(type, array, null);
    }

    /* JADX INFO: finally extract failed */
    public void parseArray(Type type, Collection array, Object fieldName) {
        ObjectDeserializer deserializer;
        Object val;
        String value;
        if (this.lexer.token() == 21 || this.lexer.token() == 22) {
            this.lexer.nextToken();
        }
        if (this.lexer.token() == 14) {
            if (Integer.TYPE == type) {
                deserializer = IntegerCodec.instance;
                this.lexer.nextToken(2);
            } else if (String.class == type) {
                deserializer = StringCodec.instance;
                this.lexer.nextToken(4);
            } else {
                deserializer = this.config.getDeserializer(type);
                this.lexer.nextToken(deserializer.getFastMatchToken());
            }
            ParseContext context2 = getContext();
            setContext(array, fieldName);
            int i = 0;
            while (true) {
                try {
                    if (isEnabled(Feature.AllowArbitraryCommas)) {
                        while (this.lexer.token() == 16) {
                            this.lexer.nextToken();
                        }
                    }
                    if (this.lexer.token() == 15) {
                        setContext(context2);
                        this.lexer.nextToken(16);
                        return;
                    }
                    if (Integer.TYPE == type) {
                        array.add(IntegerCodec.instance.deserialze(this, null, null));
                    } else if (String.class == type) {
                        if (this.lexer.token() == 4) {
                            value = this.lexer.stringVal();
                            this.lexer.nextToken(16);
                        } else {
                            Object obj = parse();
                            if (obj == null) {
                                value = null;
                            } else {
                                value = obj.toString();
                            }
                        }
                        array.add(value);
                    } else {
                        if (this.lexer.token() == 8) {
                            this.lexer.nextToken();
                            val = null;
                        } else {
                            val = deserializer.deserialze(this, type, Integer.valueOf(i));
                        }
                        array.add(val);
                        checkListResolve(array);
                    }
                    if (this.lexer.token() == 16) {
                        this.lexer.nextToken(deserializer.getFastMatchToken());
                    }
                    i++;
                } catch (Throwable th) {
                    setContext(context2);
                    throw th;
                }
            }
        } else {
            throw new JSONException("exepct '[', but " + JSONToken.name(this.lexer.token()));
        }
    }

    /* JADX INFO: Multiple debug info for r11v3 java.lang.reflect.Type: [D('type' java.lang.reflect.Type), D('value' java.lang.Object)] */
    public Object[] parseArray(Type[] types) {
        Object value;
        int i = 8;
        Object obj = null;
        if (this.lexer.token() == 8) {
            this.lexer.nextToken(16);
            return null;
        }
        int i2 = 14;
        if (this.lexer.token() == 14) {
            Object[] list = new Object[types.length];
            if (types.length == 0) {
                this.lexer.nextToken(15);
                if (this.lexer.token() == 15) {
                    this.lexer.nextToken(16);
                    return new Object[0];
                }
                throw new JSONException("syntax error");
            }
            this.lexer.nextToken(2);
            int i3 = 0;
            while (i3 < types.length) {
                if (this.lexer.token() == i) {
                    this.lexer.nextToken(16);
                    value = null;
                } else {
                    Type type = types[i3];
                    if (type == Integer.TYPE || type == Integer.class) {
                        if (this.lexer.token() == 2) {
                            value = Integer.valueOf(this.lexer.intValue());
                            this.lexer.nextToken(16);
                        } else {
                            value = TypeUtils.cast(parse(), type, this.config);
                        }
                    } else if (type != String.class) {
                        boolean isArray = false;
                        Class<?> componentType = null;
                        if (i3 == types.length - 1 && (type instanceof Class)) {
                            Class<?> clazz = (Class) type;
                            isArray = clazz.isArray();
                            componentType = clazz.getComponentType();
                        }
                        if (!isArray || this.lexer.token() == i2) {
                            obj = null;
                            value = this.config.getDeserializer(type).deserialze(this, type, null);
                        } else {
                            List<Object> varList = new ArrayList<>();
                            ObjectDeserializer derializer = this.config.getDeserializer(componentType);
                            int fastMatch = derializer.getFastMatchToken();
                            if (this.lexer.token() != 15) {
                                while (true) {
                                    varList.add(derializer.deserialze(this, type, obj));
                                    if (this.lexer.token() != 16) {
                                        break;
                                    }
                                    this.lexer.nextToken(fastMatch);
                                    obj = null;
                                }
                                if (this.lexer.token() != 15) {
                                    throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                                }
                            }
                            value = TypeUtils.cast(varList, type, this.config);
                            obj = null;
                        }
                    } else if (this.lexer.token() == 4) {
                        Object value2 = this.lexer.stringVal();
                        this.lexer.nextToken(16);
                        value = value2;
                    } else {
                        value = TypeUtils.cast(parse(), type, this.config);
                    }
                }
                list[i3] = value;
                if (this.lexer.token() == 15) {
                    break;
                } else if (this.lexer.token() == 16) {
                    if (i3 == types.length - 1) {
                        this.lexer.nextToken(15);
                    } else {
                        this.lexer.nextToken(2);
                    }
                    i3++;
                    i = 8;
                    i2 = 14;
                } else {
                    throw new JSONException("syntax error :" + JSONToken.name(this.lexer.token()));
                }
            }
            if (this.lexer.token() == 15) {
                this.lexer.nextToken(16);
                return list;
            }
            throw new JSONException("syntax error");
        }
        throw new JSONException("syntax error : " + this.lexer.tokenName());
    }

    public void parseObject(Object object) {
        Object fieldValue;
        Class<?> clazz = object.getClass();
        Map<String, FieldDeserializer> setters = this.config.getFieldDeserializers(clazz);
        if (this.lexer.token() == 12 || this.lexer.token() == 16) {
            while (true) {
                String key = this.lexer.scanSymbol(this.symbolTable);
                if (key == null) {
                    if (this.lexer.token() == 13) {
                        this.lexer.nextToken(16);
                        return;
                    } else if (this.lexer.token() == 16 && isEnabled(Feature.AllowArbitraryCommas)) {
                    }
                }
                FieldDeserializer fieldDeser = setters.get(key);
                if (fieldDeser == null && key != null) {
                    Iterator<Map.Entry<String, FieldDeserializer>> it = setters.entrySet().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        Map.Entry<String, FieldDeserializer> entry = it.next();
                        if (key.equalsIgnoreCase(entry.getKey())) {
                            fieldDeser = entry.getValue();
                            break;
                        }
                    }
                }
                if (fieldDeser != null) {
                    Class<?> fieldClass = fieldDeser.getFieldClass();
                    Type fieldType = fieldDeser.getFieldType();
                    if (fieldClass == Integer.TYPE) {
                        this.lexer.nextTokenWithColon(2);
                        fieldValue = IntegerCodec.instance.deserialze(this, fieldType, null);
                    } else if (fieldClass == String.class) {
                        this.lexer.nextTokenWithColon(4);
                        fieldValue = StringCodec.deserialze(this);
                    } else if (fieldClass == Long.TYPE) {
                        this.lexer.nextTokenWithColon(2);
                        fieldValue = LongCodec.instance.deserialze(this, fieldType, null);
                    } else {
                        ObjectDeserializer fieldValueDeserializer = this.config.getDeserializer(fieldClass, fieldType);
                        this.lexer.nextTokenWithColon(fieldValueDeserializer.getFastMatchToken());
                        fieldValue = fieldValueDeserializer.deserialze(this, fieldType, null);
                    }
                    fieldDeser.setValue(object, fieldValue);
                    if (this.lexer.token() != 16 && this.lexer.token() == 13) {
                        this.lexer.nextToken(16);
                        return;
                    }
                } else if (isEnabled(Feature.IgnoreNotMatch)) {
                    this.lexer.nextTokenWithColon();
                    parse();
                    if (this.lexer.token() == 13) {
                        this.lexer.nextToken();
                        return;
                    }
                } else {
                    throw new JSONException("setter not found, class " + clazz.getName() + ", property " + key);
                }
            }
        } else {
            throw new JSONException("syntax error, expect {, actual " + this.lexer.tokenName());
        }
    }

    public Object parseArrayWithType(Type collectionType) {
        if (this.lexer.token() == 8) {
            this.lexer.nextToken();
            return null;
        }
        Type[] actualTypes = ((ParameterizedType) collectionType).getActualTypeArguments();
        if (actualTypes.length == 1) {
            Type actualTypeArgument = actualTypes[0];
            if (actualTypeArgument instanceof Class) {
                List<Object> array = new ArrayList<>();
                parseArray((Class) actualTypeArgument, (Collection) array);
                return array;
            } else if (actualTypeArgument instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) actualTypeArgument;
                Type upperBoundType = wildcardType.getUpperBounds()[0];
                if (!Object.class.equals(upperBoundType)) {
                    List<Object> array2 = new ArrayList<>();
                    parseArray((Class) upperBoundType, (Collection) array2);
                    return array2;
                } else if (wildcardType.getLowerBounds().length == 0) {
                    return parse();
                } else {
                    throw new JSONException("not support type : " + collectionType);
                }
            } else {
                if (actualTypeArgument instanceof TypeVariable) {
                    TypeVariable<?> typeVariable = (TypeVariable) actualTypeArgument;
                    Type[] bounds = typeVariable.getBounds();
                    if (bounds.length == 1) {
                        Type boundType = bounds[0];
                        if (boundType instanceof Class) {
                            List<Object> array3 = new ArrayList<>();
                            parseArray((Class) boundType, (Collection) array3);
                            return array3;
                        }
                    } else {
                        throw new JSONException("not support : " + typeVariable);
                    }
                }
                if (actualTypeArgument instanceof ParameterizedType) {
                    List<Object> array4 = new ArrayList<>();
                    parseArray((ParameterizedType) actualTypeArgument, array4);
                    return array4;
                }
                throw new JSONException("TODO : " + collectionType);
            }
        } else {
            throw new JSONException("not support type " + collectionType);
        }
    }

    public void acceptType(String typeName) {
        JSONLexer lexer2 = this.lexer;
        lexer2.nextTokenWithColon();
        if (lexer2.token() != 4) {
            throw new JSONException("type not match error");
        } else if (typeName.equals(lexer2.stringVal())) {
            lexer2.nextToken();
            if (lexer2.token() == 16) {
                lexer2.nextToken();
            }
        } else {
            throw new JSONException("type not match error");
        }
    }

    public int getResolveStatus() {
        return this.resolveStatus;
    }

    public void setResolveStatus(int resolveStatus2) {
        this.resolveStatus = resolveStatus2;
    }

    public Object getObject(String path) {
        for (int i = 0; i < this.contextArrayIndex; i++) {
            if (path.equals(this.contextArray[i].getPath())) {
                return this.contextArray[i].getObject();
            }
        }
        return null;
    }

    public void checkListResolve(Collection array) {
        if (this.resolveStatus != 1) {
            return;
        }
        if (array instanceof List) {
            ResolveTask task = getLastResolveTask();
            task.setFieldDeserializer(new ListResolveFieldDeserializer(this, (List) array, array.size() - 1));
            task.setOwnerContext(this.context);
            setResolveStatus(0);
            return;
        }
        ResolveTask task2 = getLastResolveTask();
        task2.setFieldDeserializer(new CollectionResolveFieldDeserializer(this, array));
        task2.setOwnerContext(this.context);
        setResolveStatus(0);
    }

    public void checkMapResolve(Map object, String fieldName) {
        if (this.resolveStatus == 1) {
            MapResolveFieldDeserializer fieldResolver = new MapResolveFieldDeserializer(object, fieldName);
            ResolveTask task = getLastResolveTask();
            task.setFieldDeserializer(fieldResolver);
            task.setOwnerContext(this.context);
            setResolveStatus(0);
        }
    }

    public Object parseObject(Map object) {
        return parseObject(object, null);
    }

    public JSONObject parseObject() {
        JSONObject object = new JSONObject();
        parseObject((Map) object);
        return object;
    }

    public final void parseArray(Collection array) {
        parseArray(array, (Object) null);
    }

    /* JADX DEBUG: Multi-variable search result rejected for r5v4, resolved type: com.alibaba.fastjson.parser.JSONScanner */
    /* JADX WARN: Multi-variable type inference failed */
    public final void parseArray(Collection array, Object fieldName) {
        Object value;
        JSONObject jSONObject;
        JSONLexer lexer2 = getLexer();
        if (lexer2.token() == 21 || lexer2.token() == 22) {
            lexer2.nextToken();
        }
        if (lexer2.token() == 14) {
            lexer2.nextToken(4);
            ParseContext context2 = getContext();
            setContext(array, fieldName);
            String stringLiteral = null;
            int i = 0;
            JSONObject jSONObject2 = null;
            Collection items = null;
            while (true) {
                try {
                    if (isEnabled(Feature.AllowArbitraryCommas)) {
                        while (lexer2.token() == 16) {
                            lexer2.nextToken();
                        }
                    }
                    int i2 = lexer2.token();
                    if (i2 == 2) {
                        value = lexer2.integerValue();
                        lexer2.nextToken(16);
                        jSONObject = jSONObject2;
                    } else if (i2 == 3) {
                        if (lexer2.isEnabled(Feature.UseBigDecimal)) {
                            value = lexer2.decimalValue(true);
                        } else {
                            value = lexer2.decimalValue(false);
                        }
                        lexer2.nextToken(16);
                        jSONObject = jSONObject2;
                    } else if (i2 == 4) {
                        stringLiteral = lexer2.stringVal();
                        lexer2.nextToken(16);
                        if (lexer2.isEnabled(Feature.AllowISO8601DateFormat)) {
                            JSONScanner iso8601Lexer = new JSONScanner(stringLiteral);
                            if (iso8601Lexer.scanISO8601DateIfMatch()) {
                                value = iso8601Lexer.getCalendar().getTime();
                            } else {
                                value = stringLiteral;
                            }
                            iso8601Lexer.close();
                            jSONObject = iso8601Lexer;
                        } else {
                            value = stringLiteral;
                            jSONObject = jSONObject2;
                        }
                    } else if (i2 == 6) {
                        value = Boolean.TRUE;
                        lexer2.nextToken(16);
                        jSONObject = jSONObject2;
                    } else if (i2 == 7) {
                        value = Boolean.FALSE;
                        lexer2.nextToken(16);
                        jSONObject = jSONObject2;
                    } else if (i2 == 8) {
                        value = null;
                        lexer2.nextToken(4);
                        jSONObject = jSONObject2;
                    } else if (i2 == 12) {
                        JSONObject object = new JSONObject();
                        value = parseObject(object, Integer.valueOf(i));
                        stringLiteral = stringLiteral;
                        jSONObject = object;
                    } else if (i2 == 20) {
                        throw new JSONException("unclosed jsonArray");
                    } else if (i2 == 23) {
                        value = null;
                        lexer2.nextToken(4);
                        jSONObject = jSONObject2;
                    } else if (i2 == 14) {
                        items = new JSONArray();
                        parseArray(items, Integer.valueOf(i));
                        value = items;
                        jSONObject = jSONObject2;
                    } else if (i2 != 15) {
                        value = parse();
                        jSONObject = jSONObject2;
                    } else {
                        lexer2.nextToken(16);
                        return;
                    }
                    array.add(value);
                    checkListResolve(array);
                    if (lexer2.token() == 16) {
                        lexer2.nextToken(4);
                    }
                    i++;
                    jSONObject2 = jSONObject;
                } finally {
                    setContext(context2);
                }
            }
        } else {
            throw new JSONException("syntax error, expect [, actual " + JSONToken.name(lexer2.token()) + ", pos " + lexer2.pos());
        }
    }

    public ParseContext getContext() {
        return this.context;
    }

    public List<ResolveTask> getResolveTaskList() {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        return this.resolveTaskList;
    }

    public List<ResolveTask> getResolveTaskListDirect() {
        return this.resolveTaskList;
    }

    public void addResolveTask(ResolveTask task) {
        if (this.resolveTaskList == null) {
            this.resolveTaskList = new ArrayList(2);
        }
        this.resolveTaskList.add(task);
    }

    public ResolveTask getLastResolveTask() {
        List<ResolveTask> list = this.resolveTaskList;
        return list.get(list.size() - 1);
    }

    public List<ExtraProcessor> getExtraProcessors() {
        if (this.extraProcessors == null) {
            this.extraProcessors = new ArrayList(2);
        }
        return this.extraProcessors;
    }

    public List<ExtraProcessor> getExtraProcessorsDirect() {
        return this.extraProcessors;
    }

    public List<ExtraTypeProvider> getExtraTypeProviders() {
        if (this.extraTypeProviders == null) {
            this.extraTypeProviders = new ArrayList(2);
        }
        return this.extraTypeProviders;
    }

    public List<ExtraTypeProvider> getExtraTypeProvidersDirect() {
        return this.extraTypeProviders;
    }

    public void setContext(ParseContext context2) {
        if (!isEnabled(Feature.DisableCircularReferenceDetect)) {
            this.context = context2;
        }
    }

    public void popContext() {
        if (!isEnabled(Feature.DisableCircularReferenceDetect)) {
            this.context = this.context.getParentContext();
            ParseContext[] parseContextArr = this.contextArray;
            int i = this.contextArrayIndex;
            parseContextArr[i - 1] = null;
            this.contextArrayIndex = i - 1;
        }
    }

    public ParseContext setContext(Object object, Object fieldName) {
        if (isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        return setContext(this.context, object, fieldName);
    }

    public ParseContext setContext(ParseContext parent, Object object, Object fieldName) {
        if (isEnabled(Feature.DisableCircularReferenceDetect)) {
            return null;
        }
        this.context = new ParseContext(parent, object, fieldName);
        addContext(this.context);
        return this.context;
    }

    private void addContext(ParseContext context2) {
        int i = this.contextArrayIndex;
        this.contextArrayIndex = i + 1;
        ParseContext[] parseContextArr = this.contextArray;
        if (i >= parseContextArr.length) {
            ParseContext[] newArray = new ParseContext[((parseContextArr.length * 3) / 2)];
            System.arraycopy(parseContextArr, 0, newArray, 0, parseContextArr.length);
            this.contextArray = newArray;
        }
        this.contextArray[i] = context2;
    }

    public Object parse() {
        return parse(null);
    }

    public Object parseKey() {
        if (this.lexer.token() != 18) {
            return parse(null);
        }
        String value = this.lexer.stringVal();
        this.lexer.nextToken(16);
        return value;
    }

    public Object parse(Object fieldName) {
        JSONLexer lexer2 = getLexer();
        int i = lexer2.token();
        if (i == 2) {
            Number intValue = lexer2.integerValue();
            lexer2.nextToken();
            return intValue;
        } else if (i == 3) {
            Object value = lexer2.decimalValue(isEnabled(Feature.UseBigDecimal));
            lexer2.nextToken();
            return value;
        } else if (i == 4) {
            String stringLiteral = lexer2.stringVal();
            lexer2.nextToken(16);
            if (lexer2.isEnabled(Feature.AllowISO8601DateFormat)) {
                JSONScanner iso8601Lexer = new JSONScanner(stringLiteral);
                try {
                    if (iso8601Lexer.scanISO8601DateIfMatch()) {
                        return iso8601Lexer.getCalendar().getTime();
                    }
                    iso8601Lexer.close();
                } finally {
                    iso8601Lexer.close();
                }
            }
            return stringLiteral;
        } else if (i == 12) {
            return parseObject(new JSONObject(), fieldName);
        } else {
            if (i != 14) {
                switch (i) {
                    case 6:
                        lexer2.nextToken();
                        return Boolean.TRUE;
                    case 7:
                        lexer2.nextToken();
                        return Boolean.FALSE;
                    case 8:
                        lexer2.nextToken();
                        return null;
                    case 9:
                        lexer2.nextToken(18);
                        if (lexer2.token() == 18) {
                            lexer2.nextToken(10);
                            accept(10);
                            long time = lexer2.integerValue().longValue();
                            accept(2);
                            accept(11);
                            return new Date(time);
                        }
                        throw new JSONException("syntax error");
                    default:
                        switch (i) {
                            case 20:
                                if (lexer2.isBlankInput()) {
                                    return null;
                                }
                                throw new JSONException("unterminated json string, pos " + lexer2.getBufferPosition());
                            case 21:
                                lexer2.nextToken();
                                HashSet<Object> set = new HashSet<>();
                                parseArray(set, fieldName);
                                return set;
                            case 22:
                                lexer2.nextToken();
                                TreeSet<Object> treeSet = new TreeSet<>();
                                parseArray(treeSet, fieldName);
                                return treeSet;
                            case 23:
                                lexer2.nextToken();
                                return null;
                            default:
                                throw new JSONException("syntax error, pos " + lexer2.getBufferPosition());
                        }
                }
            } else {
                JSONArray array = new JSONArray();
                parseArray(array, fieldName);
                return array;
            }
        }
    }

    public void config(Feature feature, boolean state) {
        getLexer().config(feature, state);
    }

    public boolean isEnabled(Feature feature) {
        return getLexer().isEnabled(feature);
    }

    public JSONLexer getLexer() {
        return this.lexer;
    }

    public final void accept(int token) {
        JSONLexer lexer2 = getLexer();
        if (lexer2.token() == token) {
            lexer2.nextToken();
            return;
        }
        throw new JSONException("syntax error, expect " + JSONToken.name(token) + ", actual " + JSONToken.name(lexer2.token()));
    }

    public final void accept(int token, int nextExpectToken) {
        JSONLexer lexer2 = getLexer();
        if (lexer2.token() == token) {
            lexer2.nextToken(nextExpectToken);
            return;
        }
        throw new JSONException("syntax error, expect " + JSONToken.name(token) + ", actual " + JSONToken.name(lexer2.token()));
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        JSONLexer lexer2 = getLexer();
        try {
            if (isEnabled(Feature.AutoCloseSource)) {
                if (lexer2.token() != 20) {
                    throw new JSONException("not close json text, token : " + JSONToken.name(lexer2.token()));
                }
            }
        } finally {
            lexer2.close();
        }
    }

    public void handleResovleTask(Object value) {
        Object refValue;
        List<ResolveTask> list = this.resolveTaskList;
        if (list != null) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ResolveTask task = this.resolveTaskList.get(i);
                FieldDeserializer fieldDeser = task.getFieldDeserializer();
                if (fieldDeser != null) {
                    Object object = null;
                    if (task.getOwnerContext() != null) {
                        object = task.getOwnerContext().getObject();
                    }
                    String ref = task.getReferenceValue();
                    if (ref.startsWith("$")) {
                        refValue = getObject(ref);
                    } else {
                        refValue = task.getContext().getObject();
                    }
                    fieldDeser.setValue(object, refValue);
                }
            }
        }
    }

    public static class ResolveTask {
        private final ParseContext context;
        private FieldDeserializer fieldDeserializer;
        private ParseContext ownerContext;
        private final String referenceValue;

        public ResolveTask(ParseContext context2, String referenceValue2) {
            this.context = context2;
            this.referenceValue = referenceValue2;
        }

        public ParseContext getContext() {
            return this.context;
        }

        public String getReferenceValue() {
            return this.referenceValue;
        }

        public FieldDeserializer getFieldDeserializer() {
            return this.fieldDeserializer;
        }

        public void setFieldDeserializer(FieldDeserializer fieldDeserializer2) {
            this.fieldDeserializer = fieldDeserializer2;
        }

        public ParseContext getOwnerContext() {
            return this.ownerContext;
        }

        public void setOwnerContext(ParseContext ownerContext2) {
            this.ownerContext = ownerContext2;
        }
    }
}
