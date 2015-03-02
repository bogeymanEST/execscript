package com.github.bogeymanest.execscript;

public class DoubleValue extends Value {
    private double value;

    public DoubleValue(double value) {
        this.value = value;
    }

    @Override
    public Value add(Value value) {
        this.value += value.asDouble();
        return this;
    }

    @Override
    public Value subtract(Value value) {
        this.value -= value.asDouble();
        return this;
    }

    @Override
    public Value multiply(Value value) {
        this.value *= value.asDouble();
        return this;
    }

    @Override
    public Value divide(Value value) {
        this.value /= value.asDouble();
        if (this.value != (int) this.value && this instanceof IntegerValue)
            return new DoubleValue(this.value);
        return this;
    }

    @Override
    public String asString() {
        return String.valueOf(value);
    }

    @Override
    public int asInt() {
        return (int) value;
    }

    @Override
    public double asDouble() {
        return value;
    }

    @Override
    public boolean asBoolean() {
        return value != 0;
    }

    @Override
    public int compareTo(Value value) {
        if (asDouble() < value.asDouble())
            return -1;
        else if (asDouble() == value.asDouble())
            return 0;
        else
            return 1;
    }
}
