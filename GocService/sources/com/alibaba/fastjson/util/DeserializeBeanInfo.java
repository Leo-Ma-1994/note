package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.annotation.JSONCreator;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DeserializeBeanInfo {
    private final Class<?> clazz;
    private Constructor<?> creatorConstructor;
    private Constructor<?> defaultConstructor;
    private Method factoryMethod;
    private final List<FieldInfo> fieldList = new ArrayList();
    private int parserFeatures = 0;
    private final List<FieldInfo> sortedFieldList = new ArrayList();

    public DeserializeBeanInfo(Class<?> clazz2) {
        this.clazz = clazz2;
        this.parserFeatures = TypeUtils.getParserFeatures(clazz2);
    }

    public Constructor<?> getDefaultConstructor() {
        return this.defaultConstructor;
    }

    public void setDefaultConstructor(Constructor<?> defaultConstructor2) {
        this.defaultConstructor = defaultConstructor2;
    }

    public Constructor<?> getCreatorConstructor() {
        return this.creatorConstructor;
    }

    public void setCreatorConstructor(Constructor<?> createConstructor) {
        this.creatorConstructor = createConstructor;
    }

    public Method getFactoryMethod() {
        return this.factoryMethod;
    }

    public void setFactoryMethod(Method factoryMethod2) {
        this.factoryMethod = factoryMethod2;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }

    public List<FieldInfo> getFieldList() {
        return this.fieldList;
    }

    public List<FieldInfo> getSortedFieldList() {
        return this.sortedFieldList;
    }

    public FieldInfo getField(String propertyName) {
        for (FieldInfo item : this.fieldList) {
            if (item.getName().equals(propertyName)) {
                return item;
            }
        }
        return null;
    }

    public boolean add(FieldInfo field) {
        for (FieldInfo item : this.fieldList) {
            if (item.getName().equals(field.getName()) && (!item.isGetOnly() || field.isGetOnly())) {
                return false;
            }
        }
        this.fieldList.add(field);
        this.sortedFieldList.add(field);
        Collections.sort(this.sortedFieldList);
        return true;
    }

    /* JADX WARNING: Removed duplicated region for block: B:101:0x028b  */
    /* JADX WARNING: Removed duplicated region for block: B:108:0x02d8  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.alibaba.fastjson.util.DeserializeBeanInfo computeSetters(java.lang.Class<?> r25, java.lang.reflect.Type r26) {
        /*
        // Method dump skipped, instructions count: 1124
        */
        throw new UnsupportedOperationException("Method not decompiled: com.alibaba.fastjson.util.DeserializeBeanInfo.computeSetters(java.lang.Class, java.lang.reflect.Type):com.alibaba.fastjson.util.DeserializeBeanInfo");
    }

    public static Constructor<?> getDefaultConstructor(Class<?> clazz2) {
        if (Modifier.isAbstract(clazz2.getModifiers())) {
            return null;
        }
        Constructor<?> defaultConstructor2 = null;
        Constructor<?>[] declaredConstructors = clazz2.getDeclaredConstructors();
        int length = declaredConstructors.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                break;
            }
            Constructor<?> constructor = declaredConstructors[i];
            if (constructor.getParameterTypes().length == 0) {
                defaultConstructor2 = constructor;
                break;
            }
            i++;
        }
        if (defaultConstructor2 != null || !clazz2.isMemberClass() || Modifier.isStatic(clazz2.getModifiers())) {
            return defaultConstructor2;
        }
        Constructor<?>[] declaredConstructors2 = clazz2.getDeclaredConstructors();
        for (Constructor<?> constructor2 : declaredConstructors2) {
            if (constructor2.getParameterTypes().length == 1 && constructor2.getParameterTypes()[0].equals(clazz2.getDeclaringClass())) {
                return constructor2;
            }
        }
        return defaultConstructor2;
    }

    public static Constructor<?> getCreatorConstructor(Class<?> clazz2) {
        Constructor<?>[] declaredConstructors = clazz2.getDeclaredConstructors();
        for (Constructor<?> constructor : declaredConstructors) {
            if (((JSONCreator) constructor.getAnnotation(JSONCreator.class)) != null) {
                if (0 == 0) {
                    return constructor;
                } else {
                    throw new JSONException("multi-json creator");
                }
            }
        }
        return null;
    }

    public static Method getFactoryMethod(Class<?> clazz2) {
        Method[] declaredMethods = clazz2.getDeclaredMethods();
        for (Method method : declaredMethods) {
            if (Modifier.isStatic(method.getModifiers()) && clazz2.isAssignableFrom(method.getReturnType()) && ((JSONCreator) method.getAnnotation(JSONCreator.class)) != null) {
                if (0 == 0) {
                    return method;
                } else {
                    throw new JSONException("multi-json creator");
                }
            }
        }
        return null;
    }

    public int getParserFeatures() {
        return this.parserFeatures;
    }
}
