package com.hamrasta.trellis.context.rule;

public abstract class Rule<T> {

    public abstract boolean condition(T t) throws Throwable;

    public Integer getOrder() {
        return Integer.MAX_VALUE;
    }

}
