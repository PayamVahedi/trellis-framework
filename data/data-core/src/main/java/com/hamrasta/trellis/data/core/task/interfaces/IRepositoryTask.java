package com.hamrasta.trellis.data.core.task.interfaces;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.context.task.interfaces.ITask;

public interface IRepositoryTask<TRepository extends ICoreRepository, TOutput> extends IBaseRepositoryTask<TRepository, TOutput>, ITask<TOutput> {

}
