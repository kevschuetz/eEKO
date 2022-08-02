package org.example.eko.rest;

import org.example.eko.model.entities.Medikament;
import org.example.eko.model.entities.WirkstoffAtcCode;
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

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
        importService.importDataSet(scanningService.scanFiles(map));

        return ResponseEntity.ok().build();
    }

    @GetMapping("medikament/{pharmaNummer}/substitute")
    public ResponseEntity<Map<WirkstoffAtcCode, Map<WirkstoffAtcCode, List<Medikament>>>> getSubstitutes(@PathVariable String pharmaNummer){
        var s = substitutionService.getSubstitutesForMedikament(pharmaNummer, LocalDate.of(2022, 07, 30));
        return ResponseEntity.ok(s);
    }
}
