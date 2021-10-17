package com.hamrasta.trellis.data.sql.data.criteria;

import com.hamrasta.trellis.data.core.data.criteria.ICoreCriteria;
import com.hamrasta.trellis.data.sql.model.IBaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseCriteria<TEntity extends IBaseEntity> extends ICoreCriteria<TEntity> {

}
