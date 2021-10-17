package com.hamrasta.trellis.data.sql.data.criteria;

import com.hamrasta.trellis.data.sql.model.IBaseEntity;

import javax.persistence.EntityManager;

public abstract class BaseCriteria<TEntity extends IBaseEntity> implements IBaseCriteria<TEntity> {
    private final EntityManager entityManager;

    public BaseCriteria(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
}
