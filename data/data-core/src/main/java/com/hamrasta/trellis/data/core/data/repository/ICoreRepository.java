package com.hamrasta.trellis.data.core.data.repository;

import com.hamrasta.trellis.data.core.model.ICoreEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;


@NoRepositoryBean
public interface ICoreRepository<TEntity extends ICoreEntity> extends JpaRepository<TEntity, String> {

}
