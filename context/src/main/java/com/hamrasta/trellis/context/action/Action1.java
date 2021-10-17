package com.hamrasta.trellis.context.action;

import com.hamrasta.trellis.context.action.intefaces.IAction1;

public abstract class Action1<TOutput, TInput> extends BaseAction<TOutput> implements IAction1<TOutput, TInput> {

    public abstract TOutput execute(TInput param) throws Throwable;

}
