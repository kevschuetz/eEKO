package org.example.eko.service.scanning;

import org.example.eko.model.filerepresentations.AtcCodeFileEntry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AtcCodeScanner extends Scanner<AtcCodeFileEntry> {
    private static final ScanningRule codeRule = new ScanningRule(1, 7);
    private static final ScanningRule textRule = new ScanningRule(8, 200);


    @Override
    public List<AtcCodeFileEntry> scan(String data) {
        List<AtcCodeFileEntry> wirkstoffe = new ArrayList<>();
        if(data == null) return  wirkstoffe;
        var entries = data.split("\n");
        for(String entry : entries){
            wirkstoffe.add(scanWirkstoff(entry));
        }

        return  wirkstoffe;
    }

    private AtcCodeFileEntry scanWirkstoff(String entry) {
        String code = getString(entry, AtcCodeScanner.codeRule);
        String text = getString(entry, AtcCodeScanner.textRule);
        return new AtcCodeFileEntry(code, text);
    }
}
