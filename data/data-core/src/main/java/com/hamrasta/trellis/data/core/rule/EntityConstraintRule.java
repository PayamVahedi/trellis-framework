package com.hamrasta.trellis.data.core.rule;

import com.hamrasta.trellis.context.rule.ConstraintRule;
import com.hamrasta.trellis.data.core.metadata.EntityStateKind;
import com.hamrasta.trellis.data.core.model.ICoreEntity;

public abstract class EntityConstraintRule<TEntity extends ICoreEntity> extends ConstraintRule<TEntity> {
    EntityStateKind entityState;

    TEntity previousEntity;

    public EntityStateKind getEntityState() {
        return entityState;
    }

    public TEntity getPreviousEntity() {
        return previousEntity;
    }

    @Override
    public boolean isEnable() {
        return getEntityState() != null && getEntityState().isIn(EntityStateKind.PERSIST,EntityStateKind.UPDATE, EntityStateKind.REMOVE);
    }
}
