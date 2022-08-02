package org.example.eko.service.scanning;

import org.example.eko.model.filerepresentations.RegeltextFileEntry;
import org.example.eko.service.scanning.Scanner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegeltextScanner extends Scanner<RegeltextFileEntry> {

    @Override
    public List<RegeltextFileEntry> scan(String data) {
        List<RegeltextFileEntry> regeltextFileEntries = new ArrayList<>();
        if(data == null) return regeltextFileEntries;
        var entries = data.split("\n");
        for(String entry : entries){
            var fields = entry.split(";");
            String pharmaNummer = fields[0].replace("|", "").trim();
            String regeltext = fields[1].replace("|", "").trim();
            RegeltextFileEntry r = new RegeltextFileEntry(pharmaNummer, regeltext);
            regeltextFileEntries.add(r);
        }
        return regeltextFileEntries;
    }
}
