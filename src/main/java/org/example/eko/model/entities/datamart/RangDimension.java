package org.example.eko.model.entities.datamart;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "dt_rang", schema = "data_mart")
public class RangDimension {
    @GeneratedValue(
            generator = "data_mart_generator"
    )
    @Id
    private Long id;

    private Integer rang;
    public RangDimension(){
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRang() {
        return rang;
    }

    public void setRang(Integer rang) {
        this.rang = rang;
    }
}
