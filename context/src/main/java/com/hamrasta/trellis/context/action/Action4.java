package com.hamrasta.trellis.context.action;

import com.hamrasta.trellis.context.action.intefaces.IAction4;

public abstract class Action4<TOutput, TInput1, TInput2, TInput3, TInput4> extends BaseAction<TOutput> implements IAction4<TOutput, TInput1, TInput2, TInput3, TInput4> {

    public abstract TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4) throws Throwable;
}
