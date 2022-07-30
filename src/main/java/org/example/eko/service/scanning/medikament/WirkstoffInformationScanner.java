package org.example.eko.service.scanning.medikament;

import org.apache.commons.lang3.StringUtils;
import org.example.eko.model.entities.WirkstoffInformation;
import org.example.eko.model.repositories.WirkstoffRepository;
import org.example.eko.service.scanning.Scanner;
import org.example.eko.service.scanning.ScanningRule;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class WirkstoffInformationScanner extends Scanner<WirkstoffInformation> {
    private static ScanningRule pharmaNummer = new ScanningRule(1,7);
    private static final ScanningRule pharAtcCode = new ScanningRule(8, 7);
    private static final ScanningRule wirkAtcCode = new ScanningRule(17, 7);
    private static final ScanningRule laufNummer = new ScanningRule(15, 2);
    private static final ScanningRule wirkstoffStärke = new ScanningRule(24, 10);
    private static final ScanningRule dimensionWirkstoffStärke = new ScanningRule(34, 9);
    private static final ScanningRule wirkstoffEigenschaft = new ScanningRule(43, 20);

    private final WirkstoffRepository wirkstoffRepository;

    public WirkstoffInformationScanner(WirkstoffRepository wirkstoffRepository){
        this.wirkstoffRepository = wirkstoffRepository;
    }

    @Override
    public List<WirkstoffInformation> scan(String data) {
        List<WirkstoffInformation> wirkstoffInformations = new ArrayList<>();
        var entries = data.split("\n");
        for(String entry : entries){
            wirkstoffInformations.add(scanWirkstoffInformation(entry));
        }

        return  wirkstoffInformations;
    }

    private WirkstoffInformation scanWirkstoffInformation(String entry) {
        WirkstoffInformation information = new WirkstoffInformation();

        String pharmaNummer = getString(entry, WirkstoffInformationScanner.pharmaNummer);
        information.setPharmaNummer(pharmaNummer);
        String atcCode = getString(entry, WirkstoffInformationScanner.pharAtcCode);
        information.setPharWirkstoff(wirkstoffRepository.findById(atcCode).get());
        String atcCodeErgänzt = getString(entry, WirkstoffInformationScanner.wirkAtcCode);
        information.setWirkWirkstoff(StringUtils.isNotBlank(atcCodeErgänzt.replace("_", "")) ? wirkstoffRepository.findById(atcCodeErgänzt).get() : null);
        String laufNummer = getString(entry, WirkstoffInformationScanner.laufNummer);
        information.setLaufNummer(laufNummer != null ? Integer.parseInt(laufNummer) : null);
        String stärke = getString(entry, WirkstoffInformationScanner.wirkstoffStärke);
        information.setWirkstoffStärke(stärke);
        String stärkenDimension = getString(entry, WirkstoffInformationScanner.dimensionWirkstoffStärke);
        information.setWirkstoffStärkenDimension(stärkenDimension);
        String eigenschaft = getString(entry, WirkstoffInformationScanner.wirkstoffEigenschaft);
        information.setWirkstoffEigenschaft(eigenschaft);

        return information;
    }
}
