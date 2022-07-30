package org.example.eko.service.scanning.medikament;

import org.example.eko.model.filerepresentations.Hinweis;
import org.example.eko.service.scanning.Scanner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class HinweisScanner extends Scanner<Hinweis> {


    public List<Hinweis> scan(String data){
        List<Hinweis> hinweise = new ArrayList<>();
        var entries = data.split("\n");
        for(String entry : entries){
            var fields = entry.split(";");
            String pharmaNummer = fields[0].replace("|", "").trim();
            String hinweis = fields[1].replace("|", "").trim();
            Hinweis h = new Hinweis();
            h.setHinweis(hinweis);
            h.setPharmaNummer(pharmaNummer);
            hinweise.add(h);
        }
        return  hinweise;
    }
}
