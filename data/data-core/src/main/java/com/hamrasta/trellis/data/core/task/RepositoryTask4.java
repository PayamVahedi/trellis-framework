package com.hamrasta.trellis.data.core.task;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.data.core.task.interfaces.IRepositoryTask4;

public abstract class RepositoryTask4<TRepository extends ICoreRepository, TOutput, TInput1, TInput2, TInput3, TInput4>  extends BaseRepositoryTask<TRepository, TOutput> implements IRepositoryTask4<TRepository, TOutput, TInput1, TInput2, TInput3, TInput4> {

    public abstract TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4) throws Throwable;

}
