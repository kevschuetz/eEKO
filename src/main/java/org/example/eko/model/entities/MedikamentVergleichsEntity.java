package org.example.eko.model.entities;


import javax.persistence.*;

@Entity
public class MedikamentVergleichsEntity {
    @GeneratedValue(
            generator = "medikament_vergleich_generator"
    )
    @SequenceGenerator(
            name = "medikament_vergleich_generator",
            sequenceName = "medikament_vergleich_sequence",
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
