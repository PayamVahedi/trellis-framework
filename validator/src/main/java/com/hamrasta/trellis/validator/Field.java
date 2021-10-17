package com.hamrasta.trellis.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.io.Serializable;
import java.lang.annotation.*;
import java.util.Collection;

@Documented
@Constraint(validatedBy = {Field.Validator.class, Field.CollectionValidator.class, Field.ArrayValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
    String name() default "";

    String message() default "";

    boolean nullable() default true;

    boolean insertable() default true;

    boolean updatable() default true;

    boolean visible() default true;

    boolean unique() default false;

    int length() default 255;

    int precision() default 0;

    int scale() default 0;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    abstract class AbstractFieldValidator<T> implements ConstraintValidator<Field, T> {
        private Field annotation;

        @Override
        public void initialize(Field annotation) {
            this.annotation = annotation;
        }

        @Override
        public boolean isValid(T field, ConstraintValidatorContext cxt) {
            return true;
        }

        protected abstract boolean isValid(T field);
    }

    class Validator extends Field.AbstractFieldValidator<Serializable> {

        @Override
        protected boolean isValid(Serializable field) {
            return true;
        }
    }

    class CollectionValidator extends Field.AbstractFieldValidator<Collection> {
        @Override
        protected boolean isValid(Collection field) {
            return field == null || field.isEmpty();
        }
    }

    class ArrayValidator extends Field.AbstractFieldValidator<Serializable[]> {
        @Override
        protected boolean isValid(Serializable[] field) {
            return field == null || field.length <= 0;
        }
    }
}
