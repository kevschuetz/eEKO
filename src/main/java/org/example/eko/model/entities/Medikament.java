package org.example.eko.model.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="eko_eintrag", schema = "staging_area")
public class Medikament extends AuditModel {
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


    @Column(name = "wirkstoff_information")
    String wirkstoffInformation;

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

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name="medikament_vergleiche",
            schema = "staging_area",
            joinColumns = @JoinColumn( name="medikament_id"),
            inverseJoinColumns = @JoinColumn( name="vergleich_id")
    )
    private List<MedikamentVergleichsEntity> medikamentVergleichsEntityList;

    @Column(name="indikations_text", length = 2000)
    private String indText;

    @OneToMany(fetch = FetchType.LAZY)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JoinTable(
            name="medikament_wirkstoffe",
            schema = "staging_area",
            joinColumns = @JoinColumn( name="medikament_id"),
            inverseJoinColumns = @JoinColumn( name="wirkstoff_id")
    )
    List<Wirkstoff> wirkstoffe;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBox() {
        return box;
    }

    public void setBox(String box) {
        this.box = box;
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

    public String getIndText() {
        return indText;
    }

    public void setIndText(String indText) {
        this.indText = indText;
    }
}
