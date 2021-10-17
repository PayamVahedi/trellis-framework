package com.hamrasta.trellis.data.core.task;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.context.task.interfaces.ITask1;

public abstract class RepositoryTask1<TRepository extends ICoreRepository, TOutput, TInput> extends BaseRepositoryTask<TRepository, TOutput> implements ITask1<TOutput, TInput> {

    public abstract TOutput execute(TInput t1) throws Throwable;

}
