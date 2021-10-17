package com.hamrasta.trellis.validator;


import com.hamrasta.trellis.http.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.io.Serializable;
import java.lang.annotation.*;
import java.util.Collection;

@Documented
@Constraint(validatedBy = {Value.SerializableValidator.class, Value.CollectionValidator.class, Value.ArrayValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Value {
    String name() default "";

    String message() default "";

    String[] value() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    abstract class AbstractValidator<T> implements ConstraintValidator<Value, T> {
        protected Value annotation;

        @Override
        public void initialize(Value annotation) {
            this.annotation = annotation;
        }

        @Override
        public boolean isValid(T field, ConstraintValidatorContext cxt) {
            if (!isValid(field)) {
                String message = annotation.message();
                if (StringUtils.isEmpty(message)) {
                    ConstraintViolationCreationContext constraintValidatorContext = ((ConstraintValidatorContextImpl) cxt).getConstraintViolationCreationContexts().parallelStream().findFirst().orElse(null);
                    String field_name = (StringUtils.isNotBlank(annotation.name()) ? annotation.name() : constraintValidatorContext == null ? StringUtils.EMPTY : constraintValidatorContext.getPath().getLeafNode().getName()).replaceAll("([A-Z])", "_$1");
                    message = ((StringUtils.isEmpty(field_name) ? "FIELD" : field_name) + "_IS_INVALID").toUpperCase();
                }
                cxt.disableDefaultConstraintViolation();
                cxt.buildConstraintViolationWithTemplate(new BadRequestException(message).toString()).addConstraintViolation();
                return false;
            }
            return true;
        }

        protected boolean validation(Object field) {
            if (field == null || annotation.value().length <= 0)
                return true;
            for (String value : annotation.value()) {
                if (value.equalsIgnoreCase(field.toString()))
                    return true;
            }
            return false;
        }

        protected abstract boolean isValid(T field);
    }

    class SerializableValidator extends AbstractValidator<Object> {

        @Override
        protected boolean isValid(Object field) {
            if (field == null)
                return true;
            if (field instanceof Object[])  {
                return isValid((Object[]) field);

            } else if (field instanceof Collection) {
                return isValid(((Collection<?>) field).toArray());
            }
            return validation(field);
        }

        private boolean isValid(Object[] fields) {
            if (fields.length <= 0 || annotation.value().length <= 0)
                return true;
            for (Object currentField : fields) {
                if (validation(currentField))
                    return true;
            }
            return false;
        }
    }

    class CollectionValidator extends AbstractValidator<Collection<Object>> {
        @Override
        protected boolean isValid(Collection<Object> fields) {
            if (fields == null || fields.isEmpty() || annotation.value().length <= 0)
                return true;
            for (Object field : fields) {
                if (validation(field))
                    return true;
            }
            return false;
        }
    }

    class ArrayValidator extends AbstractValidator<Object[]> {
        @Override
        protected boolean isValid(Object[] fields) {
            if (fields == null || fields.length <= 0 || annotation.value().length <= 0)
                return true;
            for (Object field : fields) {
                if (validation(field))
                    return true;
            }
            return false;
        }
    }
}