package com.hamrasta.trellis.data.core.payload;

import com.hamrasta.trellis.data.core.metadata.EntityStateKind;

public class EntityState {
    private Object previousEntity;

    private EntityStateKind kind;

    public Object getPreviousEntity() {
        return previousEntity;
    }

    public void setPreviousEntity(Object previousEntity) {
        this.previousEntity = previousEntity;
    }

    public EntityStateKind getKind() {
        return kind;
    }

    public void setKind(EntityStateKind kind) {
        this.kind = kind;
    }

    public EntityState() {
    }

    public EntityState(Object previousEntity, EntityStateKind kind) {
        this.previousEntity = previousEntity;
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "EntityState{" +
                "previousEntity=" + previousEntity +
                ", kind=" + kind +
                '}';
    }
}
