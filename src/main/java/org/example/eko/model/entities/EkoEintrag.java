package org.example.eko.model.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "ft_eko_eintrag")
public class EkoEintrag extends AuditModel {
    @GeneratedValue(
            generator = "eko_eintrag_generator"
    )
    @SequenceGenerator(
            name = "eko_eintrag_generator",
            sequenceName = "eko_eintrag_sequence",
            initialValue = 1
    )
    @Id
    private Long id;

    @Column(
            name = "kassenverkaufspreis",
            nullable = false)
    private Double kassenverkaufspreis;

    @Column(
            name = "kvp_pro_einheit",
            nullable = false)
    private Double kvpProEinheit;

    @Column(
            name = "rezeptpflicht",
            nullable = false)
    private Integer rezeptPflichtId;

    @Column(
            name = "abgabeanzahl")
    private Integer abgabeanzahl;

    @ManyToOne
    private DateEntity validFrom;

    @Column(
            name = "box",
            nullable = false, length = 1)
    private String  box;

    @Column(
            name = "kassenzeichen",
            length = 3)
    private String kassenzeichen;

    @Column(
            name = "preisModell",
            length = 3)
    private String preisModell;

    @Column(
            name = "regeltext",
            length = 8192)
    private String regelText;

    @Column(
            name = "hinweis",
            length = 2048)
    private String hinweis;

    @Column(
            name = "langzeit_bewilligung",
            length = 3)
    private String langzeitBewilligung;

    @Column(name = "suchtgift_vignette", length = 3)
    private String suchtGiftVignette;

    @Column(name = "rezeptpflicht_bezeichnung", length = 60)
    private String rezeptpflichtBezeichnung;

    @Column(name = "position_preisvergleich")
    private Integer positionPreisvergleich;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MedikamentVergleichsEntity> medikamentVergleichsEntityList;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Double getKassenverkaufspreis() {
        return kassenverkaufspreis;
    }

    public void setKassenverkaufspreis(Double kassenverkaufspreis) {
        this.kassenverkaufspreis = kassenverkaufspreis;
    }

    public Double getKvpProEinheit() {
        return kvpProEinheit;
    }

    public void setKvpProEinheit(Double kvpProEinheit) {
        this.kvpProEinheit = kvpProEinheit;
    }

    public Integer getRezeptPflichtId() {
        return rezeptPflichtId;
    }

    public void setRezeptPflichtId(Integer rezeptPflichtId) {
        this.rezeptPflichtId = rezeptPflichtId;
    }

    public Integer getAbgabeanzahl() {
        return abgabeanzahl;
    }

    public void setAbgabeanzahl(Integer abgabeanzahl) {
        this.abgabeanzahl = abgabeanzahl;
    }

    public DateEntity getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(DateEntity validFrom) {
        this.validFrom = validFrom;
    }

    public String getKassenzeichen() {
        return kassenzeichen;
    }

    public void setKassenzeichen(String kassenzeichen) {
        this.kassenzeichen = kassenzeichen;
    }

    public String getPreisModell() {
        return preisModell;
    }

    public void setPreisModell(String preisModell) {
        this.preisModell = preisModell;
    }

    public String getRegelText() {
        return regelText;
    }

    public void setRegelText(String regelText) {
        this.regelText = regelText;
    }

    public String getHinweis() {
        return hinweis;
    }

    public void setHinweis(String hinweis) {
        this.hinweis = hinweis;
    }

    public String getLangzeitBewilligung() {
        return langzeitBewilligung;
    }

    public void setLangzeitBewilligung(String langzeitBewilligung) {
        this.langzeitBewilligung = langzeitBewilligung;
    }

    public String getSuchtGiftVignette() {
        return suchtGiftVignette;
    }

    public void setSuchtGiftVignette(String suchtGiftVignette) {
        this.suchtGiftVignette = suchtGiftVignette;
    }

    public String getRezeptpflichtBezeichnung() {
        return rezeptpflichtBezeichnung;
    }

    public void setRezeptpflichtBezeichnung(String rezeptpflichtBezeichnung) {
        this.rezeptpflichtBezeichnung = rezeptpflichtBezeichnung;
    }

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
    }

    public Integer getPositionPreisvergleich() {
        return positionPreisvergleich;
    }

    public void setPositionPreisvergleich(Integer positionPreisvergleich) {
        this.positionPreisvergleich = positionPreisvergleich;
    }

    public List<MedikamentVergleichsEntity> getMedikamentVergleichsEntityList() {
        return medikamentVergleichsEntityList;
    }

    public void setMedikamentVergleichsEntityList(List<MedikamentVergleichsEntity> medikamentVergleichsEntityList) {
        this.medikamentVergleichsEntityList = medikamentVergleichsEntityList;
    }
}
