package com.hamrasta.trellis.context.task;

import com.hamrasta.trellis.context.task.interfaces.ITask2;

public abstract class Task2<TOutput, TInput1, TInput2> extends BaseTask<TOutput> implements ITask2<TOutput, TInput1, TInput2> {

    public abstract TOutput execute(TInput1 t1, TInput2 t2) throws Throwable;

}
