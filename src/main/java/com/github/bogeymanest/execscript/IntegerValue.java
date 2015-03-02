package com.github.bogeymanest.execscript;

public class IntegerValue extends DoubleValue {
    public IntegerValue(int value) {
        super(value);
    }

    @Override
    public String asString() {
        return String.valueOf(asInt());
    }
}
