package com.hamrasta.trellis.data.sql.data.repository;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.data.sql.model.IBaseEntity;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IBaseRepository<TEntity extends IBaseEntity> extends ICoreRepository<TEntity> {

}
