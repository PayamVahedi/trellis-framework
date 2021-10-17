package com.hamrasta.trellis.context.task;

import com.hamrasta.trellis.context.task.interfaces.ITask3;

public abstract class Task3<TOutput, TInput1, TInput2, TInput3> extends BaseTask<TOutput> implements ITask3<TOutput, TInput1, TInput2, TInput3> {

    public abstract TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3) throws Throwable;

}
