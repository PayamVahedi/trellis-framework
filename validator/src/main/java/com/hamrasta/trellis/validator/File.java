package com.hamrasta.trellis.validator;

import com.hamrasta.trellis.http.exception.BadRequestException;
import com.hamrasta.trellis.core.metadata.MemoryUnit;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintViolationCreationContext;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.text.MessageFormat;
import java.util.List;

@Documented
@Constraint(validatedBy = {File.ImageValidator.class, File.ImageListValidator.class, File.ImageArrayValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface File {
    String name() default "";

    String message() default "";

    MemoryUnit unit() default MemoryUnit.KB;

    long min() default 0;

    long max() default 108488200;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    abstract class AbstractImageValidator<T> implements ConstraintValidator<File, T> {
        private File annotation;

        @Override
        public void initialize(File annotation) {
            this.annotation = annotation;
        }

        @Override
        public boolean isValid(T field, ConstraintValidatorContext cxt) {
            if (field == null)
                return true;
            return isValidImageAndSize(field, cxt);
        }

        protected abstract boolean isValidImageAndSize(T field, ConstraintValidatorContext cxt);

        protected boolean isValidFile(MultipartFile field, ConstraintValidatorContext cxt) {
            if (field == null || field.isEmpty())
                return true;
            if (!Validator.isImage(field)) {
                String message = annotation.message();
                if (StringUtils.isEmpty(message)) {
                    ConstraintViolationCreationContext constraintValidatorContext = ((ConstraintValidatorContextImpl) cxt).getConstraintViolationCreationContexts().parallelStream().findFirst().orElse(null);
                    String field_name = (StringUtils.isNotBlank(annotation.name()) ? annotation.name() : constraintValidatorContext == null ? StringUtils.EMPTY : constraintValidatorContext.getPath().getLeafNode().getName()).replaceAll("([A-Z])", "_$1");
                    message = ((StringUtils.isEmpty(field_name) ? "IMAGE" : field_name) + "_TYPE_IS_INVALID").toUpperCase();
                }
                cxt.disableDefaultConstraintViolation();
                cxt.buildConstraintViolationWithTemplate(new BadRequestException(message).toString()).addConstraintViolation();
                return false;
            }
            long fileSize = field.getSize();
            long min = annotation.min() * annotation.unit().getSize();
            long max = annotation.max() * annotation.unit().getSize();
            if (min > fileSize || max < fileSize) {
                String message = cxt.getDefaultConstraintMessageTemplate();
                if (StringUtils.isEmpty(message)) {
                    ConstraintViolationCreationContext constraintValidatorContext = ((ConstraintValidatorContextImpl) cxt).getConstraintViolationCreationContexts().parallelStream().findFirst().orElse(null);
                    String field_name = (StringUtils.isNotBlank(annotation.name()) ? annotation.name() : constraintValidatorContext == null ? StringUtils.EMPTY : constraintValidatorContext.getPath().getLeafNode().getName()).replaceAll("([A-Z])", "_$1");
                    message = ((StringUtils.isEmpty(field_name) ? "IMAGE" : field_name) + "_SIZE_MUST_BE_" + (annotation.min() > fileSize ? "GREATER" : "LESS") + "_OR_EQUAL_THAN {0} {1}").toUpperCase();
                    message = MessageFormat.format(message, annotation.min() > fileSize ? annotation.min() : annotation.max(), annotation.unit());
                }
                cxt.disableDefaultConstraintViolation();
                cxt.buildConstraintViolationWithTemplate(new BadRequestException(message).toString()).addConstraintViolation();
                return false;
            }
            return true;
        }
    }

    class ImageValidator extends AbstractImageValidator<MultipartFile> {
        @Override
        public boolean isValidImageAndSize(MultipartFile field, ConstraintValidatorContext cxt) {
            if (field == null || field.isEmpty())
                return true;
            return isValidFile(field, cxt);
        }
    }

    class ImageListValidator extends AbstractImageValidator<List<MultipartFile>> {
        @Override
        public boolean isValidImageAndSize(List<MultipartFile> files, ConstraintValidatorContext cxt) {
            if (files == null || files.isEmpty())
                return true;
            for (MultipartFile file : files) {
                if (!isValidFile(file, cxt))
                    return false;
            }
            return true;
        }
    }

    class ImageArrayValidator extends AbstractImageValidator<MultipartFile[]> {
        @Override
        public boolean isValidImageAndSize(MultipartFile[] files, ConstraintValidatorContext cxt) {
            if (files == null || files.length <= 0)
                return true;
            for (MultipartFile file : files) {
                if (!isValidFile(file, cxt))
                    return false;
            }
            return true;
        }
    }
}
