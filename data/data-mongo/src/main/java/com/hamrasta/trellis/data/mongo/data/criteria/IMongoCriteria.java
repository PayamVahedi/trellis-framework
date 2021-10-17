package com.hamrasta.trellis.data.mongo.data.criteria;

import com.hamrasta.trellis.data.core.data.criteria.ICoreCriteria;
import com.hamrasta.trellis.data.mongo.model.IBaseDocument;
import org.springframework.stereotype.Repository;

@Repository
public interface IMongoCriteria<TDocument extends IBaseDocument> extends ICoreCriteria<TDocument> {

}
