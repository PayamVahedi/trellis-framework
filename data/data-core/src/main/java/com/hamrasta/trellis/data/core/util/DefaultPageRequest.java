package com.hamrasta.trellis.data.core.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class DefaultPageRequest extends PageRequest  {

    protected DefaultPageRequest(int page, int size, Sort sort) {
        super(page, size, sort);
    }

    public static PageRequest of(int page, int size) {
        return of(page, size, Sort.by(Sort.Direction.DESC, "created"));
    }
}
