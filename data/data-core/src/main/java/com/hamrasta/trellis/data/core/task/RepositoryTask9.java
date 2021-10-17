package com.hamrasta.trellis.data.core.task;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.data.core.task.interfaces.IRepositoryTask9;


public abstract class RepositoryTask9<TRepository extends ICoreRepository, TOutput, TInput1, TInput2, TInput3, TInput4, TInput5, TInput6, TInput7, TInput8, TInput9> extends BaseRepositoryTask<TRepository, TOutput> implements IRepositoryTask9<TRepository, TOutput, TInput1, TInput2, TInput3, TInput4, TInput5, TInput6, TInput7, TInput8, TInput9> {

    public abstract TOutput execute(TInput1 t1, TInput2 t2, TInput3 t3, TInput4 t4, TInput5 t5, TInput6 t6, TInput7 t7, TInput8 t8, TInput9 t9) throws Throwable;

}
