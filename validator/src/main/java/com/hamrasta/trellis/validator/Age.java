package com.hamrasta.trellis.validator;

import com.hamrasta.trellis.http.exception.BadRequestException;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Documented
@Constraint(validatedBy = Age.RangeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Age {
    String name() default "";

    String message() default "";

    long min() default 0;

    long max() default 0;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class RangeValidator implements ConstraintValidator<Age, LocalDate> {
        private Age annotation;

        @Override
        public void initialize(Age annotation) {
            this.annotation = annotation;
        }

        @Override
        public boolean isValid(LocalDate field, ConstraintValidatorContext cxt) {
            if (field == null)
                return true;
            LocalDate today = LocalDate.now();
            long age = ChronoUnit.YEARS.between(field, today);
            if ( age < annotation.min() || annotation.max() < age) {
                String message = annotation.message();
                if (StringUtils.isEmpty(message)) {
                    ConstraintViolationCreationContext constraintValidatorContext = ((ConstraintValidatorContextImpl) cxt).getConstraintViolationCreationContexts().parallelStream().findFirst().orElse(null);
                    String field_name = (StringUtils.isNotBlank(annotation.name()) ? annotation.name() : constraintValidatorContext == null ? StringUtils.EMPTY : constraintValidatorContext.getPath().getLeafNode().getName()).replaceAll("([A-Z])", "_$1");
                    message = ((StringUtils.isEmpty(field_name) ? "AGE" : field_name) + "_MUST_BE_" + (annotation.min() > age ? "GREATER" : "LESS") + "_OR_EQUAL_THAN {0}").toUpperCase();
                    message = MessageFormat.format(message, annotation.min() > age ? annotation.min() : annotation.max());
                }
                cxt.disableDefaultConstraintViolation();
                cxt.buildConstraintViolationWithTemplate(new BadRequestException(message).toString()).addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}
