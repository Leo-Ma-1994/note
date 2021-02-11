package com.alibaba.fastjson.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.serializer.SerializerFeature;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.AccessControlException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class TypeUtils {
    public static boolean compatibleWithJavaBean;
    private static ConcurrentMap<String, Class<?>> mappings = new ConcurrentHashMap();
    private static boolean setAccessibleEnable = true;

    static {
        compatibleWithJavaBean = false;
        try {
            String prop = System.getProperty("fastjson.compatibleWithJavaBean");
            if ("true".equals(prop)) {
                compatibleWithJavaBean = true;
            } else if ("false".equals(prop)) {
                compatibleWithJavaBean = false;
            }
        } catch (Throwable th) {
        }
        addBaseClassMappings();
    }

    public static final String castToString(Object value) {
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    public static final Byte castToByte(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Byte.valueOf(((Number) value).byteValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() != 0 && !"null".equals(strVal) && !"NULL".equals(strVal)) {
                return Byte.valueOf(Byte.parseByte(strVal));
            }
            return null;
        }
        throw new JSONException("can not cast to byte, value : " + value);
    }

    public static final Character castToChar(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Character) {
            return (Character) value;
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }
            if (strVal.length() == 1) {
                return Character.valueOf(strVal.charAt(0));
            }
            throw new JSONException("can not cast to byte, value : " + value);
        }
        throw new JSONException("can not cast to byte, value : " + value);
    }

    public static final Short castToShort(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Short.valueOf(((Number) value).shortValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() != 0 && !"null".equals(strVal) && !"NULL".equals(strVal)) {
                return Short.valueOf(Short.parseShort(strVal));
            }
            return null;
        }
        throw new JSONException("can not cast to short, value : " + value);
    }

    public static final BigDecimal castToBigDecimal(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigDecimal) {
            return (BigDecimal) value;
        }
        if (value instanceof BigInteger) {
            return new BigDecimal((BigInteger) value);
        }
        String strVal = value.toString();
        if (strVal.length() == 0) {
            return null;
        }
        return new BigDecimal(strVal);
    }

    public static final BigInteger castToBigInteger(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }
        if ((value instanceof Float) || (value instanceof Double)) {
            return BigInteger.valueOf(((Number) value).longValue());
        }
        String strVal = value.toString();
        if (strVal.length() == 0) {
            return null;
        }
        return new BigInteger(strVal);
    }

    public static final Float castToFloat(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Float.valueOf(((Number) value).floatValue());
        }
        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() != 0 && !"null".equals(strVal) && !"NULL".equals(strVal)) {
                return Float.valueOf(Float.parseFloat(strVal));
            }
            return null;
        }
        throw new JSONException("can not cast to float, value : " + value);
    }

    public static final Double castToDouble(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Double.valueOf(((Number) value).doubleValue());
        }
        if (value instanceof String) {
            String strVal = value.toString();
            if (strVal.length() != 0 && !"null".equals(strVal) && !"NULL".equals(strVal)) {
                return Double.valueOf(Double.parseDouble(strVal));
            }
            return null;
        }
        throw new JSONException("can not cast to double, value : " + value);
    }

    public static final Date castToDate(Object value) {
        String format;
        if (value == null) {
            return null;
        }
        if (value instanceof Calendar) {
            return ((Calendar) value).getTime();
        }
        if (value instanceof Date) {
            return (Date) value;
        }
        long longValue = -1;
        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.indexOf(45) != -1) {
                if (strVal.length() == JSON.DEFFAULT_DATE_FORMAT.length()) {
                    format = JSON.DEFFAULT_DATE_FORMAT;
                } else if (strVal.length() == 10) {
                    format = "yyyy-MM-dd";
                } else if (strVal.length() == "yyyy-MM-dd HH:mm:ss".length()) {
                    format = "yyyy-MM-dd HH:mm:ss";
                } else {
                    format = "yyyy-MM-dd HH:mm:ss.SSS";
                }
                try {
                    return new SimpleDateFormat(format).parse(strVal);
                } catch (ParseException e) {
                    throw new JSONException("can not cast to Date, value : " + strVal);
                }
            } else if (strVal.length() == 0) {
                return null;
            } else {
                longValue = Long.parseLong(strVal);
            }
        }
        if (longValue >= 0) {
            return new Date(longValue);
        }
        throw new JSONException("can not cast to Date, value : " + value);
    }

    public static final java.sql.Date castToSqlDate(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Calendar) {
            return new java.sql.Date(((Calendar) value).getTimeInMillis());
        }
        if (value instanceof java.sql.Date) {
            return (java.sql.Date) value;
        }
        if (value instanceof Date) {
            return new java.sql.Date(((Date) value).getTime());
        }
        long longValue = 0;
        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }
            longValue = Long.parseLong(strVal);
        }
        if (longValue > 0) {
            return new java.sql.Date(longValue);
        }
        throw new JSONException("can not cast to Date, value : " + value);
    }

    public static final Timestamp castToTimestamp(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Calendar) {
            return new Timestamp(((Calendar) value).getTimeInMillis());
        }
        if (value instanceof Timestamp) {
            return (Timestamp) value;
        }
        if (value instanceof Date) {
            return new Timestamp(((Date) value).getTime());
        }
        long longValue = 0;
        if (value instanceof Number) {
            longValue = ((Number) value).longValue();
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }
            longValue = Long.parseLong(strVal);
        }
        if (longValue > 0) {
            return new Timestamp(longValue);
        }
        throw new JSONException("can not cast to Date, value : " + value);
    }

    public static final Long castToLong(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Number) {
            return Long.valueOf(((Number) value).longValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0 || "null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
            try {
                return Long.valueOf(Long.parseLong(strVal));
            } catch (NumberFormatException e) {
                JSONScanner dateParser = new JSONScanner(strVal);
                Calendar calendar = null;
                if (dateParser.scanISO8601DateIfMatch(false)) {
                    calendar = dateParser.getCalendar();
                }
                dateParser.close();
                if (calendar != null) {
                    return Long.valueOf(calendar.getTimeInMillis());
                }
            }
        }
        throw new JSONException("can not cast to long, value : " + value);
    }

    public static final Integer castToInt(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Integer) {
            return (Integer) value;
        }
        if (value instanceof Number) {
            return Integer.valueOf(((Number) value).intValue());
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() != 0 && !"null".equals(strVal) && !"null".equals(strVal) && !"NULL".equals(strVal)) {
                return Integer.valueOf(Integer.parseInt(strVal));
            }
            return null;
        }
        throw new JSONException("can not cast to int, value : " + value);
    }

    public static final byte[] castToBytes(Object value) {
        if (value instanceof byte[]) {
            return (byte[]) value;
        }
        if (value instanceof String) {
            return Base64.decodeFast((String) value);
        }
        throw new JSONException("can not cast to int, value : " + value);
    }

    public static final Boolean castToBoolean(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof Number) {
            boolean z = true;
            if (((Number) value).intValue() != 1) {
                z = false;
            }
            return Boolean.valueOf(z);
        }
        if (value instanceof String) {
            String strVal = (String) value;
            if (strVal.length() == 0) {
                return null;
            }
            if ("true".equalsIgnoreCase(strVal)) {
                return Boolean.TRUE;
            }
            if ("false".equalsIgnoreCase(strVal)) {
                return Boolean.FALSE;
            }
            if ("1".equals(strVal)) {
                return Boolean.TRUE;
            }
            if ("0".equals(strVal)) {
                return Boolean.FALSE;
            }
            if ("null".equals(strVal) || "NULL".equals(strVal)) {
                return null;
            }
        }
        throw new JSONException("can not cast to boolean, value : " + value);
    }

    public static final <T> T castToJavaBean(Object obj, Class<T> clazz) {
        return (T) cast(obj, (Class<Object>) clazz, ParserConfig.getGlobalInstance());
    }

    /* JADX DEBUG: Multi-variable search result rejected for r6v0, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> T cast(Object obj, Class<T> clazz, ParserConfig mapping) {
        Calendar calendar;
        if (obj == 0) {
            return null;
        }
        if (clazz == null) {
            throw new IllegalArgumentException("clazz is null");
        } else if (clazz == obj.getClass()) {
            return obj;
        } else {
            if (!(obj instanceof Map)) {
                if (clazz.isArray()) {
                    if (obj instanceof Collection) {
                        Collection<Object> collection = (Collection) obj;
                        int index = 0;
                        T t = (T) Array.newInstance(clazz.getComponentType(), collection.size());
                        for (Object item : collection) {
                            Array.set(t, index, cast(item, (Class<Object>) clazz.getComponentType(), mapping));
                            index++;
                        }
                        return t;
                    } else if (clazz == byte[].class) {
                        return (T) castToBytes(obj);
                    }
                }
                if (clazz.isAssignableFrom(obj.getClass())) {
                    return obj;
                }
                if (clazz == Boolean.TYPE || clazz == Boolean.class) {
                    return (T) castToBoolean(obj);
                }
                if (clazz == Byte.TYPE || clazz == Byte.class) {
                    return (T) castToByte(obj);
                }
                if (clazz == Short.TYPE || clazz == Short.class) {
                    return (T) castToShort(obj);
                }
                if (clazz == Integer.TYPE || clazz == Integer.class) {
                    return (T) castToInt(obj);
                }
                if (clazz == Long.TYPE || clazz == Long.class) {
                    return (T) castToLong(obj);
                }
                if (clazz == Float.TYPE || clazz == Float.class) {
                    return (T) castToFloat(obj);
                }
                if (clazz == Double.TYPE || clazz == Double.class) {
                    return (T) castToDouble(obj);
                }
                if (clazz == String.class) {
                    return (T) castToString(obj);
                }
                if (clazz == BigDecimal.class) {
                    return (T) castToBigDecimal(obj);
                }
                if (clazz == BigInteger.class) {
                    return (T) castToBigInteger(obj);
                }
                if (clazz == Date.class) {
                    return (T) castToDate(obj);
                }
                if (clazz == java.sql.Date.class) {
                    return (T) castToSqlDate(obj);
                }
                if (clazz == Timestamp.class) {
                    return (T) castToTimestamp(obj);
                }
                if (clazz.isEnum()) {
                    return (T) castToEnum(obj, clazz, mapping);
                }
                if (Calendar.class.isAssignableFrom(clazz)) {
                    Date date = castToDate(obj);
                    if (clazz == Calendar.class) {
                        calendar = (T) Calendar.getInstance();
                    } else {
                        try {
                            calendar = (T) clazz.newInstance();
                        } catch (Exception e) {
                            throw new JSONException("can not cast to : " + clazz.getName(), e);
                        }
                    }
                    calendar.setTime(date);
                    return (T) calendar;
                } else if ((obj instanceof String) && ((String) obj).length() == 0) {
                    return null;
                } else {
                    throw new JSONException("can not cast to : " + clazz.getName());
                }
            } else if (clazz == Map.class) {
                return obj;
            } else {
                return (clazz != Object.class || ((Map) obj).containsKey(JSON.DEFAULT_TYPE_KEY)) ? (T) castToJavaBean((Map) obj, clazz, mapping) : obj;
            }
        }
    }

    public static final <T> T castToEnum(Object obj, Class<T> clazz, ParserConfig mapping) {
        try {
            if (obj instanceof String) {
                String name = (String) obj;
                if (name.length() == 0) {
                    return null;
                }
                return (T) Enum.valueOf(clazz, name);
            }
            if (obj instanceof Number) {
                int ordinal = ((Number) obj).intValue();
                for (Object value : (Object[]) clazz.getMethod("values", new Class[0]).invoke(null, new Object[0])) {
                    T t = (T) ((Enum) value);
                    if (t.ordinal() == ordinal) {
                        return t;
                    }
                }
            }
            throw new JSONException("can not cast to : " + clazz.getName());
        } catch (Exception ex) {
            throw new JSONException("can not cast to : " + clazz.getName(), ex);
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for r3v0, resolved type: java.lang.Object */
    /* JADX WARN: Multi-variable type inference failed */
    public static final <T> T cast(Object obj, Type type, ParserConfig mapping) {
        if (obj == 0) {
            return null;
        }
        if (type instanceof Class) {
            return (T) cast(obj, (Class<Object>) ((Class) type), mapping);
        }
        if (type instanceof ParameterizedType) {
            return (T) cast(obj, (ParameterizedType) type, mapping);
        }
        if ((obj instanceof String) && ((String) obj).length() == 0) {
            return null;
        }
        if (type instanceof TypeVariable) {
            return obj;
        }
        throw new JSONException("can not cast to : " + type);
    }

    public static final <T> T cast(Object obj, ParameterizedType type, ParserConfig mapping) {
        Type rawTye = type.getRawType();
        if (rawTye == Set.class || rawTye == HashSet.class || rawTye == TreeSet.class || rawTye == List.class || rawTye == ArrayList.class) {
            Type itemType = type.getActualTypeArguments()[0];
            if (obj instanceof Iterable) {
                Collection collection = (rawTye == Set.class || rawTye == HashSet.class) ? (T) new HashSet() : rawTye == TreeSet.class ? (T) new TreeSet() : (T) new ArrayList();
                for (Object item : (Iterable) obj) {
                    collection.add(cast(item, itemType, mapping));
                }
                return (T) collection;
            }
        }
        if (rawTye == Map.class || rawTye == HashMap.class) {
            Type keyType = type.getActualTypeArguments()[0];
            Type valueType = type.getActualTypeArguments()[1];
            if (obj instanceof Map) {
                T t = (T) new HashMap();
                for (Map.Entry entry : ((Map) obj).entrySet()) {
                    t.put(cast(entry.getKey(), keyType, mapping), cast(entry.getValue(), valueType, mapping));
                }
                return t;
            }
        }
        if ((obj instanceof String) && ((String) obj).length() == 0) {
            return null;
        }
        if (type.getActualTypeArguments().length == 1 && (type.getActualTypeArguments()[0] instanceof WildcardType)) {
            return (T) cast(obj, rawTye, mapping);
        }
        throw new JSONException("can not cast to : " + type);
    }

    public static final <T> T castToJavaBean(Map<String, Object> map, Class<T> clazz, ParserConfig mapping) {
        Exception e;
        JSONObject object;
        int lineNumber;
        ParserConfig mapping2 = mapping;
        if (clazz == StackTraceElement.class) {
            try {
                String declaringClass = (String) map.get("className");
                String methodName = (String) map.get("methodName");
                String fileName = (String) map.get("fileName");
                Number value = (Number) map.get("lineNumber");
                if (value == null) {
                    lineNumber = 0;
                } else {
                    lineNumber = value.intValue();
                }
                return (T) new StackTraceElement(declaringClass, methodName, fileName, lineNumber);
            } catch (Exception e2) {
                e = e2;
                throw new JSONException(e.getMessage(), e);
            }
        } else {
            Object iClassObject = map.get(JSON.DEFAULT_TYPE_KEY);
            if (iClassObject instanceof String) {
                String className = (String) iClassObject;
                Class<?> loadClazz = loadClass(className);
                if (loadClazz == null) {
                    throw new ClassNotFoundException(className + " not found");
                } else if (!loadClazz.equals(clazz)) {
                    return (T) castToJavaBean(map, loadClazz, mapping2);
                }
            }
            if (clazz.isInterface()) {
                if (map instanceof JSONObject) {
                    object = (JSONObject) map;
                } else {
                    object = new JSONObject(map);
                }
                return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[]{clazz}, object);
            }
            if (mapping2 == null) {
                mapping2 = ParserConfig.getGlobalInstance();
            }
            try {
                Map<String, FieldDeserializer> setters = mapping2.getFieldDeserializers(clazz);
                Constructor<T> constructor = clazz.getDeclaredConstructor(new Class[0]);
                if (!constructor.isAccessible()) {
                    constructor.setAccessible(true);
                }
                T object2 = constructor.newInstance(new Object[0]);
                for (Map.Entry<String, FieldDeserializer> entry : setters.entrySet()) {
                    String key = entry.getKey();
                    FieldDeserializer fieldDeser = entry.getValue();
                    if (map.containsKey(key)) {
                        Object value2 = map.get(key);
                        Method method = fieldDeser.getMethod();
                        if (method != null) {
                            method.invoke(object2, cast(value2, method.getGenericParameterTypes()[0], mapping2));
                        } else {
                            Field field = fieldDeser.getField();
                            field.set(object2, cast(value2, field.getGenericType(), mapping2));
                        }
                    }
                }
                return object2;
            } catch (Exception e3) {
                e = e3;
                throw new JSONException(e.getMessage(), e);
            }
        }
    }

    public static void addClassMapping(String className, Class<?> clazz) {
        if (className == null) {
            className = clazz.getName();
        }
        mappings.put(className, clazz);
    }

    public static void addBaseClassMappings() {
        mappings.put("byte", Byte.TYPE);
        mappings.put("short", Short.TYPE);
        mappings.put("int", Integer.TYPE);
        mappings.put("long", Long.TYPE);
        mappings.put("float", Float.TYPE);
        mappings.put("double", Double.TYPE);
        mappings.put("boolean", Boolean.TYPE);
        mappings.put("char", Character.TYPE);
        mappings.put("[byte", byte[].class);
        mappings.put("[short", short[].class);
        mappings.put("[int", int[].class);
        mappings.put("[long", long[].class);
        mappings.put("[float", float[].class);
        mappings.put("[double", double[].class);
        mappings.put("[boolean", boolean[].class);
        mappings.put("[char", char[].class);
        mappings.put(HashMap.class.getName(), HashMap.class);
    }

    public static void clearClassMapping() {
        mappings.clear();
        addBaseClassMappings();
    }

    public static Class<?> loadClass(String className) {
        if (className == null || className.length() == 0) {
            return null;
        }
        Class<?> clazz = mappings.get(className);
        if (clazz != null) {
            return clazz;
        }
        if (className.charAt(0) == '[') {
            return Array.newInstance(loadClass(className.substring(1)), 0).getClass();
        }
        if (className.startsWith("L") && className.endsWith(";")) {
            return loadClass(className.substring(1, className.length() - 1));
        }
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            if (classLoader != null) {
                Class<?> clazz2 = classLoader.loadClass(className);
                addClassMapping(className, clazz2);
                return clazz2;
            }
        } catch (Throwable th) {
        }
        try {
            clazz = Class.forName(className);
            addClassMapping(className, clazz);
            return clazz;
        } catch (Throwable th2) {
            return clazz;
        }
    }

    public static List<FieldInfo> computeGetters(Class<?> clazz, Map<String, String> aliasMap) {
        return computeGetters(clazz, aliasMap, true);
    }

    public static List<FieldInfo> computeGetters(Class<?> clazz, Map<String, String> aliasMap, boolean sorted) {
        int serialzeFeatures;
        int ordinal;
        String propertyName;
        int i;
        Method[] methodArr;
        JSONField annotation;
        int serialzeFeatures2;
        int ordinal2;
        int i2;
        String propertyName2;
        JSONField fieldAnnotation;
        String propertyName3;
        String propertyName4;
        JSONField fieldAnnotation2;
        String propertyName5;
        Map<String, String> map = aliasMap;
        Map<String, FieldInfo> fieldInfoMap = new LinkedHashMap<>();
        Method[] methods = clazz.getMethods();
        int i3 = 0;
        for (int length = methods.length; i3 < length; length = i) {
            Method method = methods[i3];
            String methodName = method.getName();
            if (Modifier.isStatic(method.getModifiers())) {
                methodArr = methods;
                i = length;
            } else if (method.getReturnType().equals(Void.TYPE)) {
                methodArr = methods;
                i = length;
            } else if (method.getParameterTypes().length != 0) {
                methodArr = methods;
                i = length;
            } else if (method.getReturnType() == ClassLoader.class) {
                methodArr = methods;
                i = length;
            } else if (!method.getName().equals("getMetaClass") || !method.getReturnType().getName().equals("groovy.lang.MetaClass")) {
                JSONField annotation2 = (JSONField) method.getAnnotation(JSONField.class);
                if (annotation2 == null) {
                    annotation = getSupperMethodAnnotation(clazz, method);
                } else {
                    annotation = annotation2;
                }
                if (annotation == null) {
                    methodArr = methods;
                    ordinal2 = 0;
                    serialzeFeatures2 = 0;
                } else if (!annotation.serialize()) {
                    methodArr = methods;
                    i = length;
                } else {
                    ordinal2 = annotation.ordinal();
                    serialzeFeatures2 = SerializerFeature.of(annotation.serialzeFeatures());
                    if (annotation.name().length() != 0) {
                        String propertyName6 = annotation.name();
                        if (map != null) {
                            String propertyName7 = map.get(propertyName6);
                            if (propertyName7 == null) {
                                methodArr = methods;
                                i = length;
                            } else {
                                propertyName5 = propertyName7;
                            }
                        } else {
                            propertyName5 = propertyName6;
                        }
                        methodArr = methods;
                        fieldInfoMap.put(propertyName5, new FieldInfo(propertyName5, method, (Field) null, ordinal2, serialzeFeatures2));
                        i = length;
                    } else {
                        methodArr = methods;
                    }
                }
                if (!methodName.startsWith("get")) {
                    i = length;
                    i2 = 3;
                } else if (methodName.length() < 4) {
                    i = length;
                } else if (methodName.equals("getClass")) {
                    i = length;
                } else {
                    char c3 = methodName.charAt(3);
                    if (Character.isUpperCase(c3)) {
                        propertyName3 = compatibleWithJavaBean ? decapitalize(methodName.substring(3)) : Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
                    } else if (c3 == '_') {
                        propertyName3 = methodName.substring(4);
                    } else if (c3 == 'f') {
                        propertyName3 = methodName.substring(3);
                    } else if (methodName.length() < 5 || !Character.isUpperCase(methodName.charAt(4))) {
                        i = length;
                    } else {
                        propertyName3 = decapitalize(methodName.substring(3));
                    }
                    if (isJSONTypeIgnore(clazz, propertyName3)) {
                        i = length;
                    } else {
                        Field field = ParserConfig.getField(clazz, propertyName3);
                        if (!(field == null || (fieldAnnotation2 = (JSONField) field.getAnnotation(JSONField.class)) == null)) {
                            if (!fieldAnnotation2.serialize()) {
                                i = length;
                            } else {
                                int ordinal3 = fieldAnnotation2.ordinal();
                                int serialzeFeatures3 = SerializerFeature.of(fieldAnnotation2.serialzeFeatures());
                                if (fieldAnnotation2.name().length() != 0) {
                                    propertyName3 = fieldAnnotation2.name();
                                    if (map != null) {
                                        propertyName3 = map.get(propertyName3);
                                        if (propertyName3 == null) {
                                            i = length;
                                        } else {
                                            serialzeFeatures2 = serialzeFeatures3;
                                            ordinal2 = ordinal3;
                                        }
                                    } else {
                                        serialzeFeatures2 = serialzeFeatures3;
                                        ordinal2 = ordinal3;
                                    }
                                } else {
                                    serialzeFeatures2 = serialzeFeatures3;
                                    ordinal2 = ordinal3;
                                }
                            }
                        }
                        if (map != null) {
                            String propertyName8 = map.get(propertyName3);
                            if (propertyName8 == null) {
                                i = length;
                            } else {
                                propertyName4 = propertyName8;
                            }
                        } else {
                            propertyName4 = propertyName3;
                        }
                        i = length;
                        i2 = 3;
                        fieldInfoMap.put(propertyName4, new FieldInfo(propertyName4, method, field, ordinal2, serialzeFeatures2));
                    }
                }
                if (methodName.startsWith("is") && methodName.length() >= i2) {
                    char c2 = methodName.charAt(2);
                    if (Character.isUpperCase(c2)) {
                        propertyName2 = compatibleWithJavaBean ? decapitalize(methodName.substring(2)) : Character.toLowerCase(methodName.charAt(2)) + methodName.substring(i2);
                    } else if (c2 == '_') {
                        propertyName2 = methodName.substring(i2);
                    } else if (c2 == 'f') {
                        propertyName2 = methodName.substring(2);
                    }
                    Field field2 = ParserConfig.getField(clazz, propertyName2);
                    if (field2 == null) {
                        field2 = ParserConfig.getField(clazz, methodName);
                    }
                    if (!(field2 == null || (fieldAnnotation = (JSONField) field2.getAnnotation(JSONField.class)) == null)) {
                        if (fieldAnnotation.serialize()) {
                            int ordinal4 = fieldAnnotation.ordinal();
                            int serialzeFeatures4 = SerializerFeature.of(fieldAnnotation.serialzeFeatures());
                            if (fieldAnnotation.name().length() != 0) {
                                propertyName2 = fieldAnnotation.name();
                                if (map != null) {
                                    propertyName2 = map.get(propertyName2);
                                    if (propertyName2 != null) {
                                        ordinal2 = ordinal4;
                                        serialzeFeatures2 = serialzeFeatures4;
                                    }
                                } else {
                                    ordinal2 = ordinal4;
                                    serialzeFeatures2 = serialzeFeatures4;
                                }
                            } else {
                                ordinal2 = ordinal4;
                                serialzeFeatures2 = serialzeFeatures4;
                            }
                        }
                    }
                    if (map == null || (propertyName2 = map.get(propertyName2)) != null) {
                        fieldInfoMap.put(propertyName2, new FieldInfo(propertyName2, method, field2, ordinal2, serialzeFeatures2));
                    }
                }
            } else {
                methodArr = methods;
                i = length;
            }
            i3++;
            methods = methodArr;
        }
        Field[] fields = clazz.getFields();
        int length2 = fields.length;
        int i4 = 0;
        while (i4 < length2) {
            Field field3 = fields[i4];
            if (!Modifier.isStatic(field3.getModifiers())) {
                JSONField fieldAnnotation3 = (JSONField) field3.getAnnotation(JSONField.class);
                String propertyName9 = field3.getName();
                if (fieldAnnotation3 == null) {
                    ordinal = 0;
                    serialzeFeatures = 0;
                } else if (fieldAnnotation3.serialize()) {
                    int ordinal5 = fieldAnnotation3.ordinal();
                    int serialzeFeatures5 = SerializerFeature.of(fieldAnnotation3.serialzeFeatures());
                    if (fieldAnnotation3.name().length() != 0) {
                        propertyName9 = fieldAnnotation3.name();
                        ordinal = ordinal5;
                        serialzeFeatures = serialzeFeatures5;
                    } else {
                        ordinal = ordinal5;
                        serialzeFeatures = serialzeFeatures5;
                    }
                }
                if (map != null) {
                    String propertyName10 = map.get(propertyName9);
                    if (propertyName10 != null) {
                        propertyName = propertyName10;
                    }
                } else {
                    propertyName = propertyName9;
                }
                if (!fieldInfoMap.containsKey(propertyName)) {
                    fieldInfoMap.put(propertyName, new FieldInfo(propertyName, (Method) null, field3, ordinal, serialzeFeatures));
                }
            }
            i4++;
            map = aliasMap;
        }
        List<FieldInfo> fieldInfoList = new ArrayList<>();
        boolean containsAll = false;
        String[] orders = null;
        JSONType annotation3 = (JSONType) clazz.getAnnotation(JSONType.class);
        if (annotation3 != null) {
            orders = annotation3.orders();
            if (orders == null || orders.length != fieldInfoMap.size()) {
                containsAll = false;
            } else {
                containsAll = true;
                int length3 = orders.length;
                int i5 = 0;
                while (true) {
                    if (i5 >= length3) {
                        break;
                    } else if (!fieldInfoMap.containsKey(orders[i5])) {
                        containsAll = false;
                        break;
                    } else {
                        i5++;
                    }
                }
            }
        }
        if (containsAll) {
            for (String item : orders) {
                fieldInfoList.add(fieldInfoMap.get(item));
            }
        } else {
            for (FieldInfo fieldInfo : fieldInfoMap.values()) {
                fieldInfoList.add(fieldInfo);
            }
            if (sorted) {
                Collections.sort(fieldInfoList);
            }
        }
        return fieldInfoList;
    }

    public static JSONField getSupperMethodAnnotation(Class<?> clazz, Method method) {
        JSONField annotation;
        for (Class<?> interfaceClass : clazz.getInterfaces()) {
            Method[] methods = interfaceClass.getMethods();
            for (Method interfaceMethod : methods) {
                if (interfaceMethod.getName().equals(method.getName()) && interfaceMethod.getParameterTypes().length == method.getParameterTypes().length) {
                    boolean match = true;
                    int i = 0;
                    while (true) {
                        if (i >= interfaceMethod.getParameterTypes().length) {
                            break;
                        } else if (!interfaceMethod.getParameterTypes()[i].equals(method.getParameterTypes()[i])) {
                            match = false;
                            break;
                        } else {
                            i++;
                        }
                    }
                    if (match && (annotation = (JSONField) interfaceMethod.getAnnotation(JSONField.class)) != null) {
                        return annotation;
                    }
                }
            }
        }
        return null;
    }

    private static boolean isJSONTypeIgnore(Class<?> clazz, String propertyName) {
        JSONType jsonType = (JSONType) clazz.getAnnotation(JSONType.class);
        if (!(jsonType == null || jsonType.ignores() == null)) {
            for (String item : jsonType.ignores()) {
                if (propertyName.equalsIgnoreCase(item)) {
                    return true;
                }
            }
        }
        return (clazz.getSuperclass() == Object.class || clazz.getSuperclass() == null || !isJSONTypeIgnore(clazz.getSuperclass(), propertyName)) ? false : true;
    }

    public static boolean isGenericParamType(Type type) {
        if (type instanceof ParameterizedType) {
            return true;
        }
        if (type instanceof Class) {
            return isGenericParamType(((Class) type).getGenericSuperclass());
        }
        return false;
    }

    public static Type getGenericParamType(Type type) {
        if (!(type instanceof ParameterizedType) && (type instanceof Class)) {
            return getGenericParamType(((Class) type).getGenericSuperclass());
        }
        return type;
    }

    public static Type unwrap(Type type) {
        if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType) type).getGenericComponentType();
            if (componentType == Byte.TYPE) {
                return byte[].class;
            }
            if (componentType == Character.TYPE) {
                return char[].class;
            }
        }
        return type;
    }

    public static Class<?> getClass(Type type) {
        if (type.getClass() == Class.class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return getClass(((ParameterizedType) type).getRawType());
        }
        return Object.class;
    }

    public static Field getField(Class<?> clazz, String fieldName) {
        Field[] declaredFields = clazz.getDeclaredFields();
        for (Field field : declaredFields) {
            if (fieldName.equals(field.getName())) {
                return field;
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass == null || superClass == Object.class) {
            return null;
        }
        return getField(superClass, fieldName);
    }

    public static JSONType getJSONType(Class<?> clazz) {
        return (JSONType) clazz.getAnnotation(JSONType.class);
    }

    public static int getSerializeFeatures(Class<?> clazz) {
        JSONType annotation = (JSONType) clazz.getAnnotation(JSONType.class);
        if (annotation == null) {
            return 0;
        }
        return SerializerFeature.of(annotation.serialzeFeatures());
    }

    public static int getParserFeatures(Class<?> clazz) {
        JSONType annotation = (JSONType) clazz.getAnnotation(JSONType.class);
        if (annotation == null) {
            return 0;
        }
        return Feature.of(annotation.parseFeatures());
    }

    public static String decapitalize(String name) {
        if (name == null || name.length() == 0) {
            return name;
        }
        if (name.length() > 1 && Character.isUpperCase(name.charAt(1)) && Character.isUpperCase(name.charAt(0))) {
            return name;
        }
        char[] chars = name.toCharArray();
        chars[0] = Character.toLowerCase(chars[0]);
        return new String(chars);
    }

    static void setAccessible(AccessibleObject obj) {
        if (setAccessibleEnable && !obj.isAccessible()) {
            try {
                obj.setAccessible(true);
            } catch (AccessControlException e) {
                setAccessibleEnable = false;
            }
        }
    }
}
