package org.example.eko.service;

import org.example.eko.model.filerepresentations.Hinweis;
import org.example.eko.model.filerepresentations.Regeltext;
import org.example.eko.model.entities.Medikament;
import org.example.eko.model.entities.WirkstoffAtcCode;
import org.example.eko.model.entities.WirkstoffInformation;
import org.example.eko.service.scanning.medikament.HinweisScanner;
import org.example.eko.service.scanning.medikament.MedikamentScanner;
import org.example.eko.service.scanning.medikament.RegeltextScanner;
import org.example.eko.service.scanning.medikament.WirkstoffInformationScanner;
import org.example.eko.service.scanning.wirkstoff.WirkstoffScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class ScanningService {
    private static Logger logger = LoggerFactory.getLogger(ScanningService.class);
    private final MedikamentScanner medikamentScanner;
    private final HinweisScanner hinweisScanner;
    private final RegeltextScanner regeltextScanner;
    private final WirkstoffScanner wirkstoffScanner;
    private final WirkstoffInformationScanner wirkstoffInformationScanner;

    public ScanningService(MedikamentScanner medikamentScanner, HinweisScanner hinweisScanner,
                           RegeltextScanner regeltextScanner, WirkstoffScanner wirkstoffScanner,
                           WirkstoffInformationScanner wirkstoffInformationScanner){
        this.medikamentScanner = medikamentScanner;
        this.hinweisScanner = hinweisScanner;
        this.regeltextScanner = regeltextScanner;
        this.wirkstoffScanner = wirkstoffScanner;
        this.wirkstoffInformationScanner = wirkstoffInformationScanner;
    }

    public List<Medikament> scanMedikament(String data){
        Objects.requireNonNull(data);
        return medikamentScanner.scan(data);
    }

    public List<Hinweis> scanHinweise(String data){
       Objects.requireNonNull(data);
       return hinweisScanner.scan(data);
    }

    public List<Regeltext> scanRegeltexte(String data){
        Objects.requireNonNull(data);
        return regeltextScanner.scan(data);
    }

    public List<WirkstoffAtcCode> scanWirkstoffe(String data) {
        Objects.requireNonNull(data);
        return wirkstoffScanner.scan(data);
    }

    public List<WirkstoffInformation> scanWirkstoffInformationen(String data) {
        Objects.requireNonNull(data);
        return wirkstoffInformationScanner.scan(data);
    }
}
