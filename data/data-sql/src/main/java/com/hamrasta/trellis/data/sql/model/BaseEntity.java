package com.hamrasta.trellis.data.sql.model;

import com.hamrasta.trellis.data.sql.event.EntityListener;
import com.hamrasta.trellis.validator.ReadOnly;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
@EntityListeners(value = EntityListener.class)
public class BaseEntity implements IBaseEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @ReadOnly
    protected String id;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @ReadOnly
    protected Date modified = new Date();

    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @ReadOnly
    protected Date created = new Date();

    @ColumnDefault("0")
    @Version
    @Column(name = "version", nullable = false)
    @ReadOnly
    protected Long version;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : super.hashCode();
    }
}
