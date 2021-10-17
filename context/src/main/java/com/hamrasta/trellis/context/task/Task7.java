package com.hamrasta.trellis.context.task;

import com.hamrasta.trellis.context.task.interfaces.ITask7;


public abstract class Task7<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5, TInput6, TInput7> extends BaseTask<TOutput> implements ITask7<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5, TInput6, TInput7> {

    public abstract TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5, TInput6 t6, TInput7 t7) throws Throwable;

}
