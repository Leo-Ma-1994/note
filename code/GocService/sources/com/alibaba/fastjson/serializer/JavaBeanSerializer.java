package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaBeanSerializer implements ObjectSerializer {
    private int features;
    private transient Map<String, FieldSerializer> getterMap;
    private final FieldSerializer[] getters;
    private final FieldSerializer[] sortedGetters;

    public FieldSerializer[] getGetters() {
        return this.getters;
    }

    public JavaBeanSerializer(Class<?> clazz) {
        this(clazz, (Map<String, String>) null);
    }

    public JavaBeanSerializer(Class<?> clazz, String... aliasList) {
        this(clazz, createAliasMap(aliasList));
    }

    static Map<String, String> createAliasMap(String... aliasList) {
        Map<String, String> aliasMap = new HashMap<>();
        for (String alias : aliasList) {
            aliasMap.put(alias, alias);
        }
        return aliasMap;
    }

    public JavaBeanSerializer(Class<?> clazz, Map<String, String> aliasMap) {
        this.features = 0;
        this.features = TypeUtils.getSerializeFeatures(clazz);
        List<FieldSerializer> getterList = new ArrayList<>();
        for (FieldInfo fieldInfo : TypeUtils.computeGetters(clazz, aliasMap, false)) {
            getterList.add(createFieldSerializer(fieldInfo));
        }
        this.getters = (FieldSerializer[]) getterList.toArray(new FieldSerializer[getterList.size()]);
        List<FieldSerializer> getterList2 = new ArrayList<>();
        for (FieldInfo fieldInfo2 : TypeUtils.computeGetters(clazz, aliasMap, true)) {
            getterList2.add(createFieldSerializer(fieldInfo2));
        }
        this.sortedGetters = (FieldSerializer[]) getterList2.toArray(new FieldSerializer[getterList2.size()]);
    }

    /* access modifiers changed from: protected */
    public boolean isWriteClassName(JSONSerializer serializer, Object obj, Type fieldType, Object fieldName) {
        return serializer.isWriteClassName(fieldType, obj);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:111:0x01a8, code lost:
        if (((java.lang.Boolean) r5).booleanValue() == false) goto L_0x01ab;
     */
    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void write(com.alibaba.fastjson.serializer.JSONSerializer r27, java.lang.Object r28, java.lang.Object r29, java.lang.reflect.Type r30, int r31) throws java.io.IOException {
        /*
        // Method dump skipped, instructions count: 562
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.serializer.JavaBeanSerializer.write(com.alibaba.fastjson.serializer.JSONSerializer, java.lang.Object, java.lang.Object, java.lang.reflect.Type, int):void");
    }

    public boolean writeReference(JSONSerializer serializer, Object object, int fieldFeatures) {
        SerialContext context = serializer.getContext();
        if ((context != null && SerializerFeature.isEnabled(context.getFeatures(), fieldFeatures, SerializerFeature.DisableCircularReferenceDetect)) || !serializer.containsReference(object)) {
            return false;
        }
        serializer.writeReference(object);
        return true;
    }

    public FieldSerializer createFieldSerializer(FieldInfo fieldInfo) {
        if (fieldInfo.getFieldClass() == Number.class) {
            return new NumberFieldSerializer(fieldInfo);
        }
        return new ObjectFieldSerializer(fieldInfo);
    }

    public boolean isWriteAsArray(JSONSerializer serializer) {
        if (!SerializerFeature.isEnabled(this.features, SerializerFeature.BeanToArray) && !serializer.isEnabled(SerializerFeature.BeanToArray)) {
            return false;
        }
        return true;
    }

    public Map<String, FieldSerializer> getGetterMap() {
        if (this.getterMap == null) {
            HashMap<String, FieldSerializer> map = new HashMap<>(this.getters.length);
            FieldSerializer[] fieldSerializerArr = this.sortedGetters;
            for (FieldSerializer getter : fieldSerializerArr) {
                map.put(getter.getName(), getter);
            }
            this.getterMap = map;
        }
        return this.getterMap;
    }

    public Object getFieldValue(Object object, String name) throws Exception {
        FieldSerializer getter = getGetterMap().get(name);
        if (getter == null) {
            return null;
        }
        return getter.getPropertyValue(object);
    }

    public List<Object> getFieldValues(Object object) throws Exception {
        List<Object> fieldValues = new ArrayList<>(this.sortedGetters.length);
        for (FieldSerializer getter : this.sortedGetters) {
            fieldValues.add(getter.getPropertyValue(object));
        }
        return fieldValues;
    }
}
