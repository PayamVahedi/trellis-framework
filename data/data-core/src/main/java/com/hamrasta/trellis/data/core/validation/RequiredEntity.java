package com.hamrasta.trellis.data.core.validation;


import com.hamrasta.trellis.http.exception.BadRequestException;
import com.hamrasta.trellis.data.core.model.ICoreEntity;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.Collection;

@Documented
@Constraint(validatedBy = {RequiredEntity.RequiredValidator.class, RequiredEntity.RequiredCollectionValidator.class, RequiredEntity.RequiredArrayValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiredEntity {
    String name() default "";

    String message() default "";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    abstract class AbstractRequiredValidator<T> implements ConstraintValidator<RequiredEntity, T> {
        private RequiredEntity annotation;

        @Override
        public void initialize(RequiredEntity annotation) {
            this.annotation = annotation;
        }

        @Override
        public boolean isValid(T field, ConstraintValidatorContext cxt) {
            if (!isValid(field)) {
                String message = annotation.message();
                if (StringUtils.isEmpty(message)) {
                    ConstraintViolationCreationContext constraintValidatorContext = ((ConstraintValidatorContextImpl) cxt).getConstraintViolationCreationContexts().parallelStream().findFirst().orElse(null);
                    String field_name = (StringUtils.isNotBlank(annotation.name()) ? annotation.name() : constraintValidatorContext == null ? StringUtils.EMPTY : constraintValidatorContext.getPath().getLeafNode().getName()).replaceAll("([A-Z])", "_$1");
                    message = ((StringUtils.isEmpty(field_name) ? "FIELD" : field_name) + "_IS_REQUIRED").toUpperCase();
                }
                cxt.disableDefaultConstraintViolation();
                cxt.buildConstraintViolationWithTemplate(new BadRequestException(message).toString()).addConstraintViolation();
                return false;
            }
            return true;
        }

        protected abstract boolean isValid(T field);
    }

    class RequiredValidator extends AbstractRequiredValidator<Object> {

        @Override
        protected boolean isValid(Object field) {
            boolean isValid = field != null && StringUtils.isNotBlank(field.toString());
            if (isValid && field instanceof ICoreEntity)
                return StringUtils.isNotBlank(((ICoreEntity) field).getId());
            return isValid;
        }
    }

    class RequiredCollectionValidator extends AbstractRequiredValidator<Collection<Object>> {
        @Override
        protected boolean isValid(Collection<Object> fields) {
            boolean isValid = fields != null && !fields.isEmpty();
            if (isValid) {
                for (Object field : fields) {
                    if (field instanceof ICoreEntity && StringUtils.isNotBlank(((ICoreEntity) field).getId()))
                        return true;
                }
            }
            return isValid;
        }
    }

    class RequiredArrayValidator extends AbstractRequiredValidator<Object[]> {
        @Override
        protected boolean isValid(Object[] fields) {
            boolean isValid = fields != null && fields.length > 0;
            if (isValid) {
                for (Object field : fields) {
                    if (field instanceof ICoreEntity && StringUtils.isNotBlank(((ICoreEntity) field).getId()))
                        return true;
                }
            }
            return isValid;
        }
    }
}
