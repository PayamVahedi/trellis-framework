package com.hamrasta.trellis.data.core.task;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.data.core.task.interfaces.IRepositoryTask2;

public abstract class RepositoryTask2<TRepository extends ICoreRepository, TOutput, TInput1, TInput2> extends BaseRepositoryTask<TRepository, TOutput> implements IRepositoryTask2<TRepository, TOutput, TInput1, TInput2> {

    public abstract TOutput execute(TInput1 t1, TInput2 t2) throws Throwable;

}
