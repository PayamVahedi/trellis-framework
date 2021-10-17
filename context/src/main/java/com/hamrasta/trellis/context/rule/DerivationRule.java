package com.hamrasta.trellis.context.rule;

import com.hamrasta.trellis.util.reflection.ReflectionUtil;
import com.hamrasta.trellis.context.provider.ActionContextProvider;
import com.hamrasta.trellis.core.application.ApplicationContextProvider;
import com.hamrasta.trellis.http.exception.HttpException;
import com.hamrasta.trellis.core.payload.IPayload;
import com.hamrasta.trellis.context.action.*;
import de.cronn.reflection.util.PropertyGetter;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

public abstract class DerivationRule<T> extends Rule<T> implements ActionContextProvider {
    private Set<String> fields;

    public DerivationRule(PropertyGetter<T> field) {
        this(field == null ? new HashSet<>() : Set.of(field));
    }

    public DerivationRule(Set<PropertyGetter<T>> fields) {
        this.fields = fields == null || fields.size() <= 0 ? new HashSet<>() : new HashSet<>(ReflectionUtil.getPropertiesName(this.getClazz(), fields));
    }
        public abstract Object getDerivedValue(T t) throws Throwable;

    public Set<String> getFields() {
        return fields;
    }

    public boolean isEnable() {
        return true;
    }

    private Class<T> getClazz() {
        return (Class<T>) ((ParameterizedType) (getClass().getGenericSuperclass())).getActualTypeArguments()[0];
    }
}
