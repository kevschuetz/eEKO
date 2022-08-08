package org.example.eko.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "dt_wirkstoff_atc_codes")
public class WirkstoffAtcCode {

    @Id
    @Column(
            name="atc_code",
            length = 7
    )
    private String atcCode;

    @Column(
            name = "text",
            length = 200
    )
    private String text;

    @ManyToOne
    private WirkstoffAtcCode anatomischeHauptgruppe;

    @ManyToOne
    private WirkstoffAtcCode therapeutischeUntergruppe;

    @ManyToOne
    WirkstoffAtcCode pharmakologischeUntegruppe;

    @ManyToOne
    WirkstoffAtcCode chemischeUntergruppe;

    public String getAtcCode() {
        return atcCode;
    }

    public void setAtcCode(String atcCode) {
        this.atcCode = atcCode;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public WirkstoffAtcCode getAnatomischeHauptgruppe() {
        return anatomischeHauptgruppe;
    }

    public void setAnatomischeHauptgruppe(WirkstoffAtcCode anatomischeHauptgruppe) {
        this.anatomischeHauptgruppe = anatomischeHauptgruppe;
    }

    public WirkstoffAtcCode getTherapeutischeUntergruppe() {
        return therapeutischeUntergruppe;
    }

    public void setTherapeutischeUntergruppe(WirkstoffAtcCode therapeutischeUntergruppe) {
        this.therapeutischeUntergruppe = therapeutischeUntergruppe;
    }

    public WirkstoffAtcCode getPharmakologischeUntegruppe() {
        return pharmakologischeUntegruppe;
    }

    public void setPharmakologischeUntegruppe(WirkstoffAtcCode pharmakologischeUntegruppe) {
        this.pharmakologischeUntegruppe = pharmakologischeUntegruppe;
    }

    public WirkstoffAtcCode getChemischeUntergruppe() {
        return chemischeUntergruppe;
    }

    public void setChemischeUntergruppe(WirkstoffAtcCode chemischeUntergruppe) {
        this.chemischeUntergruppe = chemischeUntergruppe;
    }
}
