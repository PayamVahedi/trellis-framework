package com.hamrasta.trellis.data.core.metadata;

import com.hamrasta.trellis.util.generic.IComparable;

public enum EntityStateKind implements IComparable<EntityStateKind> {
    PERSIST,
    UPDATE,
    REMOVE,
    LOAD,
    ALL;
}
