package org.example.eko.rest;

import org.example.eko.model.entities.Medikament;
import org.example.eko.service.DataFetchingService;
import org.example.eko.service.ImportService;
import org.example.eko.service.ScanningService;
import org.example.eko.service.SubstitutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/")
public class DataResource {
    private static Logger logger = LoggerFactory.getLogger(DataResource.class);

    private final DataFetchingService dataFetchingService;
    private final ScanningService scanningService;
    private final ImportService importService;
    private final SubstitutionService substitutionService;

    public DataResource(DataFetchingService dataFetchingService, ScanningService scanningService,
                        ImportService importService, SubstitutionService substitutionService){
        this.dataFetchingService = dataFetchingService;
        this.scanningService = scanningService;
        this.importService = importService;
        this.substitutionService = substitutionService;
    }

    @PostMapping("initImport")
    public ResponseEntity<Void> initImport(){
        var map = dataFetchingService.getFilesFromZipAsString(null);
        if(map.isEmpty()){
            return ResponseEntity.status(404).build();
        }
        var scannedMedikamente = scanningService.scanMedikament(map.get("medikament.txt"));
        var importedMedikamente = importService.importMedikamente(scannedMedikamente);

        var hinweise = scanningService.scanHinweise(map.get("hinweis.txt"));
        importedMedikamente = importService.addHinweise(importedMedikamente, hinweise);

        var regelTexte = scanningService.scanRegeltexte(map.get("regeltext.txt"));
        importedMedikamente = importService.addRegeltexte(importedMedikamente, regelTexte);

        var wirkstoffe = scanningService.scanWirkstoffe(map.get("atccode.txt"));
        importService.addWirkstoffe(wirkstoffe);

        var wirkstoffInformationen = scanningService.scanWirkstoffInformationen(map.get("wirkstoff.txt"));
        importedMedikamente = importService.addWirkstoffInformationen(importedMedikamente, wirkstoffInformationen);

        return ResponseEntity.ok().build();
    }

    @GetMapping("medikament/{pharmaNummer}/substitute")
    public ResponseEntity<List<Medikament>> getSubstitutes(@PathVariable String pharmaNummer){
        var s = substitutionService.getSubstitutesForMedikamentHistorical(pharmaNummer);
        return ResponseEntity.ok(null);
    }
}
