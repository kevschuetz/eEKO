package org.example.eko.service.scanning;

import org.example.eko.model.filerepresentations.WirkstoffFileEntry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WirkstoffScanner extends Scanner<WirkstoffFileEntry> {
    private static ScanningRule pharmaNummer = new ScanningRule(1,7);
    private static final ScanningRule pharAtcCode = new ScanningRule(8, 7);
    private static final ScanningRule wirkAtcCode = new ScanningRule(17, 7);
    private static final ScanningRule laufNummer = new ScanningRule(15, 2);
    private static final ScanningRule wirkstoffStärke = new ScanningRule(24, 10);
    private static final ScanningRule dimensionWirkstoffStärke = new ScanningRule(34, 9);
    private static final ScanningRule wirkstoffEigenschaft = new ScanningRule(43, 20);

    @Override
    public List<WirkstoffFileEntry> scan(String data) {
        List<WirkstoffFileEntry> wirkstoffFileEntries = new ArrayList<>();
        if(data == null) return wirkstoffFileEntries;
        var entries = data.split("\n");
        for(String entry : entries){
            wirkstoffFileEntries.add(scanWirkstoffInformation(entry));
        }

        return  wirkstoffFileEntries;
    }

    private WirkstoffFileEntry scanWirkstoffInformation(String entry) {
        WirkstoffFileEntry wirkstoffFileEntry = null;

        String pharmaNummer = getString(entry, WirkstoffScanner.pharmaNummer);
        String atcCode = getString(entry, WirkstoffScanner.pharAtcCode);
        String atcCodeErgänzt = getString(entry, WirkstoffScanner.wirkAtcCode);
        String laufNummer = getString(entry, WirkstoffScanner.laufNummer);
        String stärke = getString(entry, WirkstoffScanner.wirkstoffStärke);
        String stärkenDimension = getString(entry, WirkstoffScanner.dimensionWirkstoffStärke);
        String eigenschaft = getString(entry, WirkstoffScanner.wirkstoffEigenschaft);

        wirkstoffFileEntry = new WirkstoffFileEntry(pharmaNummer, atcCode, atcCodeErgänzt,
                laufNummer != null ? Integer.parseInt(laufNummer) : null,
                stärke, stärkenDimension, eigenschaft);

        return wirkstoffFileEntry;
    }
}
