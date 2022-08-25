package org.example.eko.service.scanning;

import org.example.eko.model.filerepresentations.HinweisFileEntry;
import org.example.eko.model.filerepresentations.IndTextFileEntry;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Component
public class IndTextScanner extends Scanner<IndTextFileEntry> {

    @Override
    public List<IndTextFileEntry> scan(String data) {
        List<IndTextFileEntry> indTextFileEntries = new ArrayList<>();
        if(data == null) return indTextFileEntries;
        var entries = data.split("\n");
        for(String entry : entries){
            var fields = entry.split(";");
            String pharmaNummer = fields[0].replace("|", "").trim();
            String indText = fields[1].replace("|", "").trim();
            IndTextFileEntry h = new IndTextFileEntry(pharmaNummer, indText);
            indTextFileEntries.add(h);
        }
        return  indTextFileEntries;
    }
}
