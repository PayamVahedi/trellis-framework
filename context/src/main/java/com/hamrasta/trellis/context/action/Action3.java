package com.hamrasta.trellis.context.action;

import com.hamrasta.trellis.context.action.intefaces.IAction3;

public abstract class Action3<TOutput, TInput1, TInput2, TInput3> extends BaseAction<TOutput> implements IAction3<TOutput, TInput1, TInput2, TInput3> {

    public abstract TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3) throws Throwable;
}
