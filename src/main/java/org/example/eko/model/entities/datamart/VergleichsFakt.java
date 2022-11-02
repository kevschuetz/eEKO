package org.example.eko.model.entities.datamart;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name = "ft_vergleich", schema = "data_mart")
public class VergleichsFakt {
    @GeneratedValue(
            generator = "data_mart_generator"
    )
    @Id
    private Long id;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private MedikamentDimension medikament;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private MedikamentDimension vergleichsMedikament;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private VergleichsKennzeichen vergleichsKennzeichen;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private DateDimension date;

    private Double kvpEinheit;
    private Double kvp;
    private Double einsparungsPotenzial;

    public VergleichsFakt(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MedikamentDimension getMedikament() {
        return medikament;
    }

    public void setMedikament(MedikamentDimension medikament) {
        this.medikament = medikament;
    }

    public MedikamentDimension getVergleichsMedikament() {
        return vergleichsMedikament;
    }

    public void setVergleichsMedikament(MedikamentDimension vergleichsMedikament) {
        this.vergleichsMedikament = vergleichsMedikament;
    }

    public VergleichsKennzeichen getVergleichsKennzeichen() {
        return vergleichsKennzeichen;
    }

    public void setVergleichsKennzeichen(VergleichsKennzeichen vergleichsKennzeichen) {
        this.vergleichsKennzeichen = vergleichsKennzeichen;
    }

    public DateDimension getDate() {
        return date;
    }

    public void setDate(DateDimension date) {
        this.date = date;
    }

    public Double getKvpEinheit() {
        return kvpEinheit;
    }

    public void setKvpEinheit(Double kvpEinheit) {
        this.kvpEinheit = kvpEinheit;
    }

    public Double getKvp() {
        return kvp;
    }

    public void setKvp(Double kvp) {
        this.kvp = kvp;
    }

    public Double getEinsparungsPotenzial() {
        return einsparungsPotenzial;
    }

    public void setEinsparungsPotenzial(Double einsparungsPotenzial) {
        this.einsparungsPotenzial = einsparungsPotenzial;
    }
}
