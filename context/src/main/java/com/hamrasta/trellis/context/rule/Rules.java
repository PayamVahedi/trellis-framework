package com.hamrasta.trellis.context.rule;

import com.hamrasta.trellis.context.payload.DiscoveryRule;
import com.hamrasta.trellis.util.reflection.ReflectionUtil;
import com.hamrasta.trellis.http.exception.HttpErrorMessage;
import com.hamrasta.trellis.http.exception.HttpException;
import com.hamrasta.trellis.http.exception.ServiceUnavailableException;
import com.hamrasta.trellis.core.log.Logger;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.stream.Collectors;


@Documented
@Constraint(validatedBy = Rules.RuleValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Rules {
    String message() default "";

    Class<? extends ConstraintRule<?>>[] constraints() default {};

    Class<? extends DerivationRule<?>>[] derivations() default {};

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};

    class RuleValidator implements ConstraintValidator<Rules, Object> {
        private Rules annotation;

        @Override
        public void initialize(Rules annotation) {
            this.annotation = annotation;
        }

        @Override
        public boolean isValid(Object field, ConstraintValidatorContext cxt) {
            DiscoveryRule discovery = getAllRules(cxt);
            if (discovery.isSuccess()) {
                for (Rule<?> instance : discovery.getRules()) {
                    boolean isSuccess;
                    if (instance instanceof DerivationRule)
                        isSuccess = fireDerivationRule((DerivationRule<Object>) instance, field, cxt);
                    else
                        isSuccess = fireConstraintRule((ConstraintRule<Object>) instance, field, cxt);
                    if (!isSuccess)
                        return false;
                }
                return true;
            }
            return false;
        }

        public DiscoveryRule getAllRules(ConstraintValidatorContext cxt) {
            if (annotation == null || (annotation.derivations().length <= 0 && annotation.constraints().length <= 0))
                return DiscoveryRule.of(true);
            Set<Rule<?>> instances = new HashSet<>();
            for (Class<? extends DerivationRule<?>> rule : annotation.derivations()) {
                DerivationRule<?> instance = getInstance(rule, cxt);
                if (ObjectUtils.isEmpty(instance))
                    return DiscoveryRule.of(false);
                instances.add(instance);
            }

            for (Class<? extends ConstraintRule<?>> rule : annotation.constraints()) {
                ConstraintRule<?> instance = getInstance(rule, cxt);
                if (ObjectUtils.isEmpty(instance))
                    return DiscoveryRule.of(false);
                instances.add(instance);
            }
            instances = instances.stream().sorted(Comparator.comparing(Rule::getOrder)).collect(Collectors.toCollection(LinkedHashSet::new));
            return DiscoveryRule.of(instances);
        }

        private boolean fireDerivationRule(DerivationRule<Object> instance, Object field, ConstraintValidatorContext cxt) {
            try {
                if (!instance.isEnable() && instance.getFields() == null || instance.getFields().isEmpty())
                    return true;
                for (String fieldName : instance.getFields()) {
                    if (instance.condition(field))
                        ReflectionUtil.setPropertyValue(field, fieldName, instance.getDerivedValue(field));
                }
            } catch (Throwable e) {
                Logger.error("ValidationDerivationRulesException", "Rule: " + instance.getClass().getSimpleName() + " Message: " + e.getMessage());
                return parseErrorMessage(e, instance.getClass().getSimpleName(), cxt);
            }
            return true;
        }

        private boolean fireConstraintRule(ConstraintRule<Object> instance, Object field, ConstraintValidatorContext cxt) {
            try {
                if (instance.isEnable() && instance.condition(field)) {
                    String message = instance.message(field);
                    if (StringUtils.isEmpty(message)) {
                        String rule_name = StringUtils.uncapitalize(instance.getClass().getSimpleName()).replaceAll("([A-Z])", "_$1");
                        message = (rule_name.toUpperCase() + "_FAILED").trim();
                    }
                    cxt.disableDefaultConstraintViolation();
                    cxt.buildConstraintViolationWithTemplate(new HttpException(new HttpErrorMessage(instance.httpStatus(), message)).toString()).addConstraintViolation();
                    return false;
                }
                return true;
            } catch (Throwable e) {
                Logger.error("ValidationConstraintRulesException", "Rule: " + instance.getClass().getSimpleName() + " Message: " + e.getMessage());
                return parseErrorMessage(e, instance.getClass().getSimpleName(), cxt);
            }
        }

        private boolean parseErrorMessage(Throwable e, String ruleName, ConstraintValidatorContext cxt) {
            String message = StringUtils.EMPTY;
            if (e instanceof HttpException) {
                message = e.toString();
            }
            if (StringUtils.isBlank(message)) {
                String rule_name = StringUtils.uncapitalize(ruleName).replaceAll("([A-Z])", "_$1");
                message = new ServiceUnavailableException((rule_name.toUpperCase() + "_STRUCTURE_HAS_ERROR").trim()).toString();
            }
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate(message).addConstraintViolation();
            return false;
        }

        private void parseStructureErrorMessage(String ruleName, ConstraintValidatorContext cxt) {
            String rule_name = StringUtils.uncapitalize(ruleName).replaceAll("([A-Z])", "_$1");
            String message = (rule_name.toUpperCase() + "_STRUCTURE_HAS_ERROR").trim();
            cxt.disableDefaultConstraintViolation();
            cxt.buildConstraintViolationWithTemplate(new ServiceUnavailableException(message).toString()).addConstraintViolation();
        }

        private <T extends Rule<?>> T getInstance(Class<T> rule, ConstraintValidatorContext cxt) {
            try {
                return rule.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                Logger.error("GetInstanceDerivationRulesException", "Rule: " + rule.getSimpleName() + " Message: " + e.getMessage());
                parseStructureErrorMessage(rule.getSimpleName(), cxt);
                return null;
            }
        }
    }
}
