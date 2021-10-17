package com.hamrasta.trellis.data.core.task;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.data.core.task.interfaces.IRepositoryTask3;

public abstract class RepositoryTask3<TRepository extends ICoreRepository, TOutput, TInput1, TInput2, TInput3> extends BaseRepositoryTask<TRepository, TOutput> implements IRepositoryTask3<TRepository, TOutput, TInput1, TInput2, TInput3> {

    public abstract TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3) throws Throwable;

}
