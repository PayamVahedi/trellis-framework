package com.hamrasta.trellis.data.mongo.data.repository;

import com.hamrasta.trellis.data.core.data.repository.ICoreRepository;
import com.hamrasta.trellis.data.mongo.model.IBaseDocument;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface IMongoRepository<TDocument extends IBaseDocument> extends ICoreRepository<TDocument> {

}
