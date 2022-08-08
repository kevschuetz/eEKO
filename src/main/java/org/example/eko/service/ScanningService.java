package org.example.eko.service;

import org.example.eko.model.filerepresentations.*;
import org.example.eko.service.scanning.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ScanningService {
    private static Logger logger = LoggerFactory.getLogger(ScanningService.class);
    private final MedikamentScanner medikamentScanner;
    private final HinweisScanner hinweisScanner;
    private final RegeltextScanner regeltextScanner;
    private final WirkstoffScanner wirkstoffScanner;
    private final AtcCodeScanner atcCodeScanner;
    private final MedikamentZusatzScanner medikamentZusatzScanner;
    private final RezeptpflichtScanner rezeptpflichtScanner;
    private final WirkstoffInformationScanner wirkstoffInformationScanner;

    public ScanningService(MedikamentScanner medikamentScanner, HinweisScanner hinweisScanner,
                           RegeltextScanner regeltextScanner, AtcCodeScanner atcCodeScanner,
                           WirkstoffScanner wirkstoffScanner, MedikamentZusatzScanner medikamentZusatzScanner,
                           RezeptpflichtScanner rezeptpflichtScanner, WirkstoffInformationScanner wirkstoffInformationScanner){
        this.medikamentScanner = medikamentScanner;
        this.hinweisScanner = hinweisScanner;
        this.regeltextScanner = regeltextScanner;
        this.atcCodeScanner = atcCodeScanner;
        this.wirkstoffScanner = wirkstoffScanner;
        this.medikamentZusatzScanner = medikamentZusatzScanner;
        this.rezeptpflichtScanner = rezeptpflichtScanner;
        this.wirkstoffInformationScanner = wirkstoffInformationScanner;
    }

    public DataSet scanFileStrings(Map<String, String> fileStringMap){
        var medikamente = medikamentScanner.scan(fileStringMap.get("medikament.txt"));
        var hinweise = hinweisScanner.scan(fileStringMap.get("hinweis.txt"));
        var regeltext = regeltextScanner.scan(fileStringMap.get("regeltext.txt"));
        var atcCodes = atcCodeScanner.scan(fileStringMap.get("atccode.txt"));
        var wirkstoffe = wirkstoffScanner.scan(fileStringMap.get("wirkstoff.txt"));
        var medikamentZusaetze = medikamentZusatzScanner.scan(fileStringMap.get("medikament_zusatz.txt"));
        var rezeptpflicht = rezeptpflichtScanner.scan(fileStringMap.get("rezeptpflicht.txt"));
        var wirkstoffInformationen = wirkstoffInformationScanner.scan(fileStringMap.get("wirkstoff_information.txt"));

        return new DataSet(atcCodes, hinweise, medikamente, regeltext, wirkstoffe, medikamentZusaetze, rezeptpflicht, wirkstoffInformationen);
    }

}
