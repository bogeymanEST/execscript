package com.github.bogeymanest.execscript;

public class BooleanValue extends Value {
    private boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public boolean asBoolean() {
        return value;
    }

    @Override
    public String asString() {
        return value ? "true" : "false";
    }
}
