package org.example.eko;

import org.apache.tomcat.jni.Local;
import org.example.eko.service.DataMartService;
import org.example.eko.service.DataService;
import org.example.eko.service.ImportService;
import org.example.eko.service.ScanningService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.zip.ZipInputStream;

@Component
public class TestDataLoader {
    private final DataService dataService;
    private final ScanningService scanningService;
    private final ImportService importService;
    private final DataMartService dataMartService;

    public TestDataLoader(DataService dataService, ScanningService scanningService, ImportService importService, DataMartService dataMartService) throws Exception {
        this.dataService = dataService;
        this.scanningService = scanningService;
        this.importService = importService;
        this.dataMartService = dataMartService;

        loadData();
    }


    private void loadData() throws Exception {
        if(!Boolean.parseBoolean(System.getenv("LOAD_TEST_DATA"))) return;

        ZipInputStream zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv08_22_teil1.zip"));
        var map = dataService.getFileStringsFromZipIn(zipInputStream);
        zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv08_22_teil2.zip"));
        map.putAll(dataService.getFileStringsFromZipIn(zipInputStream));
        importService.importDataSet(scanningService.scanFileStrings(map), LocalDate.of(2022, 8 , 1));

        dataMartService.migrateDataForGivenDate(LocalDate.of(2022, 8, 1));

        zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv09_22_teil1.zip"));
        map = dataService.getFileStringsFromZipIn(zipInputStream);
        zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv09_22_teil2.zip"));
        map.putAll(dataService.getFileStringsFromZipIn(zipInputStream));
        importService.importDataSet(scanningService.scanFileStrings(map), LocalDate.of(2022, 9, 1));

        dataMartService.migrateDataForGivenDate(LocalDate.of(2022, 9, 1));

        zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv10_22_teil1.zip"));
        map = dataService.getFileStringsFromZipIn(zipInputStream);
        zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv10_22_teil2.zip"));
        map.putAll(dataService.getFileStringsFromZipIn(zipInputStream));
        importService.importDataSet(scanningService.scanFileStrings(map), LocalDate.of(2022, 10, 1));


        dataMartService.migrateDataForGivenDate(LocalDate.of(2022, 10, 1));

        zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv11_22_teil1.zip"));
        map = dataService.getFileStringsFromZipIn(zipInputStream);
        zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv11_22_teil2.zip"));
        map.putAll(dataService.getFileStringsFromZipIn(zipInputStream));
        importService.importDataSet(scanningService.scanFileStrings(map), LocalDate.of(2022, 11, 1));

        dataMartService.migrateDataForGivenDate(LocalDate.of(2022, 11, 1));
    }

    public static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = TestDataLoader.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }
}
