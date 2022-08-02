package org.example.eko.service.scanning;

import org.example.eko.model.filerepresentations.HinweisFileEntry;
import org.example.eko.service.scanning.Scanner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HinweisScanner extends Scanner<HinweisFileEntry> {


    public List<HinweisFileEntry> scan(String data){
        List<HinweisFileEntry> hinweise = new ArrayList<>();
        if(data == null) return hinweise;
        var entries = data.split("\n");
        for(String entry : entries){
            var fields = entry.split(";");
            String pharmaNummer = fields[0].replace("|", "").trim();
            String hinweis = fields[1].replace("|", "").trim();
            HinweisFileEntry h = new HinweisFileEntry(pharmaNummer, hinweis);
            hinweise.add(h);
        }
        return  hinweise;
    }
}
