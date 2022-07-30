package org.example.eko.service.scanning.medikament;

import org.example.eko.model.entities.DateEntity;
import org.example.eko.model.entities.Medikament;
import org.example.eko.model.repositories.DateTimeRepository;
import org.example.eko.service.scanning.Scanner;
import org.example.eko.service.scanning.ScanningRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class MedikamentScanner extends Scanner {
    private final Logger logger = LoggerFactory.getLogger(MedikamentScanner.class);

    private final static ScanningRule pharmaNummer = new ScanningRule(1, 7);
    private final static ScanningRule getRegisterNummerPrefix = new ScanningRule(8, 2);
    private final static ScanningRule registerNummer = new ScanningRule(10, 6);
    private final static ScanningRule euRegisterNummerPackungsNummer = new ScanningRule(16,3);
    private final static ScanningRule name = new ScanningRule(19, 100);
    private final static ScanningRule box = new ScanningRule(119, 3);
    private final static ScanningRule kassenZeichen = new ScanningRule(122,3);
    private final static ScanningRule menge = new ScanningRule(125,5);
    private final static ScanningRule mengenArt = new ScanningRule(130, 2);
    private final static ScanningRule kvp = new ScanningRule(132, 9);
    private final static ScanningRule kvpEinheit = new ScanningRule(141, 9);
    private final static ScanningRule darreichungsform = new ScanningRule(150,9);
    private final static ScanningRule teilbarkeit = new ScanningRule(159, 3);
    private final static ScanningRule preisModell = new ScanningRule(162, 3);
    private final static ScanningRule abgabeAnzahl = new ScanningRule(165, 3);
    private final static ScanningRule packungsHinweis = new ScanningRule(168, 28);


    private final DateTimeRepository dateTimeRepository;

    public MedikamentScanner(DateTimeRepository dateTimeRepository){
        this.dateTimeRepository = dateTimeRepository;
    }

    @Override
    public List<Medikament> scan(String data){
        List<Medikament> medikamente = new ArrayList<>();
        LocalDate date = LocalDate.of(2022, 7, 29);
        DateEntity dateTime = dateTimeRepository.save(new DateEntity(date));
        var entries = data.split("\n");
        for(String entry : entries){
            medikamente.add(scanMedikament(entry, dateTime));
        }

        return  medikamente;
    }

    private Medikament scanMedikament(String entry, DateEntity dateTime) {
        Medikament medikament = new Medikament(dateTime);

        String pharmaNummer = getString(entry, this.pharmaNummer);
        medikament.setPharmaNummer(pharmaNummer);

        String registerNummer = getString(entry, this.registerNummer);
        medikament.setRegisterNummer(registerNummer);

        String name = getString(entry, this.name);
        medikament.setName(name);

        String box = getString(entry, this.box);
        medikament.setBox(box);

        String kassenZeichen = getString(entry, this.kassenZeichen);
        medikament.setKassenzeichen(kassenZeichen);

        String menge = getString(entry, this.menge);
        medikament.setMenge(menge != null ? Double.parseDouble(menge.replace(",", ".")) : null);

        String mengenArt = getString(entry, this.mengenArt);
        medikament.setMengenart(mengenArt);

        String kvp = getString(entry, this.kvp);
        medikament.setKassenVerkaufsPreis(kvp != null ? Double.parseDouble(kvp) : null);

        String kvpEinheit = getString(entry, this.kvpEinheit);
        medikament.setKvpEinheit(kvpEinheit);

        String darreichungsForm = getString(entry, this.darreichungsform);
        medikament.setDarreichungsForm(darreichungsForm);

        String teilbarkeit = getString(entry, this.teilbarkeit);
        medikament.setTeilbarkeit(teilbarkeit);

        String preisModell = getString(entry, this.preisModell);
        medikament.setPreisModell(preisModell);

        String abgabeAnzahl = getString(entry, this.abgabeAnzahl);
        medikament.setAbgabeAnzahl(abgabeAnzahl != null ? Integer.parseInt(abgabeAnzahl): null);

        String packungsHinweis = getString(entry, this.packungsHinweis);
        medikament.setPackungsHinweis(packungsHinweis);

        String registerNummerPrefix = getString(entry, this.getRegisterNummerPrefix);
        medikament.setRegisterNummernPrefix(registerNummerPrefix);

        String euRegisterNummerPackungsNummer = getString(entry, MedikamentScanner.euRegisterNummerPackungsNummer);
        medikament.setEuRegisterNummernPackungsNummer(euRegisterNummerPackungsNummer);

        return medikament;
    }


}
