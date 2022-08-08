package org.example.eko.service.scanning;

import org.example.eko.model.filerepresentations.MedikamentFileEntry;
import org.example.eko.model.filerepresentations.MedikamentZusatzFileEntry;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class MedikamentZusatzScanner extends Scanner<MedikamentZusatzFileEntry> {
    private static final ScanningRule pharmaNummer = new ScanningRule(1, 7);
    private static final ScanningRule langZeitBewilligung = new ScanningRule(8, 3);
    private static final ScanningRule suchtgiftVignette = new ScanningRule(11, 3);

    @Override
    public List<MedikamentZusatzFileEntry> scan(String data) {
        List<MedikamentZusatzFileEntry> entries = new ArrayList<>();
        if(data == null) return  entries;
        var lines = data.split("\n");
        for(String line : lines){
            entries.add(scanMedikamentZusatz(line));
        }

        return  entries;
    }

    private MedikamentZusatzFileEntry scanMedikamentZusatz(String line) {
        String pharmaNummer = getString(line, MedikamentZusatzScanner.pharmaNummer);
        String langzeitBewilligung = getString(line, MedikamentZusatzScanner.langZeitBewilligung);
        String suchtGiftVignette = getString(line, MedikamentZusatzScanner.suchtgiftVignette);

        return new MedikamentZusatzFileEntry(pharmaNummer, langzeitBewilligung, suchtGiftVignette);
    }


}
