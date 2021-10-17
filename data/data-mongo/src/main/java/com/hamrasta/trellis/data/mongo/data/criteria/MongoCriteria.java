package com.hamrasta.trellis.data.mongo.data.criteria;

import com.hamrasta.trellis.data.mongo.model.IBaseDocument;
import org.springframework.data.mongodb.core.MongoOperations;

public abstract class MongoCriteria<TDocument extends IBaseDocument> implements IMongoCriteria<TDocument> {
    private final MongoOperations entityManager;

    public MongoCriteria(MongoOperations entityManager) {
        this.entityManager = entityManager;
    }

    protected MongoOperations getEntityManager() {
        return entityManager;
    }
}
