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
    private HeilmittelDimension D_heilmittel;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private HeilmittelDimension D_vergleichsHeilmittel;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private VergleichsKennzeichenDimension D_vergleichsKennzeichen;

    @ManyToOne
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private DatumDimension D_datum;

    private Double F_kvp;
    private Double F_einsparungsPotenzial;

    public VergleichsFakt() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public HeilmittelDimension getD_heilmittel() {
        return D_heilmittel;
    }

    public void setD_heilmittel(HeilmittelDimension medikament) {
        this.D_heilmittel = medikament;
    }

    public HeilmittelDimension getD_vergleichsHeilmittel() {
        return D_vergleichsHeilmittel;
    }

    public void setD_vergleichsHeilmittel(HeilmittelDimension vergleichsMedikament) {
        this.D_vergleichsHeilmittel = vergleichsMedikament;
    }

    public VergleichsKennzeichenDimension getVergleichsKennzeichen() {
        return D_vergleichsKennzeichen;
    }

    public void setVergleichsKennzeichen(VergleichsKennzeichenDimension vergleichsKennzeichenDimension) {
        this.D_vergleichsKennzeichen = vergleichsKennzeichenDimension;
    }

    public DatumDimension getD_datum() {
        return D_datum;
    }

    public void setD_date(DatumDimension datum) {
        this.D_datum = datum;
    }

    public Double getF_kvp() {
        return F_kvp;
    }

    public void setF_kvp(Double kvp) {
        this.F_kvp = kvp;
    }

    public Double getF_einsparungsPotenzial() {
        return F_einsparungsPotenzial;
    }

    public void setF_einsparungsPotenzial(Double einsparungsPotenzial) {
        this.F_einsparungsPotenzial = einsparungsPotenzial;
    }
}