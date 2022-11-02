package org.example.eko.model.dtos;

import org.example.eko.model.entities.staging.Medikament;

import java.util.ArrayList;
import java.util.List;

public class MedikamentEkoDTO {
    Long id;
    String pharmaNummer;
    String registerNummer;
    String name;
    String registerNummernPrefix;
    String euRegisterNummerPackungsNummer;
    Double menge;
    String mengenArt;
    String darreichungsForm;
    String teilbarkeit;
    String packungsHinweis;
    List<WirkstoffDTO> wirkstoffe;
    String wirkstoffInformation;
    Double kassenVerkaufspreis;
    Double kvpProEinheit;
    Integer rezeptFlichtId;
    Integer abgabeAnzahl;
    String box;
    String kassenzeichen;
    String preisModell;
    String regeltext;
    String hinweis;
    String langzeitBewilligung;
    String suchtgiftVignette;
    String rezeptpflichtBezeichnung;
    Integer positionPreisvergleich;
    String indikationsText;
    Integer vergleichsKennzeichen;

    public MedikamentEkoDTO(Medikament medikament, Integer vergleichsKennzeichen){
        setPharmaNummer(medikament.getPharmaNummer());
        setRegisterNummer(medikament.getRegisterNummer());
        setName(medikament.getName());
        setRegisterNummernPrefix(medikament.getRegisterNummernPrefix());
        setEuRegisterNummerPackungsNummer(medikament.getEuRegisterNummernPackungsNummer());
        setMenge(medikament.getMenge());
        setMengenArt(medikament.getMengenart());
        setDarreichungsForm(medikament.getDarreichungsForm());
        setTeilbarkeit(medikament.getTeilbarkeit());
        setPackungsHinweis(medikament.getPackungsHinweis());
        setWirkstoffInformation(medikament.getWirkstoffInformation());
        vergleichsKennzeichen = vergleichsKennzeichen;

        List<WirkstoffDTO> wirkstoffDTOS = new ArrayList<>();
        for(var w : medikament.getWirkstoffe()){
            var dto = new WirkstoffDTO();
            dto.pharWirkStoffAtcCode = w.getPharWirkstoff().getAtcCode();
            dto.pharWirkstoffText = w.getPharWirkstoff().getText();
            dto.wirkstoffEigenschaft = w.getWirkstoffEigenschaft();
            dto.wirkstoffStaerke = w.getWirkstoffStärke();
            dto.wirkstoffStaerkenDimension = w.getWirkstoffStärkenDimension();
            dto.wirkWirkstoffAtcCode = w.getWirkWirkstoff().getAtcCode();
            dto.wirkWirkstoffText = w.getWirkWirkstoff().getText();
            wirkstoffDTOS.add(dto);
        }
        setKassenVerkaufspreis(medikament.getKassenverkaufspreis());
        setKvpProEinheit(medikament.getKvpProEinheit());
        setRezeptFlichtId(medikament.getRezeptPflichtId());
        setAbgabeAnzahl(medikament.getAbgabeanzahl());
        setBox(medikament.getBox());
        setKassenzeichen(medikament.getKassenzeichen());
        setPreisModell(medikament.getPreisModell());
        setRegeltext(medikament.getRegelText());
        setHinweis(medikament.getHinweis());
        setLangzeitBewilligung(medikament.getLangzeitBewilligung());
        setSuchtgiftVignette(medikament.getSuchtGiftVignette());
        setRezeptpflichtBezeichnung(medikament.getRezeptpflichtBezeichnung());
        setPositionPreisvergleich(medikament.getPositionPreisvergleich());
        setIndikationsText(medikament.getIndText());
        setId(medikament.getId());

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIndikationsText() {
        return indikationsText;
    }

    public void setIndikationsText(String indikationsText) {
        this.indikationsText = indikationsText;
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

    public String getEuRegisterNummerPackungsNummer() {
        return euRegisterNummerPackungsNummer;
    }

    public void setEuRegisterNummerPackungsNummer(String euRegisterNummerPackungsNummer) {
        this.euRegisterNummerPackungsNummer = euRegisterNummerPackungsNummer;
    }

    public Double getMenge() {
        return menge;
    }

    public void setMenge(Double menge) {
        this.menge = menge;
    }

    public String getMengenArt() {
        return mengenArt;
    }

    public void setMengenArt(String mengenArt) {
        this.mengenArt = mengenArt;
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

    public List<WirkstoffDTO> getWirkstoffe() {
        return wirkstoffe;
    }

    public void setWirkstoffe(List<WirkstoffDTO> wirkstoffe) {
        this.wirkstoffe = wirkstoffe;
    }

    public String getWirkstoffInformation() {
        return wirkstoffInformation;
    }

    public void setWirkstoffInformation(String wirkstoffInformation) {
        this.wirkstoffInformation = wirkstoffInformation;
    }

    public Double getKassenVerkaufspreis() {
        return kassenVerkaufspreis;
    }

    public void setKassenVerkaufspreis(Double kassenVerkaufspreis) {
        this.kassenVerkaufspreis = kassenVerkaufspreis;
    }

    public Double getKvpProEinheit() {
        return kvpProEinheit;
    }

    public void setKvpProEinheit(Double kvpProEinheit) {
        this.kvpProEinheit = kvpProEinheit;
    }

    public Integer getRezeptFlichtId() {
        return rezeptFlichtId;
    }

    public void setRezeptFlichtId(Integer rezeptFlichtId) {
        this.rezeptFlichtId = rezeptFlichtId;
    }

    public Integer getAbgabeAnzahl() {
        return abgabeAnzahl;
    }

    public void setAbgabeAnzahl(Integer abgabeAnzahl) {
        this.abgabeAnzahl = abgabeAnzahl;
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

    public String getRegeltext() {
        return regeltext;
    }

    public void setRegeltext(String regeltext) {
        this.regeltext = regeltext;
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

    public String getSuchtgiftVignette() {
        return suchtgiftVignette;
    }

    public void setSuchtgiftVignette(String suchtgiftVignette) {
        this.suchtgiftVignette = suchtgiftVignette;
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

    private static class WirkstoffDTO{
        String pharWirkStoffAtcCode;
        String pharWirkstoffText;
        String wirkWirkstoffAtcCode;
        String wirkWirkstoffText;
        String wirkstoffEigenschaft;
        String wirkstoffStaerke;
        String wirkstoffStaerkenDimension;
    }
}
