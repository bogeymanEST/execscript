package com.github.bogeymanest.execscript;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private Map<String, Value> variables = new HashMap<>();

    public void set(String id, Value value) {
        variables.put(id, value);
    }

    public Value get(String id) {
        return variables.get(id);
    }
}
