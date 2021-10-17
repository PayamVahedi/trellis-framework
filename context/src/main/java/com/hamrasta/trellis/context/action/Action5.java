package com.hamrasta.trellis.context.action;

import com.hamrasta.trellis.context.action.intefaces.IAction5;

public abstract class Action5<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5> extends BaseAction<TOutput> implements IAction5<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5> {

    public abstract TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5) throws Throwable;
}
