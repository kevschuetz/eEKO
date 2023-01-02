package org.example.eko.model.entities.datamart;

import javax.persistence.*;

@Entity
@Table(name = "dt_heilmittel", schema = "data_mart")
public class HeilmittelDimension {
    @GeneratedValue(
            generator = "data_mart_generator"
    )
    @Id
    private Long id;

    private String pharmaNummer;
    private String medikamentName;
    private String chemischeUntergruppe;
    private String chemischeUntergruppeBezeichnung;
    private String chemischeSubstanz; //possibly not available
    private String chemischeSubstanzBezeichnung;
    private String pharmakologischeUntergruppe;
    private String pharmakolgischeUntergruppeBezeichnung;
    private String therapeutischeUntergruppe;
    private String therapeutischeUntergruppeBezeichnung;
    private String anatomischeHauptgruppe;
    private String anatomischeHauptgruppeBezeichnung;

    private char box;
    private Integer rezeptPflichtId;
    private String rezeptPflicht;
    private String kassenZeichen;
    private String preisModell;
    private String langzeitBewilligung;
    private String suchtgiftVignette;
    private String darreichungsForm;
    private Integer positionPreisvergleich;
    private Double kvpEinheit;

    public HeilmittelDimension(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPharmaNummer() {
        return pharmaNummer;
    }

    public void setPharmaNummer(String pharmaNummer) {
        this.pharmaNummer = pharmaNummer;
    }

    public String getMedikamentName() {
        return medikamentName;
    }

    public void setMedikamentName(String medikamentName) {
        this.medikamentName = medikamentName;
    }

    public String getChemischeUntergruppe() {
        return chemischeUntergruppe;
    }

    public void setChemischeUntergruppe(String chemischeUntergruppe) {
        this.chemischeUntergruppe = chemischeUntergruppe;
    }

    public String getChemischeUntergruppeBezeichnung() {
        return chemischeUntergruppeBezeichnung;
    }

    public void setChemischeUntergruppeBezeichnung(String chemischeUntergruppeBezeichnung) {
        this.chemischeUntergruppeBezeichnung = chemischeUntergruppeBezeichnung;
    }

    public String getChemischeSubstanz() {
        return chemischeSubstanz;
    }

    public void setChemischeSubstanz(String chemischeSubstanz) {
        this.chemischeSubstanz = chemischeSubstanz;
    }

    public String getPharmakologischeUntergruppe() {
        return pharmakologischeUntergruppe;
    }

    public void setPharmakologischeUntergruppe(String pharmakologischeUntergruppe) {
        this.pharmakologischeUntergruppe = pharmakologischeUntergruppe;
    }

    public String getPharmakolgischeUntergruppeBezeichnung() {
        return pharmakolgischeUntergruppeBezeichnung;
    }

    public void setPharmakolgischeUntergruppeBezeichnung(String pharmakolgischeUntergruppeBezeichnung) {
        this.pharmakolgischeUntergruppeBezeichnung = pharmakolgischeUntergruppeBezeichnung;
    }

    public String getTherapeutischeUntergruppe() {
        return therapeutischeUntergruppe;
    }

    public void setTherapeutischeUntergruppe(String therapeutischeUntergruppe) {
        this.therapeutischeUntergruppe = therapeutischeUntergruppe;
    }

    public String getTherapeutischeUntergruppeBezeichnung() {
        return therapeutischeUntergruppeBezeichnung;
    }

    public void setTherapeutischeUntergruppeBezeichnung(String therapeutischeUntergruppeBezeichnung) {
        this.therapeutischeUntergruppeBezeichnung = therapeutischeUntergruppeBezeichnung;
    }

    public String getAnatomischeHauptgruppe() {
        return anatomischeHauptgruppe;
    }

    public void setAnatomischeHauptgruppe(String anatomischeHauptgruppe) {
        this.anatomischeHauptgruppe = anatomischeHauptgruppe;
    }

    public String getAnatomischeHauptgruppeBezeichnung() {
        return anatomischeHauptgruppeBezeichnung;
    }

    public void setAnatomischeHauptgruppeBezeichnung(String anatomischeHauptgruppeBezeichnung) {
        this.anatomischeHauptgruppeBezeichnung = anatomischeHauptgruppeBezeichnung;
    }

    public char getBox() {
        return box;
    }

    public void setBox(char box) {
        this.box = box;
    }

    public Integer getRezeptPflichtId() {
        return rezeptPflichtId;
    }

    public void setRezeptPflichtId(Integer rezeptPflichtId) {
        this.rezeptPflichtId = rezeptPflichtId;
    }

    public String getRezeptPflicht() {
        return rezeptPflicht;
    }

    public void setRezeptPflicht(String rezeptPflicht) {
        this.rezeptPflicht = rezeptPflicht;
    }

    public String getKassenZeichen() {
        return kassenZeichen;
    }

    public void setKassenZeichen(String kassenZeichen) {
        this.kassenZeichen = kassenZeichen;
    }

    public String getPreisModell() {
        return preisModell;
    }

    public void setPreisModell(String preisModell) {
        this.preisModell = preisModell;
    }

    public String getLangzeitBewilligung() {
        return langzeitBewilligung;
    }

    public void setLangzeitBewilligung(String langzeitBewilligung) {
        this.langzeitBewilligung = langzeitBewilligung;
    }

    public String getSuchtgiftVignette() {
        return suchtgiftVignette;
    }

    public void setSuchtgiftVignette(String suchtgiftVignette) {
        this.suchtgiftVignette = suchtgiftVignette;
    }

    public String getDarreichungsForm() {
        return darreichungsForm;
    }

    public void setDarreichungsForm(String darreichungsForm) {
        this.darreichungsForm = darreichungsForm;
    }

    public Integer getPositionPreisvergleich() {
        return positionPreisvergleich;
    }

    public void setPositionPreisvergleich(Integer positionPreisvergleich) {
        this.positionPreisvergleich = positionPreisvergleich;
    }

    public String getChemischeSubstanzBezeichnung() {
        return chemischeSubstanzBezeichnung;
    }

    public void setChemischeSubstanzBezeichnung(String chemischeSubstanzBezeichnung) {
        this.chemischeSubstanzBezeichnung = chemischeSubstanzBezeichnung;
    }
    public Double getKvpEinheit() {
        return kvpEinheit;
    }

    public void setKvpEinheit(Double kvpEinheit) {
        this.kvpEinheit = kvpEinheit;
    }
}
