package org.example.eko.model.entities;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="ft_medikament")
public class Medikament extends AuditModel {
    @Id
    @GeneratedValue(
            generator = "medikament_generator"
    )
    @SequenceGenerator(
            name = "medikament_generator",
            sequenceName = "medikament_sequence",
            initialValue = 1
    )
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
            name = "box",
            length = 3
    )
    private String box;


    @Column(
            name = "kassenzeichen",
            length = 3
    )
    private String kassenzeichen;


    @Column(
            name = "menge",
            length = 5

    )
    private Double menge;


    @Column(
            name = "mengenart",
            length = 2
    )
    private String mengenart;

    @Column(
            name = "kassenverkaufspreis",
            length = 9
    )
    private Double kassenVerkaufsPreis;


    @Column(
            name = "kvp_einheit",
            length = 9
    )
    private String kvpEinheit;


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
            name = "preismodell",
            length = 3
    )
    private String preisModell;


    @Column(
            name = "abgabeanzahl",
            length = 3
    )
    private Integer abgabeAnzahl;


    @Column(
            name = "packungshinweis",
            length = 28
    )
    private String packungsHinweis;

    @Column(
            name = "hinweis"
    )
    private String hinweis;

    @Column(
            name = "regeltext",
            length = 8160
    )
    private String regeltext;


    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "valid_date_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private DateEntity dateTime;


    @OneToMany(fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    List<WirkstoffInformation> wirkstoffInformationen;

    public Medikament(){

    }

    public Medikament(DateEntity dateTime){
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
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

    public Double getKassenVerkaufsPreis() {
        return kassenVerkaufsPreis;
    }

    public void setKassenVerkaufsPreis(Double kassenVerkaufsPreis) {
        this.kassenVerkaufsPreis = kassenVerkaufsPreis;
    }

    public String getKvpEinheit() {
        return kvpEinheit;
    }

    public void setKvpEinheit(String kvpEinheit) {
        this.kvpEinheit = kvpEinheit;
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

    public String getPreisModell() {
        return preisModell;
    }

    public void setPreisModell(String preisModell) {
        this.preisModell = preisModell;
    }

    public Integer getAbgabeAnzahl() {
        return abgabeAnzahl;
    }

    public void setAbgabeAnzahl(Integer abgabeAnzahl) {
        this.abgabeAnzahl = abgabeAnzahl;
    }

    public String getPackungsHinweis() {
        return packungsHinweis;
    }

    public void setPackungsHinweis(String packungsHinweis) {
        this.packungsHinweis = packungsHinweis;
    }

    public DateEntity getDateTime() {
        return dateTime;
    }

    public void setDateTime(DateEntity dateTime) {
        this.dateTime = dateTime;
    }

    public String getHinweis() {
        return hinweis;
    }

    public void setHinweis(String hinweis) {
        this.hinweis = hinweis;
    }

    public String getRegeltext() {
        return regeltext;
    }

    public void setRegeltext(String regeltext) {
        this.regeltext = regeltext;
    }

    public void addWirkstoffInformationen(List<WirkstoffInformation> v) {
        if(this.getWirkstoffInformationen() == null) wirkstoffInformationen = new ArrayList<>();
        wirkstoffInformationen.addAll(v);
    }

    public List<WirkstoffInformation> getWirkstoffInformationen() {
        return wirkstoffInformationen;
    }

    public void setWirkstoffInformationen(List<WirkstoffInformation> wirkstoffInformationen) {
        this.wirkstoffInformationen = wirkstoffInformationen;
    }
}
