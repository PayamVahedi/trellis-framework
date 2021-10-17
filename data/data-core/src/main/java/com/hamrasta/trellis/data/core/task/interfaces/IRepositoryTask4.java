package com.hamrasta.trellis.data.core.task.interfaces;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.context.task.interfaces.ITask4;

public interface IRepositoryTask4<TRepository extends ICoreRepository, TOutput, TInput1, TInput2, TInput3, TInput4>  extends IBaseRepositoryTask<TRepository, TOutput>, ITask4<TOutput, TInput1, TInput2, TInput3, TInput4> {

}
