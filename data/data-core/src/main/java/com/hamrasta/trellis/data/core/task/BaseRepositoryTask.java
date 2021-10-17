package com.hamrasta.trellis.data.core.task;

import com.hamrasta.trellis.context.task.BaseTask;
import com.hamrasta.trellis.core.application.ApplicationContextProvider;
import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.data.core.task.interfaces.IBaseRepositoryTask;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.transaction.RollbackException;
import java.lang.reflect.ParameterizedType;

@Transactional(
        isolation = Isolation.READ_COMMITTED,
        propagation = Propagation.REQUIRES_NEW,
        rollbackFor = {RollbackException.class}
)
public abstract class BaseRepositoryTask<TRepository extends ICoreRepository, TOutput>  extends BaseTask<TOutput> implements IBaseRepositoryTask<TRepository, TOutput> {

    protected TRepository getRepository() {
        return ApplicationContextProvider.context.getBean((Class<TRepository>) ((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments()[0]);
    }

}
