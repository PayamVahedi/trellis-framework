package com.hamrasta.trellis.context.task;

import com.hamrasta.trellis.context.task.interfaces.ITask5;

public abstract class Task5<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5> extends BaseTask<TOutput> implements ITask5<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5> {

    public abstract TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5) throws Throwable;

}
