package com.hamrasta.trellis.data.core.task.interfaces;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.context.task.interfaces.ITask5;

public interface IRepositoryTask5<TRepository extends ICoreRepository, TOutput, TInput1, TInput2, TInput3, TInput4, TInput5> extends IBaseRepositoryTask<TRepository, TOutput>, ITask5<TOutput, TInput1, TInput2, TInput3, TInput4, TInput5> {

}
