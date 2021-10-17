package com.hamrasta.trellis.util.generic;

public interface IComparable<T> {

    default boolean isIn(T... values) {
        for (T current : values) {
            if (this.equals(current)) {
                return true;
            }
        }
        return false;
    }

    default boolean notEqual(T value) {
        return !equals(value);
    }

    default boolean notIn(T... values) {
        for (T current : values) {
            if (this.equals(current)) {
                return false;
            }
        }
        return true;
    }

    default boolean equalAll(T... values) {
        for (T current : values) {
            if (!this.equals(current)) {
                return false;
            }
        }
        return true;
    }
}
