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
import java.math.BigDecimal;
import java.text.MessageFormat;

@Documented
@Constraint(validatedBy = Range.RangeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Range {
    String name() default "";

    String message() default "";

    long min() default Long.MIN_VALUE;

    long max() default Long.MAX_VALUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class RangeValidator implements ConstraintValidator<Range, Number> {
        private Range annotation;

        @Override
        public void initialize(Range annotation) {
            this.annotation = annotation;
        }

        @Override
        public boolean isValid(Number field, ConstraintValidatorContext cxt) {
            if (field == null || StringUtils.isEmpty(field.toString()))
                return true;
            Long value = new BigDecimal(field.toString()).longValue();
            if (annotation.min() > value || annotation.max() < value) {
                String message = annotation.message();
                if (StringUtils.isEmpty(message)) {
                    ConstraintViolationCreationContext constraintValidatorContext = ((ConstraintValidatorContextImpl) cxt).getConstraintViolationCreationContexts().parallelStream().findFirst().orElse(null);
                    String field_name = (StringUtils.isNotBlank(annotation.name()) ? annotation.name() : constraintValidatorContext == null ? StringUtils.EMPTY : constraintValidatorContext.getPath().getLeafNode().getName()).replaceAll("([A-Z])", "_$1");
                    message = ((StringUtils.isEmpty(field_name) ? "FIELD" : field_name) + "_MUST_BE_" + (annotation.min() > value ? "GREATER" : "LESS") + "_OR_EQUAL_THAN {0}").toUpperCase();
                    message = MessageFormat.format(message, annotation.min() > value ? annotation.min() : annotation.max());
                }
                cxt.disableDefaultConstraintViolation();
                cxt.buildConstraintViolationWithTemplate(new BadRequestException(message).toString()).addConstraintViolation();
                return false;
            }
            return true;
        }
    }
}
