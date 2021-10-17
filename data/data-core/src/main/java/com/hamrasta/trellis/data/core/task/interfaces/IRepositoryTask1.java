package com.hamrasta.trellis.data.core.task.interfaces;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.context.task.interfaces.ITask1;

public interface IRepositoryTask1<TRepository extends ICoreRepository, TOutput, TInput> extends IBaseRepositoryTask<TRepository, TOutput>, ITask1<TOutput, TInput> {

}
