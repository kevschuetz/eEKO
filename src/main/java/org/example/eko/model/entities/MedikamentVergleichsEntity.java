package org.example.eko.model.entities;


import javax.persistence.*;

@Entity
@Table(name="vergleich", schema = "staging_area")
public class MedikamentVergleichsEntity {
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

    @Column(name = "position_vergleichs_medikament")
    private Integer positionVergleichsMedikament;

    @ManyToOne
    private Medikament vergleichsMedikament;

    @Column(name = "vergleichs_kennzeichen")
    private Integer vergleichsKennzeichen;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPositionVergleichsMedikament() {
        return positionVergleichsMedikament;
    }

    public void setPositionVergleichsMedikament(Integer positionVergleichsMedikament) {
        this.positionVergleichsMedikament = positionVergleichsMedikament;
    }

    public Medikament getVergleichsMedikament() {
        return vergleichsMedikament;
    }

    public void setVergleichsMedikament(Medikament vergleichsMedikament) {
        this.vergleichsMedikament = vergleichsMedikament;
    }

    public Integer getVergleichsKennzeichen() {
        return vergleichsKennzeichen;
    }

    public void setVergleichsKennzeichen(Integer vergleichsKennzeichen) {
        this.vergleichsKennzeichen = vergleichsKennzeichen;
    }
}
