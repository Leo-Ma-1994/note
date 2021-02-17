package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public final class ListResolveFieldDeserializer extends FieldDeserializer {
    private final int index;
    private final List list;
    private final DefaultJSONParser parser;

    public ListResolveFieldDeserializer(DefaultJSONParser parser2, List list2, int index2) {
        super(null, null);
        this.parser = parser2;
        this.index = index2;
        this.list = list2;
    }

    @Override // com.alibaba.fastjson.parser.deserializer.FieldDeserializer
    public void setValue(Object object, Object value) {
        JSONArray jsonArray;
        Object array;
        Object item;
        this.list.set(this.index, value);
        List list2 = this.list;
        if ((list2 instanceof JSONArray) && (array = (jsonArray = (JSONArray) list2).getRelatedArray()) != null && Array.getLength(array) > this.index) {
            if (jsonArray.getComponentType() != null) {
                item = TypeUtils.cast(value, jsonArray.getComponentType(), this.parser.getConfig());
            } else {
                item = value;
            }
            Array.set(array, this.index, item);
        }
    }

    @Override // com.alibaba.fastjson.parser.deserializer.FieldDeserializer
    public void parseField(DefaultJSONParser parser2, Object object, Type objectType, Map<String, Object> map) {
    }
}
