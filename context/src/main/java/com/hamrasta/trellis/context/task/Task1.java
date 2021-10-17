package com.hamrasta.trellis.context.task;

import com.hamrasta.trellis.context.task.interfaces.ITask1;

public abstract class Task1<TOutput, TInput> extends BaseTask<TOutput> implements ITask1<TOutput, TInput> {

    public abstract TOutput execute(TInput t1) throws Throwable;

}
