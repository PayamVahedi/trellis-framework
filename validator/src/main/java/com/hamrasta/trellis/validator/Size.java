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
import java.text.MessageFormat;
import java.util.Collection;

@Documented
@Constraint(validatedBy = {Size.SizeCollectionValidator.class, Size.SizeArrayValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Size {
    String name() default "";

    String message() default "";

    long min() default 0;

    long max() default Long.MAX_VALUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    abstract class AbstractSizeValidator<T> implements ConstraintValidator<Size, T> {
        private Size annotation;

        @Override
        public void initialize(Size annotation) {
            this.annotation = annotation;
        }

        @Override
        public boolean isValid(T field, ConstraintValidatorContext cxt) {
            if (field == null || StringUtils.isEmpty(field.toString()))
                return true;
            int value = length(field);
            if (annotation.min() > value || annotation.max() < value) {
                String message = annotation.message();
                if (StringUtils.isEmpty(message)) {
                    ConstraintViolationCreationContext constraintValidatorContext = ((ConstraintValidatorContextImpl) cxt).getConstraintViolationCreationContexts().parallelStream().findFirst().orElse(null);
                    String field_name = (StringUtils.isNotBlank(annotation.name()) ? annotation.name() : constraintValidatorContext == null ? StringUtils.EMPTY : constraintValidatorContext.getPath().getLeafNode().getName()).replaceAll("([A-Z])", "_$1");
                    message = ((StringUtils.isEmpty(field_name) ? "COLLECTION" : field_name) + "_SIZE_MUST_BE_" + (annotation.min() > value ? "GREATER" : "LESS") + "_OR_EQUAL_THAN {0}").toUpperCase();
                    message = MessageFormat.format(message, annotation.min() > value ? annotation.min() : annotation.max());
                }
                cxt.disableDefaultConstraintViolation();
                cxt.buildConstraintViolationWithTemplate(new BadRequestException(message).toString()).addConstraintViolation();
                return false;
            }
            return true;
        }

        protected abstract int length(T field);

    }

    class SizeCollectionValidator extends AbstractSizeValidator<Collection> {
        @Override
        protected int length(Collection field) {
            return field == null ? 0 : field.size();
        }
    }

    class SizeArrayValidator extends AbstractSizeValidator<Serializable[]> {
        @Override
        protected int length(Serializable[] field) {
            return field == null ? 0 : field.length;
        }
    }
}
