package com.hamrasta.trellis.data.core.rule;

import com.hamrasta.trellis.context.payload.DiscoveryRule;
import com.hamrasta.trellis.context.rule.ConstraintRule;
import com.hamrasta.trellis.context.rule.DerivationRule;
import com.hamrasta.trellis.context.rule.Rule;
import com.hamrasta.trellis.data.core.payload.EntityRuleParameter;
import com.hamrasta.trellis.data.core.payload.EntityState;
import com.hamrasta.trellis.util.reflection.ReflectionUtil;
import com.hamrasta.trellis.http.exception.HttpErrorMessage;
import com.hamrasta.trellis.http.exception.HttpException;
import com.hamrasta.trellis.http.exception.ServiceUnavailableException;
import com.hamrasta.trellis.core.log.Logger;
import com.hamrasta.trellis.data.core.metadata.EntityStateKind;
import com.hamrasta.trellis.data.core.model.ICoreEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Documented
@Constraint(validatedBy = EntityRules.RuleValidator.class)
@Target({ElementType.CONSTRUCTOR, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EntityRules {
    String message() default "";

    Class<? extends EntityConstraintRule<? extends ICoreEntity>>[] constraints() default {};

    Class<? extends EntityDerivationRule<? extends ICoreEntity>>[] derivations() default {};

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class RuleValidator implements ConstraintValidator<EntityRules, ICoreEntity> {
        private EntityRules annotation;

        @Override
        public void initialize(EntityRules annotation) {
            this.annotation = annotation;
        }

        @Override
        public boolean isValid(ICoreEntity field, ConstraintValidatorContext cxt) {
            DiscoveryRule discovery = getAllRules(cxt);
            if (discovery.isSuccess()) {
                for (Rule<?> instance : discovery.getRules()) {
                    boolean isSuccess;
                    if (instance instanceof DerivationRule)
                        isSuccess = fireDerivationRule((EntityDerivationRule) instance, field, cxt);
                    else
                        isSuccess = fireConstraintRule((EntityConstraintRule) instance, field, cxt);
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
            for (Class<? extends EntityDerivationRule<? extends ICoreEntity>> rule : annotation.derivations()) {
                DerivationRule<?> instance = getInstance(rule, cxt);
                if (ObjectUtils.isEmpty(instance))
                    return DiscoveryRule.of(false);
                instances.add(instance);
            }

            for (Class<? extends ConstraintRule<? extends ICoreEntity>> rule : annotation.constraints()) {
                ConstraintRule<?> instance = getInstance(rule, cxt);
                if (ObjectUtils.isEmpty(instance))
                    return DiscoveryRule.of(false);
                instances.add(instance);
            }
            instances = instances.stream().sorted(Comparator.comparing(Rule::getOrder)).collect(Collectors.toCollection(LinkedHashSet::new));
            return DiscoveryRule.of(instances);
        }

        private <TEntity extends ICoreEntity> boolean fireDerivationRule(EntityDerivationRule<TEntity> instance, TEntity entity, ConstraintValidatorContext cxt) {
            try {
                EntityRuleParameter<TEntity> parameter = getEntityParameter(cxt);
                if (!instance.isEnable() && ObjectUtils.isEmpty(instance.getFields()))
                    return true;
                instance.entityState = parameter.getState();
                instance.previousEntity = parameter.getPreviousEntity();
                if (instance.condition(entity)) {
                    for (String fieldName : instance.getFields()) {
                        ReflectionUtil.setPropertyValue(entity, fieldName, instance.getDerivedValue(entity));
                    }
                }
            } catch (Throwable e) {
                Logger.error("ValidationDerivationRulesException", "Rule: " + instance.getClass().getSimpleName() + " Message: " + e.getMessage());
                return parseErrorMessage(e, instance.getClass().getSimpleName(), cxt);
            }
            return true;
        }

        private <TEntity extends ICoreEntity> boolean fireConstraintRule(EntityConstraintRule<TEntity> instance, TEntity entity, ConstraintValidatorContext cxt) {
            try {
                EntityRuleParameter<TEntity> parameter = getEntityParameter(cxt);
                instance.entityState = parameter.getState();
                instance.previousEntity = parameter.getPreviousEntity();
                if (instance.isEnable() && instance.condition(entity)) {
                    String message = instance.message(entity);
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

        private <TEntity extends ICoreEntity> EntityRuleParameter<TEntity> getEntityParameter(ConstraintValidatorContext cxt) {
            EntityStateKind entityStateKind = EntityStateKind.ALL;
            TEntity previousEntity = null;
            if (cxt instanceof HibernateConstraintValidatorContext) {
                EntityState entityState = cxt.unwrap(HibernateConstraintValidatorContext.class).getConstraintValidatorPayload(EntityState.class);
                entityStateKind = entityState == null || entityState.getKind() == null ? EntityStateKind.ALL : entityState.getKind();
                previousEntity = entityState == null ? null : (TEntity) entityState.getPreviousEntity();
            }
            return new EntityRuleParameter<>(entityStateKind, previousEntity);
        }
    }
}
