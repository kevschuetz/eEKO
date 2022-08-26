package org.example.eko.rest;

import org.example.eko.model.dtos.MedikamentEkoDTO;
import org.example.eko.model.entities.Medikament;
import org.example.eko.model.entities.WirkstoffAtcCode;
import org.example.eko.model.repositories.MedikamentRepository;
import org.example.eko.service.DataService;
import org.example.eko.service.ImportService;
import org.example.eko.service.ScanningService;
import org.example.eko.service.SubstitutionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipInputStream;

@Controller
@RequestMapping("/api/")
public class DataResource {
    private static Logger logger = LoggerFactory.getLogger(DataResource.class);

    private final DataService dataService;
    private final ScanningService scanningService;
    private final ImportService importService;
    private final SubstitutionService substitutionService;
    private final MedikamentRepository medikamentRepository;

    public DataResource(DataService dataService, ScanningService scanningService,
                        ImportService importService, SubstitutionService substitutionService,
                        MedikamentRepository medikamentRepository){
        this.dataService = dataService;
        this.scanningService = scanningService;
        this.importService = importService;
        this.substitutionService = substitutionService;
        this.medikamentRepository = medikamentRepository;
    }

    @PostMapping(value = "/import/zip", headers = "content-type=multipart/*", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> importDataZip(@RequestPart("part1") MultipartFile file1,
                                           @RequestPart("part2") MultipartFile file2,
                                           @RequestParam("date")
                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate){
        try {
            var map = dataService.getFileStringsFromZipIn(new ZipInputStream(file1.getInputStream()));
            map.putAll(dataService.getFileStringsFromZipIn(new ZipInputStream(file2.getInputStream())));
            importService.importDataSet(scanningService.scanFileStrings(map), localDate);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }


    @PostMapping(value = "/import/url")
    public ResponseEntity<Void> importDataUrl(@RequestParam("urlPart1") String url1,
                                           @RequestParam("urlPart2") String url2,
                                           @RequestParam("date")
                                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate localDate){
        var map = dataService.getFileStringsFromDownloadUrl(url1);
        map.putAll(dataService.getFileStringsFromDownloadUrl(url2));
        try {
            importService.importDataSet(scanningService.scanFileStrings(map), localDate);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }

    }

    @GetMapping(value="/substitutes/{pharmaNummer}")
    public ResponseEntity<List<MedikamentEkoDTO>> getSubstitutesByDate(@PathVariable String pharmaNummer,
                                                                 @RequestParam("date")
                                                                 @DateTimeFormat(iso= DateTimeFormat.ISO.DATE) LocalDate date){
        return ResponseEntity.ok(substitutionService.getSubstitutesOrdered(pharmaNummer, date));
    }
}
