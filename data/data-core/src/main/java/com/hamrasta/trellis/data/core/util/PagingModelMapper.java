package com.hamrasta.trellis.data.core.util;

import com.hamrasta.trellis.util.mapper.ModelMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public interface PagingModelMapper extends ModelMapper {
    default <S, D> Page<D> plainToClass(Page<S> source, final Class<D> destination) {
        List<D> contents = source.getContent().stream().map(x -> plainToClass(x, destination)).collect(Collectors.toList());
        return new PageImpl<>(contents, source.getPageable(), source.getTotalElements());
    }

    default <S, D> Page<D> plainToClass(Page<S> source, final Class<D> destination, Set<Field> excludeFields) {
        List<D> contents = source.getContent().stream().map(x -> plainToClass(x, destination, excludeFields)).collect(Collectors.toList());
        return new PageImpl<>(contents, source.getPageable(), source.getTotalElements());
    }

    default <S, D> Page<D> plainToClass(Page<S> source, final Class<D> destination, BeanMappingBuilder builder) {
        List<D> contents = source.getContent().stream().map(x -> plainToClass(x, destination, builder)).collect(Collectors.toList());
        return new PageImpl<>(contents, source.getPageable(), source.getTotalElements());
    }
}
