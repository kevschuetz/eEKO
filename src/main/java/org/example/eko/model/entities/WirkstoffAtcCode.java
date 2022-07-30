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
}
