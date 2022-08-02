package org.example.eko.service.scanning;

import org.example.eko.model.filerepresentations.MedikamentFileEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class MedikamentScanner extends Scanner<MedikamentFileEntry> {
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


    @Override
    public List<MedikamentFileEntry> scan(String data){
        List<MedikamentFileEntry> entries = new ArrayList<>();
        if(data == null) return  entries;
        LocalDate date = LocalDate.of(2022, 7, 29);
        var lines = data.split("\n");
        for(String line : lines){
            entries.add(scanMedikament(line));
        }

        return  entries;
    }

    private MedikamentFileEntry scanMedikament(String entry) {
        MedikamentFileEntry medikamentFileEntry = null;

        String pharmaNummer = getString(entry, this.pharmaNummer);
        String registerNummer = getString(entry, this.registerNummer);
        String name = getString(entry, this.name);
        String box = getString(entry, this.box);
        String kassenZeichen = getString(entry, this.kassenZeichen);
        String menge = getString(entry, this.menge);
        String mengenArt = getString(entry, this.mengenArt);
        String kvp = getString(entry, this.kvp);
        String kvpEinheit = getString(entry, this.kvpEinheit);
        String darreichungsForm = getString(entry, this.darreichungsform);
        String teilbarkeit = getString(entry, this.teilbarkeit);
        String preisModell = getString(entry, this.preisModell);
        String abgabeAnzahl = getString(entry, this.abgabeAnzahl);
        String packungsHinweis = getString(entry, this.packungsHinweis);
        String registerNummerPrefix = getString(entry, this.getRegisterNummerPrefix);
        String euRegisterNummerPackungsNummer = getString(entry, MedikamentScanner.euRegisterNummerPackungsNummer);

        medikamentFileEntry = new MedikamentFileEntry(pharmaNummer, registerNummer, name, box, kassenZeichen, menge != null ? Double.parseDouble(menge.replace(",", ".")) : null,
                mengenArt, kvp != null ? Double.parseDouble(kvp) : null, kvpEinheit, darreichungsForm, teilbarkeit,
                preisModell, abgabeAnzahl != null ? Double.parseDouble(abgabeAnzahl) : null, packungsHinweis, registerNummerPrefix, euRegisterNummerPackungsNummer);

        return medikamentFileEntry;
    }


}
