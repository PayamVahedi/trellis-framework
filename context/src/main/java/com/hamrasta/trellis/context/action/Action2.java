package com.hamrasta.trellis.context.action;

import com.hamrasta.trellis.context.action.intefaces.IAction2;

public abstract class Action2<TOutput, TInput1, TInput2> extends BaseAction<TOutput> implements IAction2<TOutput, TInput1, TInput2> {

    public abstract TOutput execute(TInput1 t1, TInput2 t2) throws Throwable;

}
