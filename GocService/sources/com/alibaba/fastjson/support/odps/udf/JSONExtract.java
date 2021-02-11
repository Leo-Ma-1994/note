package com.alibaba.fastjson.support.odps.udf;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONPath;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.aliyun.odps.udf.UDF;

public class JSONExtract extends UDF {
    public JSONExtract() {
        SerializeConfig.getGlobalInstance().setAsmEnable(false);
    }

    public String evaluate(String jsonString, String path) throws Exception {
        return JSON.toJSONString(JSONPath.eval(JSON.parse(jsonString), path));
    }
}
