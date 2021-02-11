package com.alibaba.fastjson.support.odps.udf;

import com.alibaba.fastjson.JSON;
import com.aliyun.odps.udf.UDF;

public class CodecCheck extends UDF {
    public String evaluate() throws Exception {
        A a = new A();
        a.setId(123);
        a.setName("xxx");
        JSON.parseObject(JSON.toJSONString(a), A.class);
        return "ok";
    }

    public static class A {
        private int id;
        private String name;

        public int getId() {
            return this.id;
        }

        public void setId(int id2) {
            this.id = id2;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name2) {
            this.name = name2;
        }
    }
}
