package com.github.bogeymanest.execscript;

import java.util.List;

public class ListValue extends Value {
    private List<Value> value;

    public ListValue(List<Value> value) {
        this.value = value;
    }

    @Override
    public String asString() {
        StringBuilder sb = new StringBuilder("[");
        for (Value v : value) {
            sb.append(v.asString()).append(", ");
        }
        if (value.size() > 0) sb.delete(sb.length() - 2, sb.length());
        sb.append("]");
        return sb.toString();
    }

    @Override
    public Value add(Value value) {
        this.value.add(value);
        return this;
    }

    @Override
    public List<Value> asList() {
        return value;
    }
}
