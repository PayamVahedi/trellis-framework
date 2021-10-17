package com.hamrasta.trellis.context.action;


import com.hamrasta.trellis.context.action.intefaces.IAction;

public abstract class Action<TOutput> extends BaseAction<TOutput> implements IAction<TOutput> {

    public abstract TOutput execute() throws Throwable;

}
