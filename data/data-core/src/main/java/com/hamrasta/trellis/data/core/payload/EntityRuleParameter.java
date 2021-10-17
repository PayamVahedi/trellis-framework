package com.hamrasta.trellis.data.core.payload;

import com.hamrasta.trellis.data.core.metadata.EntityStateKind;
import com.hamrasta.trellis.data.core.model.ICoreEntity;

public class EntityRuleParameter<TEntity extends ICoreEntity> {
    private EntityStateKind state;

    private TEntity previousEntity;

    public EntityStateKind getState() {
        return state;
    }

    public void setState(EntityStateKind state) {
        this.state = state;
    }

    public TEntity getPreviousEntity() {
        return previousEntity;
    }

    public void setPreviousEntity(TEntity previousEntity) {
        this.previousEntity = previousEntity;
    }

    public EntityRuleParameter() {
    }

    public EntityRuleParameter(EntityStateKind state, TEntity previousEntity) {
        this.state = state;
        this.previousEntity = previousEntity;
    }

}
