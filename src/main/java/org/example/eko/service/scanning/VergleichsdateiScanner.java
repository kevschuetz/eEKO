package org.example.eko.service.scanning;

import org.apache.commons.lang3.StringUtils;
import org.example.eko.model.filerepresentations.MedikamentZusatzFileEntry;
import org.example.eko.model.filerepresentations.SubstitutionEntry;
import org.example.eko.model.filerepresentations.VergleichsdateiFileEntry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VergleichsdateiScanner extends Scanner<VergleichsdateiFileEntry> {
    private static final ScanningRule pharmaNummer = new ScanningRule(1, 7);
    private static final ScanningRule position = new ScanningRule(8,2);
    private static final Integer pharmaNummerVergleichStart = 10;
    private static final Integer positionVergleichStart = 17;
    private static final Integer vergleichsKennzeichenStart = 19;
    private static final Integer offset = 10;

    @Override
    public List<VergleichsdateiFileEntry> scan(String data) {
        List<VergleichsdateiFileEntry> entries = new ArrayList<>();
        if(data == null) return  entries;
        var lines = data.split("\n");
        for(String line : lines){
            entries.add(scanVergleichsEntry(line));
        }

        return  entries;
    }

    private VergleichsdateiFileEntry scanVergleichsEntry(String line) {
        String pharmaNummer = getString(line, VergleichsdateiScanner.pharmaNummer);
        String position = getString(line, VergleichsdateiScanner.position);

        List<SubstitutionEntry> substitutionEntries = new ArrayList<>();
         // ...
        int currentOffset = 0;
        while(true){
            if(line.length() < currentOffset + VergleichsdateiScanner.offset) break;

            String pharmaNummerS = getString(line, new ScanningRule(VergleichsdateiScanner.pharmaNummerVergleichStart + currentOffset, 7));
            String positionS = getString(line, new ScanningRule(VergleichsdateiScanner.positionVergleichStart + currentOffset, 2));
            String vergleichsKennzeichen = getString(line, new ScanningRule(VergleichsdateiScanner.vergleichsKennzeichenStart + currentOffset, 1));

            if(StringUtils.isEmpty(pharmaNummer)) break;

            substitutionEntries.add(new SubstitutionEntry(pharmaNummerS, positionS != null ? Integer.parseInt(positionS) : -1, vergleichsKennzeichen != null ? Integer.parseInt(vergleichsKennzeichen) : -1));
            currentOffset += VergleichsdateiScanner.offset;
        }

        return new VergleichsdateiFileEntry(pharmaNummer, position != null ? Integer.parseInt(position) : -1, substitutionEntries);
    }
}
