package com.github.bogeymanest.execscript;

public class StringValue extends Value {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String asString() {
        return value;
    }

    @Override
    public Value add(Value value) {
        this.value += value.asString();
        return this;
    }
}
