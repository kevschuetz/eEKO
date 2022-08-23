package org.example.eko.service.scanning;

import org.example.eko.model.filerepresentations.HinweisFileEntry;
import org.example.eko.model.filerepresentations.WirkstoffInformationFileEntry;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WirkstoffInformationScanner extends Scanner<WirkstoffInformationFileEntry> {


    @Override
    public List<WirkstoffInformationFileEntry> scan(String data) {
        List<WirkstoffInformationFileEntry> wirkstoffInformationen = new ArrayList<>();
        if(data == null) return wirkstoffInformationen;
        var entries = data.split("\n");
        for(String entry : entries){
            var fields = entry.split(";");
            String pharmaNummer = fields[0].replace("|", "").trim();
            String wirkstoffInformation = fields[1].replace("|", "").trim();
            WirkstoffInformationFileEntry h = new WirkstoffInformationFileEntry(pharmaNummer, wirkstoffInformation);
            wirkstoffInformationen.add(h);
        }
        return  wirkstoffInformationen;
    }
}
