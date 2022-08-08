package org.example.eko.service.scanning;

import org.example.eko.model.filerepresentations.MedikamentZusatzFileEntry;
import org.example.eko.model.filerepresentations.RezeptpflichtFileEntry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RezeptpflichtScanner extends Scanner<RezeptpflichtFileEntry> {
    private static final ScanningRule pharmaNummer = new ScanningRule(1, 7);
    private static final ScanningRule isRezeptpflichtig = new ScanningRule(8, 1);
    private static final ScanningRule rezeptpflichtBezeichnung = new ScanningRule(9, 60);


    @Override
    public List<RezeptpflichtFileEntry> scan(String data) {
        List<RezeptpflichtFileEntry> entries = new ArrayList<>();
        if(data == null) return  entries;
        var lines = data.split("\n");
        for(String line : lines){
            entries.add(scanRezeptpflichtEntry(line));
        }

        return  entries;
    }

    private RezeptpflichtFileEntry scanRezeptpflichtEntry(String line) {
        String pharmaNummer = getString(line, RezeptpflichtScanner.pharmaNummer);

        String isRezeptpflichtString = getString(line, RezeptpflichtScanner.isRezeptpflichtig);
        Integer isRezeptflichtInt = isRezeptpflichtString != null ? Integer.parseInt(isRezeptpflichtString) : null;

        String rezeptPflichtBezeichnung = getString(line, RezeptpflichtScanner.rezeptpflichtBezeichnung);



        return new RezeptpflichtFileEntry(pharmaNummer, isRezeptflichtInt, rezeptPflichtBezeichnung);
    }
}
