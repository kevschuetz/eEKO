package org.example.eko.model.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="ft_medikament")
public class Medikament extends AuditModel {
    @Id
    @Column(
            name = "pharma_nummer",
            nullable = false,
            length = 7
    )
    private String pharmaNummer;

    @Column(
            name = "register_nummer",
            nullable = false,
            length = 6
    )
    private String registerNummer;

    @Column(
            name = "name",
            length = 100
    )
    private String name;

    @Column(
            name = "register_nummer_prefix",
            length = 2
    )
    private String registerNummernPrefix;

    @Column(
            name = "eu_register_nummer_packungs_nummer",
            length = 3
    )
    private String euRegisterNummernPackungsNummer;


    @Column(
            name = "menge",
            length = 5,
            nullable = false

    )
    private Double menge;


    @Column(
            name = "mengenart",
            length = 2
    )
    private String mengenart;

    @Column(
            name = "darreichungsform",
            length = 9
    )
    private String darreichungsForm;


    @Column(
            name = "teilbarkeit",
            length = 3
    )
    private String teilbarkeit;


    @Column(
            name = "packungshinweis",
            length = 28
    )
    private String packungsHinweis;

    @Column(name = "wirkstoff")
    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<Wirkstoff> wirkstoffe;

    @Column(name = "wirkstoff_information")
    String wirkstoffInformation;

    @Column(name = "eko_eintrag")
    @OneToMany(cascade = CascadeType.ALL)
    List<EkoEintrag> ekoEintraege;

    public Medikament(){

    }

    public String getPharmaNummer() {
        return pharmaNummer;
    }

    public void setPharmaNummer(String pharmaNummer) {
        this.pharmaNummer = pharmaNummer;
    }

    public String getRegisterNummer() {
        return registerNummer;
    }

    public void setRegisterNummer(String registerNummer) {
        this.registerNummer = registerNummer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegisterNummernPrefix() {
        return registerNummernPrefix;
    }

    public void setRegisterNummernPrefix(String registerNummernPrefix) {
        this.registerNummernPrefix = registerNummernPrefix;
    }

    public String getEuRegisterNummernPackungsNummer() {
        return euRegisterNummernPackungsNummer;
    }

    public void setEuRegisterNummernPackungsNummer(String euRegisterNummernPackungsNummer) {
        this.euRegisterNummernPackungsNummer = euRegisterNummernPackungsNummer;
    }


    public Double getMenge() {
        return menge;
    }

    public void setMenge(Double menge) {
        this.menge = menge;
    }

    public String getMengenart() {
        return mengenart;
    }

    public void setMengenart(String mengenart) {
        this.mengenart = mengenart;
    }

    public String getDarreichungsForm() {
        return darreichungsForm;
    }

    public void setDarreichungsForm(String darreichungsForm) {
        this.darreichungsForm = darreichungsForm;
    }

    public String getTeilbarkeit() {
        return teilbarkeit;
    }

    public void setTeilbarkeit(String teilbarkeit) {
        this.teilbarkeit = teilbarkeit;
    }

    public String getPackungsHinweis() {
        return packungsHinweis;
    }

    public void setPackungsHinweis(String packungsHinweis) {
        this.packungsHinweis = packungsHinweis;
    }

    public List<Wirkstoff> getWirkstoffe() {
        return wirkstoffe;
    }

    public void setWirkstoffe(List<Wirkstoff> wirkstoffInformationen) {
        this.wirkstoffe = wirkstoffInformationen;
    }

    public String getWirkstoffInformation() {
        return wirkstoffInformation;
    }

    public void setWirkstoffInformation(String wirkstoffInformation) {
        this.wirkstoffInformation = wirkstoffInformation;
    }

    public List<EkoEintrag> getEkoEintraege() {
        return ekoEintraege;
    }

    public void setEkoEintraege(List<EkoEintrag> ekoEintraege) {
        this.ekoEintraege = ekoEintraege;
    }
}
