package com.hamrasta.trellis.util.mapper;

import com.hamrasta.trellis.util.reflection.ReflectionUtil;
import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.dozer.loader.api.TypeMappingBuilder;
import org.dozer.loader.api.TypeMappingOptions;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface ModelMapper {
    DozerBeanMapper modelMapper = new DozerBeanMapper();

    default DozerBeanMapper getModelMapper(BeanMappingBuilder builder) {
        return getModelMapper(builder, false);
    }

    default DozerBeanMapper getModelMapper(BeanMappingBuilder builder, boolean forceInit) {
        DozerBeanMapper model_mapper = forceInit ? new DozerBeanMapper() : modelMapper;
        if (!ModelMapperConfig.initilized || forceInit) {
            if (!forceInit)
                ModelMapperConfig.initilized = true;
            model_mapper.addMapping(builder);
        }
        return model_mapper;
    }

    default <D> D plainToClass(Object source, Class<D> destination) {
        return plainToClass(source, destination, getExcludeField(source.getClass()));
    }

    default <D> D plainToClass(Object source, Class<D> destination, Set<Field> excludeFields) {
        return plainToClass(source, destination, getDefaultMapperBuilder(source.getClass(), destination, excludeFields));
    }

    default <D> D plainToClass(Object source, Class<D> destination, BeanMappingBuilder builder) {
        return getModelMapper(builder).map(source, destination);
    }

    default <S, D> D plainToClass(S source, D destination) {
        return plainToClass(source, destination, getExcludeField(source.getClass()));
    }

    default <S, D> D plainToClass(S source, D destination, Set<Field> excludeFields) {
        return plainToClass(source, destination, getDefaultMapperBuilder(source.getClass(), destination.getClass(), excludeFields));
    }

    default <S, D> D plainToClass(S source, D destination, BeanMappingBuilder builder) {
        getModelMapper(builder).map(source, destination);
        return destination;
    }

    default <S, D> List<D> plainToClass(Iterable<S> source, final Class<D> destination) {
        return plainToClass(source, destination, getExcludeField(source.getClass()));
    }

    default <S, D> List<D> plainToClass(Iterable<S> source, final Class<D> destination, Set<Field> excludeFields) {
        return plainToClass(source, destination, getDefaultMapperBuilder(source.getClass(), destination, excludeFields));
    }

    default <S, D> List<D> plainToClass(Iterable<S> source, final Class<D> destination, BeanMappingBuilder builder) {
        final List<D> result = new ArrayList<>();
        if (source == null)
            return result;
        for (S element : source) {
            result.add(getModelMapper(builder).map(element, destination));
        }
        return result;
    }

    private <S, D> BeanMappingBuilder getDefaultMapperBuilder(Class<S> source, Class<D> destination, Set<Field> excludeFields) {
        return new BeanMappingBuilder() {
            protected void configure() {
                TypeMappingBuilder typeBuilder = mapping(source, destination, TypeMappingOptions.mapNull(false), TypeMappingOptions.mapEmptyString(false));
                if (excludeFields == null || excludeFields.isEmpty())
                    return;
                for (Field field : excludeFields) {
                    typeBuilder.exclude(field.getName());
                }
            }
        };
    }

    private Set<Field> getExcludeField(Class<?> clazz) {
        Set<Field> fields = ReflectionUtil.getFields(clazz);
        Set<Field> readOnlyFields = new HashSet<>();
        if (fields.isEmpty())
            return readOnlyFields;
        for (Field field : fields) {
            if (field.isAnnotationPresent(Exclude.class))
                readOnlyFields.add(field);
        }
        return readOnlyFields;
    }
}
