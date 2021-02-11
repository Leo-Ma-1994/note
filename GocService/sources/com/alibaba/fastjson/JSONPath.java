package com.alibaba.fastjson;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.ASMJavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.FieldDeserializer;
import com.alibaba.fastjson.parser.deserializer.JavaBeanDeserializer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;
import com.alibaba.fastjson.serializer.ASMJavaBeanSerializer;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.JavaBeanSerializer;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.util.IOUtils;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

public class JSONPath implements ObjectSerializer {
    private static int CACHE_SIZE = 1024;
    private static ConcurrentMap<String, JSONPath> pathCache = new ConcurrentHashMap(128, 0.75f, 1);
    private ParserConfig parserConfig;
    private final String path;
    private Segement[] segments;
    private SerializeConfig serializeConfig;

    /* access modifiers changed from: package-private */
    public interface Filter {
        boolean apply(JSONPath jSONPath, Object obj, Object obj2, Object obj3);
    }

    /* access modifiers changed from: package-private */
    public enum Operator {
        EQ,
        NE,
        GT,
        GE,
        LT,
        LE,
        LIKE,
        NOT_LIKE,
        RLIKE,
        NOT_RLIKE,
        IN,
        NOT_IN,
        BETWEEN,
        NOT_BETWEEN
    }

    /* access modifiers changed from: package-private */
    public interface Segement {
        Object eval(JSONPath jSONPath, Object obj, Object obj2);
    }

    public JSONPath(String path2) {
        this(path2, SerializeConfig.getGlobalInstance(), ParserConfig.getGlobalInstance());
    }

    public JSONPath(String path2, SerializeConfig serializeConfig2, ParserConfig parserConfig2) {
        if (path2 == null || path2.isEmpty()) {
            throw new IllegalArgumentException();
        }
        this.path = path2;
        this.serializeConfig = serializeConfig2;
        this.parserConfig = parserConfig2;
    }

    /* access modifiers changed from: protected */
    public void init() {
        if (this.segments == null) {
            if ("*".equals(this.path)) {
                this.segments = new Segement[]{WildCardSegement.instance};
            } else {
                this.segments = new JSONPathParser(this.path).explain();
            }
        }
    }

    public Object eval(Object rootObject) {
        if (rootObject == null) {
            return null;
        }
        init();
        Object currentObject = rootObject;
        int i = 0;
        while (true) {
            Segement[] segementArr = this.segments;
            if (i >= segementArr.length) {
                return currentObject;
            }
            currentObject = segementArr[i].eval(this, rootObject, currentObject);
            i++;
        }
    }

    public boolean contains(Object rootObject) {
        if (rootObject == null) {
            return false;
        }
        init();
        Object currentObject = rootObject;
        int i = 0;
        while (true) {
            Segement[] segementArr = this.segments;
            if (i >= segementArr.length) {
                return true;
            }
            currentObject = segementArr[i].eval(this, rootObject, currentObject);
            if (currentObject == null) {
                return false;
            }
            i++;
        }
    }

    public boolean containsValue(Object rootObject, Object value) {
        Object currentObject = eval(rootObject);
        if (currentObject == value) {
            return true;
        }
        if (currentObject == null) {
            return false;
        }
        if (!(currentObject instanceof Iterable)) {
            return eq(currentObject, value);
        }
        for (Object item : (Iterable) currentObject) {
            if (eq(item, value)) {
                return true;
            }
        }
        return false;
    }

    public int size(Object rootObject) {
        if (rootObject == null) {
            return -1;
        }
        init();
        Object currentObject = rootObject;
        int i = 0;
        while (true) {
            Segement[] segementArr = this.segments;
            if (i >= segementArr.length) {
                return evalSize(currentObject);
            }
            currentObject = segementArr[i].eval(this, rootObject, currentObject);
            i++;
        }
    }

    public void arrayAdd(Object rootObject, Object... values) {
        if (values != null && values.length != 0 && rootObject != null) {
            init();
            Object currentObject = rootObject;
            Object parentObject = null;
            int i = 0;
            while (true) {
                Segement[] segementArr = this.segments;
                if (i >= segementArr.length) {
                    break;
                }
                if (i == segementArr.length - 1) {
                    parentObject = currentObject;
                }
                currentObject = this.segments[i].eval(this, rootObject, currentObject);
                i++;
            }
            if (currentObject != null) {
                if (currentObject instanceof Collection) {
                    Collection collection = (Collection) currentObject;
                    for (Object value : values) {
                        collection.add(value);
                    }
                    return;
                }
                Class<?> resultClass = currentObject.getClass();
                if (resultClass.isArray()) {
                    int length = Array.getLength(currentObject);
                    Object descArray = Array.newInstance(resultClass.getComponentType(), values.length + length);
                    System.arraycopy(currentObject, 0, descArray, 0, length);
                    for (int i2 = 0; i2 < values.length; i2++) {
                        Array.set(descArray, length + i2, values[i2]);
                    }
                    Segement[] segementArr2 = this.segments;
                    Segement lastSegement = segementArr2[segementArr2.length - 1];
                    if (lastSegement instanceof PropertySegement) {
                        ((PropertySegement) lastSegement).setValue(this, parentObject, descArray);
                    } else if (lastSegement instanceof ArrayAccessSegement) {
                        ((ArrayAccessSegement) lastSegement).setValue(this, parentObject, descArray);
                    } else {
                        throw new UnsupportedOperationException();
                    }
                } else {
                    throw new UnsupportedOperationException();
                }
            } else {
                throw new JSONPathException("value not found in path " + this.path);
            }
        }
    }

