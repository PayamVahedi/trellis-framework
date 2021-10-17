package com.hamrasta.trellis.data.core.data.criteria;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.data.core.model.ICoreEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ICoreCriteria<TEntity extends ICoreEntity> extends ICoreRepository<TEntity> {

}
