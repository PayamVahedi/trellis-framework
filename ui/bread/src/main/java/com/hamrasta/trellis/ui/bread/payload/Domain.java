package com.hamrasta.trellis.ui.bread.payload;

import com.hamrasta.trellis.core.payload.ResponseMessage;
import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.data.core.model.ICoreEntity;

public class Domain extends ResponseMessage {
    private Class<? extends ICoreEntity> entity;

    private Class<? extends ICoreRepository<? extends ICoreEntity>> repository;

    public Class<? extends ICoreEntity> getEntity() {
        return entity;
    }

    public void setEntity(Class<? extends ICoreEntity> entity) {
        this.entity = entity;
    }

    public Class<? extends ICoreRepository<? extends ICoreEntity>> getRepository() {
        return repository;
    }

    public void setRepository(Class<? extends ICoreRepository<? extends ICoreEntity>> repository) {
        this.repository = repository;
    }

    public Domain() {
    }

    public Domain(Class<? extends ICoreEntity> entity, Class<? extends ICoreRepository<? extends ICoreEntity>> repository) {
        this.entity = entity;
        this.repository = repository;
    }

    @Override
    public String toString() {
        return "Domain{" +
                "entity=" + entity +
                ", repository=" + repository +
                '}';
    }
}
