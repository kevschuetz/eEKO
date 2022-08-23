package org.example.eko;

import org.example.eko.service.DataService;
import org.example.eko.service.ImportService;
import org.example.eko.service.ScanningService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.zip.ZipInputStream;

@Component
@Profile("test")
public class TestDataLoader {
    private final DataService dataService;
    private final ScanningService scanningService;
    private final ImportService importService;

    public TestDataLoader(DataService dataService, ScanningService scanningService, ImportService importService) throws FileNotFoundException {
        this.dataService = dataService;
        this.scanningService = scanningService;
        this.importService = importService;

        loadData();
    }


    private void loadData() throws FileNotFoundException {
        ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(new File("src/main/resources/ehmv08_22_teil1.zip")));
        var map = dataService.getFileStringsFromZipIn(zipInputStream);
        zipInputStream = new ZipInputStream(new FileInputStream(new File("src/main/resources/ehmv08_22_teil2.zip")));
        map.putAll(dataService.getFileStringsFromZipIn(zipInputStream));
        importService.importDataSet(scanningService.scanFileStrings(map), LocalDate.of(2022, 7 , 1));

        zipInputStream = new ZipInputStream(new FileInputStream(new File("src/main/resources/ehmv08_22_teil1.zip")));
        map = dataService.getFileStringsFromZipIn(zipInputStream);
        zipInputStream = new ZipInputStream(new FileInputStream(new File("src/main/resources/ehmv08_22_teil2.zip")));
        map.putAll(dataService.getFileStringsFromZipIn(zipInputStream));
        importService.importDataSet(scanningService.scanFileStrings(map), LocalDate.of(2022, 8, 1));
    }


}
