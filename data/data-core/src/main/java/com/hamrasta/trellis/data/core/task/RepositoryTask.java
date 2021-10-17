package com.hamrasta.trellis.data.core.task;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.data.core.task.interfaces.IRepositoryTask;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.RollbackException;

public abstract class RepositoryTask<TRepository extends ICoreRepository, TOutput> extends BaseRepositoryTask<TRepository, TOutput> implements IRepositoryTask<TRepository, TOutput> {

    public abstract TOutput execute() throws Throwable;

}
