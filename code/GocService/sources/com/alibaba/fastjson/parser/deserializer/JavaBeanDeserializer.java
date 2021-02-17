package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.FilterUtils;
import com.alibaba.fastjson.util.DeserializeBeanInfo;
import com.alibaba.fastjson.util.FieldInfo;
import com.goodocom.bttek.BuildConfig;
import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JavaBeanDeserializer implements ObjectDeserializer {
    private DeserializeBeanInfo beanInfo;
    private final Class<?> clazz;
    private final Map<String, FieldDeserializer> feildDeserializerMap;
    private final List<FieldDeserializer> fieldDeserializers;
    private final List<FieldDeserializer> sortedFieldDeserializers;

    public JavaBeanDeserializer(ParserConfig config, Class<?> clazz2) {
        this(config, clazz2, clazz2);
    }

    public JavaBeanDeserializer(ParserConfig config, Class<?> clazz2, Type type) {
        this.feildDeserializerMap = new IdentityHashMap();
        this.fieldDeserializers = new ArrayList();
        this.sortedFieldDeserializers = new ArrayList();
        this.clazz = clazz2;
        this.beanInfo = DeserializeBeanInfo.computeSetters(clazz2, type);
        for (FieldInfo fieldInfo : this.beanInfo.getFieldList()) {
            addFieldDeserializer(config, clazz2, fieldInfo);
        }
        for (FieldInfo fieldInfo2 : this.beanInfo.getSortedFieldList()) {
            this.sortedFieldDeserializers.add(this.feildDeserializerMap.get(fieldInfo2.getName().intern()));
        }
    }

    public Map<String, FieldDeserializer> getFieldDeserializerMap() {
        return this.feildDeserializerMap;
    }

    public FieldDeserializer getFieldDeserializer(String name) {
        FieldDeserializer feildDeser = this.feildDeserializerMap.get(name);
        if (feildDeser != null) {
            return feildDeser;
        }
        for (Map.Entry<String, FieldDeserializer> entry : this.feildDeserializerMap.entrySet()) {
            if (name.equals(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    private void addFieldDeserializer(ParserConfig mapping, Class<?> clazz2, FieldInfo fieldInfo) {
        String interName = fieldInfo.getName().intern();
        FieldDeserializer fieldDeserializer = createFieldDeserializer(mapping, clazz2, fieldInfo);
        this.feildDeserializerMap.put(interName, fieldDeserializer);
        this.fieldDeserializers.add(fieldDeserializer);
    }

    public FieldDeserializer createFieldDeserializer(ParserConfig mapping, Class<?> clazz2, FieldInfo fieldInfo) {
        return mapping.createFieldDeserializer(mapping, clazz2, fieldInfo);
    }

    public Object createInstance(DefaultJSONParser parser, Type type) {
        Object object;
        if ((type instanceof Class) && this.clazz.isInterface()) {
            return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{(Class) type}, new JSONObject());
        }
        if (this.beanInfo.getDefaultConstructor() == null) {
            return null;
        }
        try {
            Constructor<?> constructor = this.beanInfo.getDefaultConstructor();
            if (constructor.getParameterTypes().length == 0) {
                object = constructor.newInstance(new Object[0]);
            } else {
                object = constructor.newInstance(parser.getContext().getObject());
            }
            if (parser.isEnabled(Feature.InitStringFieldAsEmpty)) {
                for (FieldInfo fieldInfo : this.beanInfo.getFieldList()) {
                    if (fieldInfo.getFieldClass() == String.class) {
                        try {
                            fieldInfo.set(object, BuildConfig.FLAVOR);
                        } catch (Exception e) {
                            throw new JSONException("create instance error, class " + this.clazz.getName(), e);
                        }
                    }
                }
            }
            return object;
        } catch (Exception e2) {
            throw new JSONException("create instance error, class " + this.clazz.getName(), e2);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName) {
        return (T) deserialze(parser, type, fieldName, null);
    }

    public <T> T deserialzeArrayMapping(DefaultJSONParser parser, Type type, Object fieldName, Object object) {
        JSONLexer lexer = parser.getLexer();
        if (lexer.token() == 14) {
            T t = (T) createInstance(parser, type);
            int size = this.sortedFieldDeserializers.size();
            int i = 0;
            while (i < size) {
                char seperator = i == size + -1 ? ']' : ',';
                FieldDeserializer fieldDeser = this.sortedFieldDeserializers.get(i);
                Class<?> fieldClass = fieldDeser.getFieldClass();
                if (fieldClass == Integer.TYPE) {
                    fieldDeser.setValue((Object) t, lexer.scanInt(seperator));
                } else if (fieldClass == String.class) {
                    fieldDeser.setValue((Object) t, lexer.scanString(seperator));
                } else if (fieldClass == Long.TYPE) {
                    fieldDeser.setValue(t, lexer.scanLong(seperator));
                } else if (fieldClass.isEnum()) {
                    fieldDeser.setValue(t, lexer.scanEnum(fieldClass, parser.getSymbolTable(), seperator));
                } else {
                    lexer.nextToken(14);
                    fieldDeser.setValue(t, parser.parseObject(fieldDeser.getFieldType()));
                    if (seperator == ']') {
                        if (lexer.token() == 15) {
                            lexer.nextToken(16);
                        } else {
                            throw new JSONException("syntax error");
                        }
                    } else if (seperator == ',' && lexer.token() != 16) {
                        throw new JSONException("syntax error");
                    }
                }
                i++;
            }
            lexer.nextToken(16);
            return t;
        }
        throw new JSONException("error");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:106:0x01d2, code lost:
        r4 = com.alibaba.fastjson.util.TypeUtils.loadClass(r3);
        r12 = (T) r20.getConfig().getDeserializer(r4).deserialze(r20, r4, r22);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:107:0x01e2, code lost:
        if (r2 == null) goto L_0x01e7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:108:0x01e4, code lost:
        r2.setObject(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:109:0x01e7, code lost:
        r20.setContext(r14);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:110:0x01ea, code lost:
        return r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:133:0x0246, code lost:
        r1 = r0;
        r2 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:181:0x033f, code lost:
        throw new com.alibaba.fastjson.JSONException("syntax error, unexpect token " + com.alibaba.fastjson.parser.JSONToken.name(r11.token()));
     */
    /* JADX WARNING: Removed duplicated region for block: B:172:0x0304  */
    /* JADX WARNING: Removed duplicated region for block: B:187:0x034b  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public <T> T deserialze(com.alibaba.fastjson.parser.DefaultJSONParser r20, java.lang.reflect.Type r21, java.lang.Object r22, java.lang.Object r23) {
        /*
        // Method dump skipped, instructions count: 855
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer.deserialze(com.alibaba.fastjson.parser.DefaultJSONParser, java.lang.reflect.Type, java.lang.Object, java.lang.Object):java.lang.Object");
    }

    public boolean parseField(DefaultJSONParser parser, String key, Object object, Type objectType, Map<String, Object> fieldValues) {
        JSONLexer lexer = parser.getLexer();
        FieldDeserializer fieldDeserializer = this.feildDeserializerMap.get(key);
        if (fieldDeserializer == null) {
            Iterator<Map.Entry<String, FieldDeserializer>> it = this.feildDeserializerMap.entrySet().iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                Map.Entry<String, FieldDeserializer> entry = it.next();
                if (entry.getKey().equalsIgnoreCase(key)) {
                    fieldDeserializer = entry.getValue();
                    break;
                }
            }
        }
        if (fieldDeserializer == null) {
            parseExtra(parser, object, key);
            return false;
        }
        lexer.nextTokenWithColon(fieldDeserializer.getFastMatchToken());
        fieldDeserializer.parseField(parser, object, objectType, fieldValues);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void parseExtra(DefaultJSONParser parser, Object object, String key) {
        Object value;
        JSONLexer lexer = parser.getLexer();
        if (lexer.isEnabled(Feature.IgnoreNotMatch)) {
            lexer.nextTokenWithColon();
            Type type = FilterUtils.getExtratype(parser, object, key);
            if (type == null) {
                value = parser.parse();
            } else {
                value = parser.parseObject(type);
            }
            FilterUtils.processExtra(parser, object, key, value);
            return;
        }
        throw new JSONException("setter not found, class " + this.clazz.getName() + ", property " + key);
    }

    @Override // com.alibaba.fastjson.parser.deserializer.ObjectDeserializer
    public int getFastMatchToken() {
        return 12;
    }

    public List<FieldDeserializer> getSortedFieldDeserializers() {
        return this.sortedFieldDeserializers;
    }

    public final boolean isSupportArrayToBean(JSONLexer lexer) {
        return Feature.isEnabled(this.beanInfo.getParserFeatures(), Feature.SupportArrayToBean) || lexer.isEnabled(Feature.SupportArrayToBean);
    }
}
