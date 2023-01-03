package org.example.eko;

import org.apache.tomcat.jni.Local;
import org.example.eko.service.DataMartService;
import org.example.eko.service.DataService;
import org.example.eko.service.ImportService;
import org.example.eko.service.ScanningService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.*;
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
        //if(!Boolean.parseBoolean(System.getenv("LOAD_TEST_DATA"))) return;
        boolean imported = false;
        String fileName = "ehmv08_22_teil1.zip";


        while(getResourceFileAsInputStream(fileName) != null) {
            ZipInputStream zipInputStream = new ZipInputStream(getResourceFileAsInputStream(fileName));
            var map = dataService.getFileStringsFromZipIn(zipInputStream);
            String[] strings = fileName.split("_", 3);
            fileName = strings[0] + strings[1] + "teil2.zip";
            zipInputStream = new ZipInputStream(getResourceFileAsInputStream(fileName));
            map.putAll(dataService.getFileStringsFromZipIn(zipInputStream));

            String yearString = "20" + strings[1];
            int year = Integer.parseInt(yearString);
            String monthStr = "";

            monthStr += strings[0].charAt(4);
            monthStr += strings[0].charAt(5);
            int month = Integer.parseInt(monthStr);

            imported = importService.importDataSet(scanningService.scanFileStrings(map), LocalDate.of(year, month , 1));
            if(imported) {
                dataMartService.migrateDataForGivenDate(LocalDate.of(year, month, 1));
            }

            int nextYear;
            int nextMonth;
            if(month == 11) nextYear = year + 1;
            else nextYear = year;
            nextMonth = month + 1;

            String nextYearString = String.valueOf(String.valueOf(nextYear).toCharArray()[2] + String.valueOf(nextYear).toCharArray()[3]);

            fileName = "ehmv" + nextMonth + "_" + nextYearString + "_teil1.zip";
        }


        /*
        ZipInputStream zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv08_22_teil1.zip"));
        var map = dataService.getFileStringsFromZipIn(zipInputStream);
        zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv08_22_teil2.zip"));
        map.putAll(dataService.getFileStringsFromZipIn(zipInputStream));
        imported = importService.importDataSet(scanningService.scanFileStrings(map), LocalDate.of(2022, 8 , 1));

        if(imported) dataMartService.migrateDataForGivenDate(LocalDate.of(2022, 8, 1));

        zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv09_22_teil1.zip"));
        map = dataService.getFileStringsFromZipIn(zipInputStream);
        zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv09_22_teil2.zip"));
        map.putAll(dataService.getFileStringsFromZipIn(zipInputStream));
        imported = importService.importDataSet(scanningService.scanFileStrings(map), LocalDate.of(2022, 9, 1));

        if(imported)dataMartService.migrateDataForGivenDate(LocalDate.of(2022, 9, 1));

        zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv10_22_teil1.zip"));
        map = dataService.getFileStringsFromZipIn(zipInputStream);
        zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv10_22_teil2.zip"));
        map.putAll(dataService.getFileStringsFromZipIn(zipInputStream));
        imported=importService.importDataSet(scanningService.scanFileStrings(map), LocalDate.of(2022, 10, 1));


        if(imported)dataMartService.migrateDataForGivenDate(LocalDate.of(2022, 10, 1));

        zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv11_22_teil1.zip"));
        map = dataService.getFileStringsFromZipIn(zipInputStream);
        zipInputStream = new ZipInputStream(getResourceFileAsInputStream("ehmv11_22_teil2.zip"));
        map.putAll(dataService.getFileStringsFromZipIn(zipInputStream));
        imported=importService.importDataSet(scanningService.scanFileStrings(map), LocalDate.of(2022, 11, 1));

        if(imported)dataMartService.migrateDataForGivenDate(LocalDate.of(2022, 11, 1));
        */
    }

    public static InputStream getResourceFileAsInputStream(String fileName) {
        ClassLoader classLoader = TestDataLoader.class.getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }
    private void saveToDateFile(String date) throws IOException {
        File fileToWriteTo = new File(".../resources/importedDates.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileToWriteTo, true));
        writer.append(".").append(date);
    }
}
