package org.example.eko.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@JsonIgnoreProperties(
        value = {"timestamp"},
        allowGetters = true
)
public abstract class AuditModel implements Serializable {
    @Temporal(TemporalType.TIMESTAMP)
    @Column(
            name = "timestamp",
            nullable = false,
            updatable = false
    )
    @CreatedDate
    private Date timestamp;

    public AuditModel() {
    }

    public Date getTimestamp() {
        return this.timestamp;
    }
}


