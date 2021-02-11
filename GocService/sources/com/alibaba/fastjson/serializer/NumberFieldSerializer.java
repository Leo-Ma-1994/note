package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.util.FieldInfo;

/* access modifiers changed from: package-private */
public final class NumberFieldSerializer extends FieldSerializer {
    public NumberFieldSerializer(FieldInfo fieldInfo) {
        super(fieldInfo);
    }

    @Override // com.alibaba.fastjson.serializer.FieldSerializer
    public void writeProperty(JSONSerializer serializer, Object propertyValue) throws Exception {
        writePrefix(serializer);
        writeValue(serializer, propertyValue);
    }

    @Override // com.alibaba.fastjson.serializer.FieldSerializer
    public void writeValue(JSONSerializer serializer, Object propertyValue) throws Exception {
        SerializeWriter out = serializer.getWriter();
        if (propertyValue != null) {
            out.append((CharSequence) propertyValue.toString());
        } else if (out.isEnabled(SerializerFeature.WriteNullNumberAsZero)) {
            out.write('0');
        } else {
            out.writeNull();
        }
    }
}
