package com.hamrasta.trellis.context.task;

import com.hamrasta.trellis.context.task.interfaces.ITask4;

public abstract class Task4<TOutput, TInput1, TInput2, TInput3, TInput4>  extends BaseTask<TOutput> implements ITask4<TOutput, TInput1, TInput2, TInput3, TInput4> {

    public abstract TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4) throws Throwable;

}
