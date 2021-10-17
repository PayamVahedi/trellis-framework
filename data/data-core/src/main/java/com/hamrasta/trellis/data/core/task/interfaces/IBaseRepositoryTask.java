package com.hamrasta.trellis.data.core.task.interfaces;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.context.task.interfaces.IBaseTask;
import com.hamrasta.trellis.data.core.util.PagingModelMapper;

public interface IBaseRepositoryTask<TRepository extends ICoreRepository, TOutput> extends IBaseTask<TOutput>, PagingModelMapper {

}
