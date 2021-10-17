package com.hamrasta.trellis.data.core.model;

import com.hamrasta.trellis.core.payload.IPayload;

import java.util.Date;

public interface ICoreEntity extends IPayload {
    String getId();

    void setId(String id);

    Date getCreated();

    void setCreated(Date created);

    Date getModified();

    void setModified(Date modified);

    Long getVersion();

    void setVersion(Long version);

}