    public boolean set(Object rootObject, Object value) {
        if (rootObject == null) {
            return false;
        }
        init();
        Object currentObject = rootObject;
        Object parentObject = null;
        int i = 0;
        while (true) {
            Segement[] segementArr = this.segments;
            if (i >= segementArr.length) {
                break;
            } else if (i == segementArr.length - 1) {
                parentObject = currentObject;
                break;
            } else {
                currentObject = segementArr[i].eval(this, rootObject, currentObject);
                if (currentObject == null) {
                    break;
                }
                i++;
            }
        }
        if (parentObject == null) {
            return false;
        }
        Segement[] segementArr2 = this.segments;
        Segement lastSegement = segementArr2[segementArr2.length - 1];
        if (lastSegement instanceof PropertySegement) {
            ((PropertySegement) lastSegement).setValue(this, parentObject, value);
            return true;
        } else if (lastSegement instanceof ArrayAccessSegement) {
            return ((ArrayAccessSegement) lastSegement).setValue(this, parentObject, value);
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public static Object eval(Object rootObject, String path2) {
        return compile(path2).eval(rootObject);
    }

    public static int size(Object rootObject, String path2) {
        JSONPath jsonpath = compile(path2);
        return jsonpath.evalSize(jsonpath.eval(rootObject));
    }

    public static boolean contains(Object rootObject, String path2) {
        if (rootObject == null) {
            return false;
        }
        return compile(path2).contains(rootObject);
    }

    public static boolean containsValue(Object rootObject, String path2, Object value) {
        return compile(path2).containsValue(rootObject, value);
    }

    public static void arrayAdd(Object rootObject, String path2, Object... values) {
        compile(path2).arrayAdd(rootObject, values);
    }

    public static void set(Object rootObject, String path2, Object value) {
        compile(path2).set(rootObject, value);
    }

    public static JSONPath compile(String path2) {
        JSONPath jsonpath = pathCache.get(path2);
        if (jsonpath != null) {
            return jsonpath;
        }
        JSONPath jsonpath2 = new JSONPath(path2);
        if (pathCache.size() >= CACHE_SIZE) {
            return jsonpath2;
        }
        pathCache.putIfAbsent(path2, jsonpath2);
        return pathCache.get(path2);
    }

    public String getPath() {
        return this.path;
    }

    /* access modifiers changed from: package-private */
    public static class JSONPathParser {
        private char ch;
        private int level;
        private final String path;
        private int pos;

        public JSONPathParser(String path2) {
            this.path = path2;
            next();
        }

        /* access modifiers changed from: package-private */
        public void next() {
            String str = this.path;
            int i = this.pos;
            this.pos = i + 1;
            this.ch = str.charAt(i);
        }

        /* access modifiers changed from: package-private */
        public boolean isEOF() {
            return this.pos >= this.path.length();
        }

        /* access modifiers changed from: package-private */
        public Segement readSegement() {
            while (!isEOF()) {
                skipWhitespace();
                char c = this.ch;
                if (c == '@') {
                    next();
                    return SelfSegement.instance;
                } else if (c == '$') {
                    next();
                } else if (c == '.') {
                    next();
                    if (this.ch == '*') {
                        if (!isEOF()) {
                            next();
                        }
                        return WildCardSegement.instance;
                    }
                    String propertyName = readName();
                    if (this.ch != '(') {
                        return new PropertySegement(propertyName);
                    }
                    next();
                    if (this.ch == ')') {
                        if (!isEOF()) {
                            next();
                        }
                        if ("size".equals(propertyName)) {
                            return SizeSegement.instance;
                        }
                        throw new UnsupportedOperationException();
                    }
                    throw new UnsupportedOperationException();
                } else if (c == '[') {
                    return parseArrayAccess();
                } else {
                    if (this.level == 0) {
                        return new PropertySegement(readName());
                    }
                    throw new UnsupportedOperationException();
                }
            }
            return null;
        }

        public final void skipWhitespace() {
            while (this.ch < IOUtils.whitespaceFlags.length && IOUtils.whitespaceFlags[this.ch]) {
                next();
            }
        }

        /* access modifiers changed from: package-private */
        public Segement parseArrayAccess() {
            String[] containsValues;
            String endsWithValue;
            String endsWithValue2;
            accept('[');
            boolean predicateFlag = false;
            if (this.ch == '?') {
                next();
                accept('(');
                if (this.ch == '@') {
                    next();
                    accept('.');
                }
                predicateFlag = true;
            }
            if (predicateFlag || IOUtils.firstIdentifier(this.ch)) {
                String propertyName = readName();
                skipWhitespace();
                if (predicateFlag && this.ch == ')') {
                    next();
                    accept(']');
                    return new FilterSegement(new NotNullSegement(propertyName));
                } else if (this.ch == ']') {
                    next();
                    return new FilterSegement(new NotNullSegement(propertyName));
                } else {
                    Operator op = readOp();
                    skipWhitespace();
                    if (op == Operator.BETWEEN || op == Operator.NOT_BETWEEN) {
                        boolean not = op == Operator.NOT_BETWEEN;
                        Object startValue = readValue();
                        if ("and".equalsIgnoreCase(readName())) {
                            Object endValue = readValue();
                            if (startValue == null || endValue == null) {
                                throw new JSONPathException(this.path);
                            } else if (JSONPath.isInt(startValue.getClass()) && JSONPath.isInt(endValue.getClass())) {
                                return new FilterSegement(new IntBetweenSegement(propertyName, ((Number) startValue).longValue(), ((Number) endValue).longValue(), not));
                            } else {
                                throw new JSONPathException(this.path);
                            }
                        } else {
                            throw new JSONPathException(this.path);
                        }
                    } else if (op == Operator.IN || op == Operator.NOT_IN) {
                        boolean not2 = op == Operator.NOT_IN;
                        accept('(');
                        List<Object> valueList = new ArrayList<>();
                        valueList.add(readValue());
                        while (true) {
                            skipWhitespace();
                            if (this.ch != ',') {
                                break;
                            }
                            next();
                            valueList.add(readValue());
                        }
                        accept(')');
                        if (predicateFlag) {
                            accept(')');
                        }
                        accept(']');
                        boolean isInt = true;
                        boolean isIntObj = true;
                        boolean isString = true;
                        for (Object item : valueList) {
                            if (item != null) {
                                Class<?> clazz = item.getClass();
                                if (!(!isInt || clazz == Byte.class || clazz == Short.class || clazz == Integer.class || clazz == Long.class)) {
                                    isInt = false;
                                    isIntObj = false;
                                }
                                if (isString && clazz != String.class) {
                                    isString = false;
                                }
                            } else if (isInt) {
                                isInt = false;
                            }
                        }
                        if (valueList.size() == 1 && valueList.get(0) == null) {
                            if (not2) {
                                return new FilterSegement(new NotNullSegement(propertyName));
                            }
                            return new FilterSegement(new NullSegement(propertyName));
                        } else if (isInt) {
                            if (valueList.size() == 1) {
                                return new FilterSegement(new IntOpSegement(propertyName, ((Number) valueList.get(0)).longValue(), not2 ? Operator.NE : Operator.EQ));
                            }
                            long[] values = new long[valueList.size()];
                            for (int i = 0; i < values.length; i++) {
                                values[i] = ((Number) valueList.get(i)).longValue();
                            }
                            return new FilterSegement(new IntInSegement(propertyName, values, not2));
                        } else if (isString) {
                            if (valueList.size() == 1) {
                                return new FilterSegement(new StringOpSegement(propertyName, (String) valueList.get(0), not2 ? Operator.NE : Operator.EQ));
                            }
                            String[] values2 = new String[valueList.size()];
                            valueList.toArray(values2);
                            return new FilterSegement(new StringInSegement(propertyName, values2, not2));
                        } else if (isIntObj) {
                            Long[] values3 = new Long[valueList.size()];
                            for (int i2 = 0; i2 < values3.length; i2++) {
                                Number item2 = (Number) valueList.get(i2);
                                if (item2 != null) {
                                    values3[i2] = Long.valueOf(item2.longValue());
                                }
                            }
                            return new FilterSegement(new IntObjInSegement(propertyName, values3, not2));
                        } else {
                            throw new UnsupportedOperationException();
                        }
                    } else {
                        char c = this.ch;
                        if (c == '\'' || c == '\"') {
                            String strValue = readString();
                            if (predicateFlag) {
                                accept(')');
                            }
                            accept(']');
                            if (op == Operator.RLIKE) {
                                return new FilterSegement(new RlikeSegement(propertyName, strValue, false));
                            }
                            if (op == Operator.NOT_RLIKE) {
                                return new FilterSegement(new RlikeSegement(propertyName, strValue, true));
                            }
                            if (op == Operator.LIKE || op == Operator.NOT_LIKE) {
                                while (strValue.indexOf("%%") != -1) {
                                    strValue = strValue.replaceAll("%%", "%");
                                }
                                boolean not3 = op == Operator.NOT_LIKE;
                                int p0 = strValue.indexOf(37);
                                if (p0 != -1) {
                                    String[] items = strValue.split("%");
                                    if (p0 == 0) {
                                        if (strValue.charAt(strValue.length() - 1) == '%') {
                                            String[] containsValues2 = new String[(items.length - 1)];
                                            System.arraycopy(items, 1, containsValues2, 0, containsValues2.length);
                                            containsValues = containsValues2;
                                            endsWithValue2 = null;
                                            endsWithValue = null;
                                        } else {
                                            String endsWithValue3 = items[items.length - 1];
                                            if (items.length > 2) {
                                                String[] containsValues3 = new String[(items.length - 2)];
                                                System.arraycopy(items, 1, containsValues3, 0, containsValues3.length);
                                                endsWithValue = endsWithValue3;
                                                endsWithValue2 = null;
                                                containsValues = containsValues3;
                                            } else {
                                                endsWithValue = endsWithValue3;
                                                endsWithValue2 = null;
                                                containsValues = null;
                                            }
                                        }
                                    } else if (strValue.charAt(strValue.length() - 1) == '%') {
                                        containsValues = items;
                                        endsWithValue2 = null;
                                        endsWithValue = null;
                                    } else if (items.length == 1) {
                                        endsWithValue2 = items[0];
                                        endsWithValue = null;
                                        containsValues = null;
                                    } else if (items.length == 2) {
                                        endsWithValue2 = items[0];
                                        endsWithValue = items[1];
                                        containsValues = null;
                                    } else {
                                        endsWithValue2 = items[0];
                                        String endsWithValue4 = items[items.length - 1];
                                        String[] containsValues4 = new String[(items.length - 2)];
                                        System.arraycopy(items, 1, containsValues4, 0, containsValues4.length);
                                        endsWithValue = endsWithValue4;
                                        containsValues = containsValues4;
                                    }
                                    return new FilterSegement(new MatchSegement(propertyName, endsWithValue2, endsWithValue, containsValues, not3));
                                } else if (op == Operator.LIKE) {
                                    op = Operator.EQ;
                                } else {
                                    op = Operator.NE;
                                }
                            }
                            return new FilterSegement(new StringOpSegement(propertyName, strValue, op));
                        } else if (isDigitFirst(c)) {
                            long value = readLongValue();
                            if (predicateFlag) {
                                accept(')');
                            }
                            accept(']');
                            return new FilterSegement(new IntOpSegement(propertyName, value, op));
                        } else if (this.ch != 'n' || !"null".equals(readName())) {
                            throw new UnsupportedOperationException();
                        } else {
                            if (predicateFlag) {
                                accept(')');
                            }
                            accept(']');
                            if (op == Operator.EQ) {
                                return new FilterSegement(new NullSegement(propertyName));
                            }
                            if (op == Operator.NE) {
                                return new FilterSegement(new NotNullSegement(propertyName));
                            }
                            throw new UnsupportedOperationException();
                        }
                    }
                }
            } else {
                int start = this.pos - 1;
                while (this.ch != ']' && !isEOF()) {
                    next();
                }
                String text = this.path.substring(start, this.pos - 1);
                if (!isEOF()) {
                    accept(']');
                }
                return buildArraySegement(text);
            }
        }

        /* access modifiers changed from: protected */
        public long readLongValue() {
            int beginIndex = this.pos - 1;
            char c = this.ch;
            if (c == '+' || c == '-') {
                next();
            }
            while (true) {
                char c2 = this.ch;
                if (c2 < '0' || c2 > '9') {
                    break;
                }
                next();
            }
            return Long.parseLong(this.path.substring(beginIndex, this.pos - 1));
        }

        /* access modifiers changed from: protected */
        public Object readValue() {
            skipWhitespace();
            if (isDigitFirst(this.ch)) {
                return Long.valueOf(readLongValue());
            }
            char c = this.ch;
            if (c == '\"' || c == '\'') {
                return readString();
            }
            if (c != 'n') {
                throw new UnsupportedOperationException();
            } else if ("null".equals(readName())) {
                return null;
            } else {
                throw new JSONPathException(this.path);
            }
        }

        static boolean isDigitFirst(char ch2) {
            return ch2 == '-' || ch2 == '+' || (ch2 >= '0' && ch2 <= '9');
        }

        /* access modifiers changed from: protected */
        public Operator readOp() {
            Operator op = null;
            char c = this.ch;
            if (c == '=') {
                next();
                op = Operator.EQ;
            } else if (c == '!') {
                next();
                accept('=');
                op = Operator.NE;
            } else if (c == '<') {
                next();
                if (this.ch == '=') {
                    next();
                    op = Operator.LE;
                } else {
                    op = Operator.LT;
                }
            } else if (c == '>') {
                next();
                if (this.ch == '=') {
                    next();
                    op = Operator.GE;
                } else {
                    op = Operator.GT;
                }
            }
            if (op != null) {
                return op;
            }
            String name = readName();
            if ("not".equalsIgnoreCase(name)) {
                skipWhitespace();
                String name2 = readName();
                if ("like".equalsIgnoreCase(name2)) {
                    return Operator.NOT_LIKE;
                }
                if ("rlike".equalsIgnoreCase(name2)) {
                    return Operator.NOT_RLIKE;
                }
                if ("in".equalsIgnoreCase(name2)) {
                    return Operator.NOT_IN;
                }
                if ("between".equalsIgnoreCase(name2)) {
                    return Operator.NOT_BETWEEN;
                }
                throw new UnsupportedOperationException();
            } else if ("like".equalsIgnoreCase(name)) {
                return Operator.LIKE;
            } else {
                if ("rlike".equalsIgnoreCase(name)) {
                    return Operator.RLIKE;
                }
                if ("in".equalsIgnoreCase(name)) {
                    return Operator.IN;
                }
                if ("between".equalsIgnoreCase(name)) {
                    return Operator.BETWEEN;
                }
                throw new UnsupportedOperationException();
            }
        }

        /* access modifiers changed from: package-private */
        public String readName() {
            skipWhitespace();
            if (IOUtils.firstIdentifier(this.ch)) {
                StringBuffer buf = new StringBuffer();
                while (!isEOF()) {
                    char c = this.ch;
                    if (c == '\\') {
                        next();
                        buf.append(this.ch);
                        next();
                    } else if (!IOUtils.isIdent(c)) {
                        break;
                    } else {
                        buf.append(this.ch);
                        next();
                    }
                }
                if (isEOF() && IOUtils.isIdent(this.ch)) {
                    buf.append(this.ch);
                }
                return buf.toString();
            }
            throw new JSONPathException("illeal jsonpath syntax. " + this.path);
        }

        /* access modifiers changed from: package-private */
        public String readString() {
            char quoate = this.ch;
            next();
            int beginIndex = this.pos - 1;
            while (this.ch != quoate && !isEOF()) {
                next();
            }
            String strValue = this.path.substring(beginIndex, isEOF() ? this.pos : this.pos - 1);
            accept(quoate);
            return strValue;
        }

        /* access modifiers changed from: package-private */
        public void accept(char expect) {
            if (this.ch != expect) {
                throw new JSONPathException("expect '" + expect + ", but '" + this.ch + "'");
            } else if (!isEOF()) {
                next();
            }
        }

        public Segement[] explain() {
            String str = this.path;
            if (str == null || str.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Segement[] segements = new Segement[8];
            while (true) {
                Segement segment = readSegement();
                if (segment == null) {
                    break;
                }
                int i = this.level;
                this.level = i + 1;
                segements[i] = segment;
            }
            int i2 = this.level;
            if (i2 == segements.length) {
                return segements;
            }
            Segement[] result = new Segement[i2];
            System.arraycopy(segements, 0, result, 0, i2);
            return result;
        }

        /* access modifiers changed from: package-private */
        public Segement buildArraySegement(String indexText) {
            int end;
            int step;
            int indexTextLen = indexText.length();
            char firstChar = indexText.charAt(0);
            char lastChar = indexText.charAt(indexTextLen - 1);
            int commaIndex = indexText.indexOf(44);
            if (indexText.length() <= 2 || firstChar != '\'' || lastChar != '\'') {
                int colonIndex = indexText.indexOf(58);
                if (commaIndex == -1 && colonIndex == -1) {
                    return new ArrayAccessSegement(Integer.parseInt(indexText));
                }
                if (commaIndex != -1) {
                    String[] indexesText = indexText.split(",");
                    int[] indexes = new int[indexesText.length];
                    for (int i = 0; i < indexesText.length; i++) {
                        indexes[i] = Integer.parseInt(indexesText[i]);
                    }
                    return new MultiIndexSegement(indexes);
                } else if (colonIndex != -1) {
                    String[] indexesText2 = indexText.split(":");
                    int[] indexes2 = new int[indexesText2.length];
                    for (int i2 = 0; i2 < indexesText2.length; i2++) {
                        String str = indexesText2[i2];
                        if (!str.isEmpty()) {
                            indexes2[i2] = Integer.parseInt(str);
                        } else if (i2 == 0) {
                            indexes2[i2] = 0;
                        } else {
                            throw new UnsupportedOperationException();
                        }
                    }
                    int start = indexes2[0];
                    if (indexes2.length > 1) {
                        end = indexes2[1];
                    } else {
                        end = -1;
                    }
                    if (indexes2.length == 3) {
                        step = indexes2[2];
                    } else {
                        step = 1;
                    }
                    if (end >= 0 && end < start) {
                        throw new UnsupportedOperationException("end must greater than or equals start. start " + start + ",  end " + end);
                    } else if (step > 0) {
                        return new RangeSegement(start, end, step);
                    } else {
                        throw new UnsupportedOperationException("step must greater than zero : " + step);
                    }
                } else {
                    throw new UnsupportedOperationException();
                }
            } else if (commaIndex == -1) {
                return new PropertySegement(indexText.substring(1, indexTextLen - 1));
            } else {
                String[] indexesText3 = indexText.split(",");
                String[] propertyNames = new String[indexesText3.length];
                for (int i3 = 0; i3 < indexesText3.length; i3++) {
                    String indexesTextItem = indexesText3[i3];
                    propertyNames[i3] = indexesTextItem.substring(1, indexesTextItem.length() - 1);
                }
                return new MultiPropertySegement(propertyNames);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public static class SelfSegement implements Segement {
        public static final SelfSegement instance = new SelfSegement();

        SelfSegement() {
        }

        @Override // com.alibaba.fastjson.JSONPath.Segement
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            return currentObject;
        }
    }

    /* access modifiers changed from: package-private */
    public static class SizeSegement implements Segement {
        public static final SizeSegement instance = new SizeSegement();

        SizeSegement() {
        }

        @Override // com.alibaba.fastjson.JSONPath.Segement
        public Integer eval(JSONPath path, Object rootObject, Object currentObject) {
            return Integer.valueOf(path.evalSize(currentObject));
        }
    }

    /* access modifiers changed from: package-private */
    public static class PropertySegement implements Segement {
        private final String propertyName;

        public PropertySegement(String propertyName2) {
            this.propertyName = propertyName2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segement
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            return path.getPropertyValue(currentObject, this.propertyName, true);
        }

        public void setValue(JSONPath path, Object parent, Object value) {
            path.setPropertyValue(parent, this.propertyName, value);
        }
    }

    /* access modifiers changed from: package-private */
    public static class MultiPropertySegement implements Segement {
        private final String[] propertyNames;

        public MultiPropertySegement(String[] propertyNames2) {
            this.propertyNames = propertyNames2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segement
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            List<Object> fieldValues = new ArrayList<>(this.propertyNames.length);
            for (String propertyName : this.propertyNames) {
                fieldValues.add(path.getPropertyValue(currentObject, propertyName, true));
            }
            return fieldValues;
        }
    }

    /* access modifiers changed from: package-private */
    public static class WildCardSegement implements Segement {
        public static WildCardSegement instance = new WildCardSegement();

        WildCardSegement() {
        }

        @Override // com.alibaba.fastjson.JSONPath.Segement
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            return path.getPropertyValues(currentObject);
        }
    }

    /* access modifiers changed from: package-private */
    public static class ArrayAccessSegement implements Segement {
        private final int index;

        public ArrayAccessSegement(int index2) {
            this.index = index2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segement
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            return path.getArrayItem(currentObject, this.index);
        }

        public boolean setValue(JSONPath path, Object currentObject, Object value) {
            return path.setArrayItem(path, currentObject, this.index, value);
        }
    }

    /* access modifiers changed from: package-private */
    public static class MultiIndexSegement implements Segement {
        private final int[] indexes;

        public MultiIndexSegement(int[] indexes2) {
            this.indexes = indexes2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segement
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            List<Object> items = new ArrayList<>(this.indexes.length);
            int i = 0;
            while (true) {
                int[] iArr = this.indexes;
                if (i >= iArr.length) {
                    return items;
                }
                items.add(path.getArrayItem(currentObject, iArr[i]));
                i++;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public static class RangeSegement implements Segement {
        private final int end;
        private final int start;
        private final int step;

        public RangeSegement(int start2, int end2, int step2) {
            this.start = start2;
            this.end = end2;
            this.step = step2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segement
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            int size = SizeSegement.instance.eval(path, rootObject, currentObject).intValue();
            int start2 = this.start;
            if (start2 < 0) {
                start2 += size;
            }
            int end2 = this.end;
            if (end2 < 0) {
                end2 += size;
            }
            List<Object> items = new ArrayList<>(((end2 - start2) / this.step) + 1);
            int i = start2;
            while (i <= end2 && i < size) {
                items.add(path.getArrayItem(currentObject, i));
                i += this.step;
            }
            return items;
        }
    }

    /* access modifiers changed from: package-private */
    public static class NotNullSegement implements Filter {
        private final String propertyName;

        public NotNullSegement(String propertyName2) {
            this.propertyName = propertyName2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            if (path.getPropertyValue(item, this.propertyName, false) != null) {
                return true;
            }
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public static class NullSegement implements Filter {
        private final String propertyName;

        public NullSegement(String propertyName2) {
            this.propertyName = propertyName2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            if (path.getPropertyValue(item, this.propertyName, false) == null) {
                return true;
            }
            return false;
        }
    }

    /* access modifiers changed from: package-private */
    public static class IntInSegement implements Filter {
        private final boolean not;
        private final String propertyName;
        private final long[] values;

        public IntInSegement(String propertyName2, long[] values2, boolean not2) {
            this.propertyName = propertyName2;
            this.values = values2;
            this.not = not2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = path.getPropertyValue(item, this.propertyName, false);
            if (propertyValue == null) {
                return false;
            }
            if (propertyValue instanceof Number) {
                long longPropertyValue = ((Number) propertyValue).longValue();
                for (long value : this.values) {
                    if (value == longPropertyValue) {
                        return !this.not;
                    }
                }
            }
            return this.not;
        }
    }

    /* access modifiers changed from: package-private */
    public static class IntBetweenSegement implements Filter {
        private final long endValue;
        private final boolean not;
        private final String propertyName;
        private final long startValue;

        public IntBetweenSegement(String propertyName2, long startValue2, long endValue2, boolean not2) {
            this.propertyName = propertyName2;
            this.startValue = startValue2;
            this.endValue = endValue2;
            this.not = not2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = path.getPropertyValue(item, this.propertyName, false);
            if (propertyValue == null) {
                return false;
            }
            if (propertyValue instanceof Number) {
                long longPropertyValue = ((Number) propertyValue).longValue();
                if (longPropertyValue >= this.startValue && longPropertyValue <= this.endValue) {
                    return !this.not;
                }
            }
            return this.not;
        }
    }

    /* access modifiers changed from: package-private */
    public static class IntObjInSegement implements Filter {
        private final boolean not;
        private final String propertyName;
        private final Long[] values;

        public IntObjInSegement(String propertyName2, Long[] values2, boolean not2) {
            this.propertyName = propertyName2;
            this.values = values2;
            this.not = not2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            int i = 0;
            Object propertyValue = path.getPropertyValue(item, this.propertyName, false);
            if (propertyValue == null) {
                Long[] lArr = this.values;
                int length = lArr.length;
                while (i < length) {
                    if (lArr[i] == null) {
                        return !this.not;
                    }
                    i++;
                }
                return this.not;
            }
            if (propertyValue instanceof Number) {
                long longPropertyValue = ((Number) propertyValue).longValue();
                Long[] lArr2 = this.values;
                int length2 = lArr2.length;
                while (i < length2) {
                    Long value = lArr2[i];
                    if (value != null && value.longValue() == longPropertyValue) {
                        return !this.not;
                    }
                    i++;
                }
            }
            return this.not;
        }
    }

    /* access modifiers changed from: package-private */
    public static class StringInSegement implements Filter {
        private final boolean not;
        private final String propertyName;
        private final String[] values;

        public StringInSegement(String propertyName2, String[] values2, boolean not2) {
            this.propertyName = propertyName2;
            this.values = values2;
            this.not = not2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = path.getPropertyValue(item, this.propertyName, false);
            String[] strArr = this.values;
            for (String value : strArr) {
                if (value == propertyValue) {
                    return !this.not;
                }
                if (value != null && value.equals(propertyValue)) {
                    return !this.not;
                }
            }
            return this.not;
        }
    }

    /* access modifiers changed from: package-private */
    public static class IntOpSegement implements Filter {
        private final Operator op;
        private final String propertyName;
        private final long value;

        public IntOpSegement(String propertyName2, long value2, Operator op2) {
            this.propertyName = propertyName2;
            this.value = value2;
            this.op = op2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = path.getPropertyValue(item, this.propertyName, false);
            if (propertyValue == null || !(propertyValue instanceof Number)) {
                return false;
            }
            long longValue = ((Number) propertyValue).longValue();
            if (this.op == Operator.EQ) {
                if (longValue == this.value) {
                    return true;
                }
                return false;
            } else if (this.op == Operator.NE) {
                if (longValue != this.value) {
                    return true;
                }
                return false;
            } else if (this.op == Operator.GE) {
                if (longValue >= this.value) {
                    return true;
                }
                return false;
            } else if (this.op == Operator.GT) {
                if (longValue > this.value) {
                    return true;
                }
                return false;
            } else if (this.op == Operator.LE) {
                if (longValue <= this.value) {
                    return true;
                }
                return false;
            } else if (this.op != Operator.LT || longValue >= this.value) {
                return false;
            } else {
                return true;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public static class MatchSegement implements Filter {
        private final String[] containsValues;
        private final String endsWithValue;
        private final int minLength;
        private final boolean not;
        private final String propertyName;
        private final String startsWithValue;

        public MatchSegement(String propertyName2, String startsWithValue2, String endsWithValue2, String[] containsValues2, boolean not2) {
            this.propertyName = propertyName2;
            this.startsWithValue = startsWithValue2;
            this.endsWithValue = endsWithValue2;
            this.containsValues = containsValues2;
            this.not = not2;
            int len = startsWithValue2 != null ? 0 + startsWithValue2.length() : 0;
            len = endsWithValue2 != null ? len + endsWithValue2.length() : len;
            if (containsValues2 != null) {
                for (String item : containsValues2) {
                    len += item.length();
                }
            }
            this.minLength = len;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = path.getPropertyValue(item, this.propertyName, false);
            if (propertyValue == null) {
                return false;
            }
            String strPropertyValue = propertyValue.toString();
            if (strPropertyValue.length() < this.minLength) {
                return this.not;
            }
            int start = 0;
            String str = this.startsWithValue;
            if (str != null) {
                if (!strPropertyValue.startsWith(str)) {
                    return this.not;
                }
                start = 0 + this.startsWithValue.length();
            }
            String[] strArr = this.containsValues;
            if (strArr != null) {
                for (String containsValue : strArr) {
                    int index = strPropertyValue.indexOf(containsValue, start);
                    if (index == -1) {
                        return this.not;
                    }
                    start = index + containsValue.length();
                }
            }
            String str2 = this.endsWithValue;
            if (str2 == null || strPropertyValue.endsWith(str2)) {
                return !this.not;
            }
            return this.not;
        }
    }

    /* access modifiers changed from: package-private */
    public static class RlikeSegement implements Filter {
        private final boolean not;
        private final Pattern pattern;
        private final String propertyName;

        public RlikeSegement(String propertyName2, String pattern2, boolean not2) {
            this.propertyName = propertyName2;
            this.pattern = Pattern.compile(pattern2);
            this.not = not2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = path.getPropertyValue(item, this.propertyName, false);
            if (propertyValue == null) {
                return false;
            }
            boolean match = this.pattern.matcher(propertyValue.toString()).matches();
            if (this.not) {
                return !match;
            }
            return match;
        }
    }

    /* access modifiers changed from: package-private */
    public static class StringOpSegement implements Filter {
        private final Operator op;
        private final String propertyName;
        private final String value;

        public StringOpSegement(String propertyName2, String value2, Operator op2) {
            this.propertyName = propertyName2;
            this.value = value2;
            this.op = op2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Filter
        public boolean apply(JSONPath path, Object rootObject, Object currentObject, Object item) {
            Object propertyValue = path.getPropertyValue(item, this.propertyName, false);
            if (this.op == Operator.EQ) {
                return this.value.equals(propertyValue);
            }
            if (this.op == Operator.NE) {
                return !this.value.equals(propertyValue);
            }
            if (propertyValue == null) {
                return false;
            }
            int compareResult = this.value.compareTo(propertyValue.toString());
            if (this.op == Operator.GE) {
                if (compareResult <= 0) {
                    return true;
                }
                return false;
            } else if (this.op == Operator.GT) {
                if (compareResult < 0) {
                    return true;
                }
                return false;
            } else if (this.op == Operator.LE) {
                if (compareResult >= 0) {
                    return true;
                }
                return false;
            } else if (this.op != Operator.LT || compareResult <= 0) {
                return false;
            } else {
                return true;
            }
        }
    }

    public static class FilterSegement implements Segement {
        private final Filter filter;

        public FilterSegement(Filter filter2) {
            this.filter = filter2;
        }

        @Override // com.alibaba.fastjson.JSONPath.Segement
        public Object eval(JSONPath path, Object rootObject, Object currentObject) {
            if (currentObject == null) {
                return null;
            }
            List<Object> items = new ArrayList<>();
            if (currentObject instanceof Iterable) {
                for (Object item : (Iterable) currentObject) {
                    if (this.filter.apply(path, rootObject, currentObject, item)) {
                        items.add(item);
                    }
                }
                return items;
            } else if (this.filter.apply(path, rootObject, currentObject, currentObject)) {
                return currentObject;
            } else {
                return null;
            }
        }
    }

    /* access modifiers changed from: protected */
    public Object getArrayItem(Object currentObject, int index) {
        if (currentObject == null) {
            return null;
        }
        if (currentObject instanceof List) {
            List list = (List) currentObject;
            if (index >= 0) {
                if (index < list.size()) {
                    return list.get(index);
                }
                return null;
            } else if (Math.abs(index) <= list.size()) {
                return list.get(list.size() + index);
            } else {
                return null;
            }
        } else if (currentObject.getClass().isArray()) {
            int arrayLenth = Array.getLength(currentObject);
            if (index >= 0) {
                if (index < arrayLenth) {
                    return Array.get(currentObject, index);
                }
                return null;
            } else if (Math.abs(index) <= arrayLenth) {
                return Array.get(currentObject, arrayLenth + index);
            } else {
                return null;
            }
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public boolean setArrayItem(JSONPath path2, Object currentObject, int index, Object value) {
        if (currentObject instanceof List) {
            List list = (List) currentObject;
            if (index >= 0) {
                list.set(index, value);
            } else {
                list.set(list.size() + index, value);
            }
            return true;
        } else if (currentObject.getClass().isArray()) {
            int arrayLenth = Array.getLength(currentObject);
            if (index >= 0) {
                if (index < arrayLenth) {
                    Array.set(currentObject, index, value);
                }
            } else if (Math.abs(index) <= arrayLenth) {
                Array.set(currentObject, arrayLenth + index, value);
            }
            return true;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    /* access modifiers changed from: protected */
    public Collection<Object> getPropertyValues(Object currentObject) {
        JavaBeanSerializer beanSerializer = getJavaBeanSerializer(currentObject.getClass());
        if (beanSerializer != null) {
            try {
                return beanSerializer.getFieldValues(currentObject);
            } catch (Exception e) {
                throw new JSONPathException("jsonpath error, path " + this.path, e);
            }
        } else if (currentObject instanceof Map) {
            return ((Map) currentObject).values();
        } else {
            throw new UnsupportedOperationException();
        }
    }

    static boolean eq(Object a, Object b) {
        if (a == b) {
            return true;
        }
        if (a == null || b == null) {
            return false;
        }
        if (a.getClass() == b.getClass()) {
            return a.equals(b);
        }
        if (!(a instanceof Number)) {
            return a.equals(b);
        }
        if (b instanceof Number) {
            return eqNotNull((Number) a, (Number) b);
        }
        return false;
    }

    static boolean eqNotNull(Number a, Number b) {
        Class clazzA = a.getClass();
        boolean isIntA = isInt(clazzA);
        Class clazzB = a.getClass();
        boolean isIntB = isInt(clazzB);
        if (!isIntA || !isIntB) {
            boolean isDoubleA = isDouble(clazzA);
            boolean isDoubleB = isDouble(clazzB);
            if (((!isDoubleA || !isDoubleB) && ((!isDoubleA || !isIntA) && (!isDoubleB || !isIntA))) || a.doubleValue() != b.doubleValue()) {
                return false;
            }
            return true;
        } else if (a.longValue() == b.longValue()) {
            return true;
        } else {
            return false;
        }
    }

    protected static boolean isDouble(Class<?> clazzA) {
        return clazzA == Float.class || clazzA == Double.class;
    }

    protected static boolean isInt(Class<?> clazzA) {
        return clazzA == Byte.class || clazzA == Short.class || clazzA == Integer.class || clazzA == Long.class;
    }

    /* access modifiers changed from: protected */
    public Object getPropertyValue(Object currentObject, String propertyName, boolean strictMode) {
        if (currentObject == null) {
            return null;
        }
        if (currentObject instanceof Map) {
            return ((Map) currentObject).get(propertyName);
        }
        JavaBeanSerializer beanSerializer = getJavaBeanSerializer(currentObject.getClass());
        if (beanSerializer != null) {
            try {
                return beanSerializer.getFieldValue(currentObject, propertyName);
            } catch (Exception e) {
                throw new JSONPathException("jsonpath error, path " + this.path + ", segement " + propertyName, e);
            }
        } else if (currentObject instanceof List) {
            List list = (List) currentObject;
            List<Object> fieldValues = new ArrayList<>(list.size());
            for (int i = 0; i < list.size(); i++) {
                fieldValues.add(getPropertyValue(list.get(i), propertyName, strictMode));
            }
            return fieldValues;
        } else {
            throw new JSONPathException("jsonpath error, path " + this.path + ", segement " + propertyName);
        }
    }

    /* access modifiers changed from: protected */
    public boolean setPropertyValue(Object parent, String name, Object value) {
        if (parent instanceof Map) {
            ((Map) parent).put(name, value);
            return true;
        }
        ObjectDeserializer derializer = this.parserConfig.getDeserializer(parent.getClass());
        JavaBeanDeserializer beanDerializer = null;
        if (derializer instanceof JavaBeanDeserializer) {
            beanDerializer = (JavaBeanDeserializer) derializer;
        } else if (derializer instanceof ASMJavaBeanDeserializer) {
            beanDerializer = ((ASMJavaBeanDeserializer) derializer).getInnterSerializer();
        }
        if (beanDerializer != null) {
            FieldDeserializer fieldDeserializer = beanDerializer.getFieldDeserializer(name);
            if (fieldDeserializer == null) {
                return false;
            }
            fieldDeserializer.setValue(parent, value);
            return true;
        }
        throw new UnsupportedOperationException();
    }

    /* access modifiers changed from: protected */
    public JavaBeanSerializer getJavaBeanSerializer(Class<?> currentClass) {
        ObjectSerializer serializer = this.serializeConfig.getObjectWriter(currentClass);
        if (serializer instanceof JavaBeanSerializer) {
            return (JavaBeanSerializer) serializer;
        }
        if (serializer instanceof ASMJavaBeanSerializer) {
            return ((ASMJavaBeanSerializer) serializer).getJavaBeanSerializer();
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public int evalSize(Object currentObject) {
        if (currentObject == null) {
            return -1;
        }
        if (currentObject instanceof Collection) {
            return ((Collection) currentObject).size();
        }
        if (currentObject instanceof Object[]) {
            return ((Object[]) currentObject).length;
        }
        if (currentObject.getClass().isArray()) {
            return Array.getLength(currentObject);
        }
        if (currentObject instanceof Map) {
            int count = 0;
            for (Object value : ((Map) currentObject).values()) {
                if (value != null) {
                    count++;
                }
            }
            return count;
        }
        JavaBeanSerializer beanSerializer = getJavaBeanSerializer(currentObject.getClass());
        if (beanSerializer == null) {
            return -1;
        }
        try {
            List<Object> values = beanSerializer.getFieldValues(currentObject);
            int count2 = 0;
            for (int i = 0; i < values.size(); i++) {
                if (values.get(i) != null) {
                    count2++;
                }
            }
            return count2;
        } catch (Exception e) {
            throw new JSONException("evalSize error : " + this.path, e);
        }
    }

    @Override // com.alibaba.fastjson.serializer.ObjectSerializer
    public void write(JSONSerializer serializer, Object object, Object fieldName, Type fieldType, int features) throws IOException {
        serializer.write(this.path);
    }
}
