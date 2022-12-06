package org.example.eko.model.entities.datamart;

import javax.persistence.*;

@Table(name = "dt_vergleichskennzeichen", schema = "data_mart")
@Entity
public class VergleichsKennzeichenDimension {
    @GeneratedValue(
            generator = "data_mart_generator"
    )
    @Id
    private Long id;

    private Integer kennzeichen;
    private String bezeichnung;

    public VergleichsKennzeichenDimension(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getKennzeichen() {
        return kennzeichen;
    }

    public void setKennzeichen(Integer kennzeichen) {
        this.kennzeichen = kennzeichen;
    }

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }
}
