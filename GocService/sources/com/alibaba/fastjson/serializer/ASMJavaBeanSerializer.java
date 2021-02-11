package com.alibaba.fastjson.serializer;

public abstract class ASMJavaBeanSerializer implements ObjectSerializer {
    protected JavaBeanSerializer nature;

    public ASMJavaBeanSerializer(Class<?> clazz) {
        this.nature = new JavaBeanSerializer(clazz);
    }

    public JavaBeanSerializer getJavaBeanSerializer() {
        return this.nature;
    }
}
