package org.example.eko.model.entities;

import javax.persistence.*;

@Entity
@Table(
        name = "ft_wirkstoff"
)
public class Wirkstoff {
    @GeneratedValue(
            generator = "staging_area_generator"
    )
    @SequenceGenerator(
            name = "staging_area_generator",
            sequenceName = "staging_area_sequence",
            initialValue = 1
    )
    @Id
    private Long id;

    @ManyToOne
    private WirkstoffAtcCode pharWirkstoff;

    @Column(
            name = "laufnummer"
    )
    private Integer laufNummer;


    @ManyToOne
    private WirkstoffAtcCode wirkWirkstoff;

    @Column(
            name = "wirkstoff_stärke",
            length = 10
    )
    private String wirkstoffStärke;

    @Column(
            name = "dimension_wirkstoff_stärke",
            length = 9
    )
    private String wirkstoffStärkenDimension;

    @Column(
            name= "wirkstoff_eigenschaft",
            length = 20
    )
    private String wirkstoffEigenschaft;

    public String getPharmaNummer() {
        return pharmaNummer;
    }

    public void setPharmaNummer(String pharmaNummer) {
        this.pharmaNummer = pharmaNummer;
    }

    @Transient
    private String pharmaNummer;

    public Long getId() {
        return id;
    }



    public WirkstoffAtcCode getPharWirkstoff() {
        return pharWirkstoff;
    }

    public void setPharWirkstoff(WirkstoffAtcCode wirkstoff) {
        this.pharWirkstoff = wirkstoff;
    }

    public Integer getLaufNummer() {
        return laufNummer;
    }

    public void setLaufNummer(Integer laufNummer) {
        this.laufNummer = laufNummer;
    }

    public WirkstoffAtcCode getWirkWirkstoff() {
        return wirkWirkstoff;
    }

    public void setWirkWirkstoff(WirkstoffAtcCode atcCodeErgänzt) {
        this.wirkWirkstoff = atcCodeErgänzt;
    }

    public String getWirkstoffStärke() {
        return wirkstoffStärke;
    }

    public void setWirkstoffStärke(String wirkstoffStärke) {
        this.wirkstoffStärke = wirkstoffStärke;
    }

    public String getWirkstoffStärkenDimension() {
        return wirkstoffStärkenDimension;
    }

    public void setWirkstoffStärkenDimension(String wirkstoffStärkenDimension) {
        this.wirkstoffStärkenDimension = wirkstoffStärkenDimension;
    }

    public String getWirkstoffEigenschaft() {
        return wirkstoffEigenschaft;
    }

    public void setWirkstoffEigenschaft(String wirkstoffEigenschaft) {
        this.wirkstoffEigenschaft = wirkstoffEigenschaft;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
