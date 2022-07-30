package org.example.eko.service.scanning.medikament;

import org.example.eko.model.filerepresentations.Regeltext;
import org.example.eko.service.scanning.Scanner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegeltextScanner extends Scanner<Regeltext> {

    @Override
    public List<Regeltext> scan(String data) {
        List<Regeltext> regeltexts = new ArrayList<>();
        var entries = data.split("\n");
        for(String entry : entries){
            var fields = entry.split(";");
            String pharmaNummer = fields[0].replace("|", "").trim();
            String regeltext = fields[1].replace("|", "").trim();
            Regeltext r = new Regeltext();
            r.setRegeltext(regeltext);
            r.setPharmaNummer(pharmaNummer);
            regeltexts.add(r);
        }
        return  regeltexts;
    }
}
