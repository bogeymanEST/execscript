package com.github.bogeymanest.execscript;

import java.util.List;
import java.util.Map;

public abstract class Value {

    public Value add(Value value) {
        throw new UnsupportedOperationException("Value does not support addition");
    }

    public Value subtract(Value value) {
        throw new UnsupportedOperationException("Value does not support subtraction");
    }

    public Value multiply(Value value) {
        throw new UnsupportedOperationException("Value does not support multiplication");
    }

    public Value divide(Value value) {
        throw new UnsupportedOperationException("Value does not support division");
    }

    public String asString() {
        throw new UnsupportedOperationException("Value cannot be represented as a string");
    }

    public int asInt() {
        throw new UnsupportedOperationException("Value cannot be represented as an integer");
    }

    public double asDouble() {
        throw new UnsupportedOperationException("Value cannot be represented as a double");
    }

    public boolean asBoolean() {
        throw new UnsupportedOperationException("Value cannot be represented as a boolean");
    }

    public List<Value> asList() {
        throw new UnsupportedOperationException("Value cannot be represented as a list");
    }

    public Map<String, Value> asMap() {
        throw new UnsupportedOperationException("Value cannot be represented as a map");
    }

    /**
     * Compares the value to a given value
     *
     * @param value The value to compare to
     * @return -1 if this value is smaller than the given value, 0 if they are equal or 1 if this value is larger
     */
    public int compareTo(Value value) {
        throw new UnsupportedOperationException("Value cannot be compared");
    }
}
