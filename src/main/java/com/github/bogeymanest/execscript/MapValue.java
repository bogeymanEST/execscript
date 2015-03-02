package com.github.bogeymanest.execscript;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class MapValue extends Value {
    private Map<String, Value> value;

    public MapValue(Map<String, Value> value) {
        this.value = value;
    }

    @Override
    public String asString() {
        StringBuilder sb = new StringBuilder("{");
        for (Map.Entry<String, Value> entry : value.entrySet()) {
            String key = entry.getKey();
            if (!StringUtils.isNumeric(key))
                key = "\"" + key + "\"";
            sb.append(key).append(": ").append(entry.getValue().asString()).append(", ");
        }
        if (value.size() > 0) sb.delete(sb.length() - 2, sb.length());
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Map<String, Value> asMap() {
        return value;
    }
}
