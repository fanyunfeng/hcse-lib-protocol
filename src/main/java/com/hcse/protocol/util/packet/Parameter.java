package com.hcse.protocol.util.packet;

import java.util.HashMap;
import java.util.Map;

public class Parameter {
    // 参数信息的名称
    private String name;
    
    // 该参数对应的 ：具体信息 key: 参数值，value：参数的MD5
    private Map<String, String> values = new HashMap<String, String>();

    public Map<String, String> getValues() {
        return values;
    }

    public void addParameter(String value, String d2Key) {
        values.put(value, d2Key);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ParameterInfo [name=" + name + ", values=" + values + "]";
    }
}
