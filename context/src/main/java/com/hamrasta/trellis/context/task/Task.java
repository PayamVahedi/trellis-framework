package com.hamrasta.trellis.context.task;

import com.hamrasta.trellis.context.task.interfaces.ITask;

public abstract class Task<TOutput> extends BaseTask<TOutput> implements ITask<TOutput> {

    public abstract TOutput execute() throws Throwable;

}
