package com.hamrasta.trellis.data.core.rule;

import com.hamrasta.trellis.context.rule.DerivationRule;
import com.hamrasta.trellis.data.core.metadata.EntityStateKind;
import com.hamrasta.trellis.data.core.model.ICoreEntity;
import de.cronn.reflection.util.PropertyGetter;
import java.util.Set;

public abstract class EntityDerivationRule<TEntity extends ICoreEntity> extends DerivationRule<TEntity> {
    EntityStateKind entityState;

    TEntity previousEntity;

    public EntityDerivationRule(PropertyGetter<TEntity> field) {
        super(field);
    }

    public EntityDerivationRule(Set<PropertyGetter<TEntity>> fields) {
        super(fields);
    }

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
