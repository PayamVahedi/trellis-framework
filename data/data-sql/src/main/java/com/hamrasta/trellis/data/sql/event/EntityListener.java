package com.hamrasta.trellis.data.sql.event;

import com.hamrasta.trellis.data.core.metadata.EntityStateKind;
import com.hamrasta.trellis.data.core.payload.EntityState;
import com.hamrasta.trellis.data.sql.payload.InProgressValidation;
import org.apache.commons.lang3.SerializationUtils;
import org.hibernate.validator.HibernateValidatorFactory;

import javax.persistence.*;
import javax.validation.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class EntityListener {

    static Set<InProgressValidation> inProgressValidations = new HashSet<>();

    @Transient
    private Object previousEntity;

    @PrePersist
    private void prePersist(Object entity) {
        validation(previousEntity, entity, EntityStateKind.PERSIST);
    }

    @PostPersist
    private void postPersist(Object entity) {

    }

    @PreUpdate
    private void preUpdate(Object entity) {
        validation(previousEntity, entity, EntityStateKind.UPDATE);
    }

    @PostUpdate
    private void postUpdate(Object entity) {

    }

    @PreRemove
    private void preRemove(Object entity) {
        validation(previousEntity, entity, EntityStateKind.REMOVE);
    }

    @PostRemove
    private void postRemove(Object entity) {

    }

    @PostLoad
    private void postLoad(Object entity) {
        previousEntity = SerializationUtils.clone((Serializable) entity);
        validation(previousEntity, entity, EntityStateKind.LOAD);
    }

    private void validation(Object previousEntity, Object entity, EntityStateKind kind) {
        InProgressValidation inProgressValidation = new InProgressValidation(entity, kind);
        if (!inProgressValidations.contains(inProgressValidation)) {
            inProgressValidations.add(inProgressValidation);
            ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
            Validator validator = factory.unwrap(HibernateValidatorFactory.class).usingContext().constraintValidatorPayload(new EntityState(previousEntity, kind)).getValidator();
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(entity);
            inProgressValidations.remove(inProgressValidation);
            if (constraintViolations != null && !constraintViolations.isEmpty()) {
                throw new ConstraintViolationException(constraintViolations);
            }

        }
    }

}
