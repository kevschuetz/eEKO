package org.example.eko.service;

import org.example.eko.model.filerepresentations.*;
import org.example.eko.service.scanning.HinweisScanner;
import org.example.eko.service.scanning.MedikamentScanner;
import org.example.eko.service.scanning.RegeltextScanner;
import org.example.eko.service.scanning.WirkstoffScanner;
import org.example.eko.service.scanning.AtcCodeScanner;
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

    public ScanningService(MedikamentScanner medikamentScanner, HinweisScanner hinweisScanner,
                           RegeltextScanner regeltextScanner, AtcCodeScanner atcCodeScanner,
                           WirkstoffScanner wirkstoffInformationScanner){
        this.medikamentScanner = medikamentScanner;
        this.hinweisScanner = hinweisScanner;
        this.regeltextScanner = regeltextScanner;
        this.atcCodeScanner = atcCodeScanner;
        this.wirkstoffScanner = wirkstoffInformationScanner;
    }

    public DataSet scanFiles(Map<String, String> fileStringMap){
        var medikamente = medikamentScanner.scan(fileStringMap.get("medikament.txt"));
        var hinweise = hinweisScanner.scan(fileStringMap.get("hinweis.txt"));
        var regeltext = regeltextScanner.scan(fileStringMap.get("regeltext.txt"));
        var atcCodes = atcCodeScanner.scan(fileStringMap.get("atccode.txt"));
        var wirkstoffe = wirkstoffScanner.scan(fileStringMap.get("wirkstoff.txt"));

        return new DataSet(atcCodes, hinweise, medikamente, regeltext, wirkstoffe);
    }

}
