package com.hamrasta.trellis.data.core.task.interfaces;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.context.task.interfaces.ITask3;

public interface IRepositoryTask3<TRepository extends ICoreRepository, TOutput, TInput1, TInput2, TInput3> extends IBaseRepositoryTask<TRepository, TOutput>, ITask3<TOutput, TInput1, TInput2, TInput3> {

}
