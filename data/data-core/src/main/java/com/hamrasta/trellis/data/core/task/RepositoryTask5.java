package com.hamrasta.trellis.data.core.task;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.data.core.task.interfaces.IRepositoryTask5;

public abstract class RepositoryTask5<TRepository extends ICoreRepository, TOutput, TInput1, TInput2, TInput3, TInput4, TInput5> extends BaseRepositoryTask<TRepository, TOutput> implements IRepositoryTask5<TRepository, TOutput, TInput1, TInput2, TInput3, TInput4, TInput5> {

    public abstract TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5) throws Throwable;

}
