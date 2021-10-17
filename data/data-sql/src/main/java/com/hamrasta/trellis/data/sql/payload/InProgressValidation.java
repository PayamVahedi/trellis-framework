package com.hamrasta.trellis.data.sql.payload;

import com.hamrasta.trellis.core.payload.IPayload;
import com.hamrasta.trellis.data.core.metadata.EntityStateKind;

import java.util.Objects;

public class InProgressValidation implements IPayload {
    private Object entity;

    private EntityStateKind kind;

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }

    public EntityStateKind getKind() {
        return kind;
    }

    public void setKind(EntityStateKind kind) {
        this.kind = kind;
    }

    public InProgressValidation() {
    }

    public InProgressValidation(Object entity, EntityStateKind kind) {
        this.entity = entity;
        this.kind = kind;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InProgressValidation that = (InProgressValidation) o;
        return Objects.equals(entity, that.entity) &&
                kind == that.kind;
    }

    @Override
    public int hashCode() {
        return Objects.hash(entity.hashCode(), kind);
    }
}
