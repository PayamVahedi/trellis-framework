package com.hamrasta.trellis.data.core.task.interfaces;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.context.task.interfaces.ITask2;

public interface IRepositoryTask2<TRepository extends ICoreRepository, TOutput, TInput1, TInput2> extends IBaseRepositoryTask<TRepository, TOutput>, ITask2<TOutput, TInput1, TInput2> {

}
