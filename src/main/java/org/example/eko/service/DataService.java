package org.example.eko.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Service
public class DataService {
    private static Logger logger = LoggerFactory.getLogger(DataService.class);

    public Map<String, String> getFileStringsFromDownloadUrl(String zipDownloadUrl) {
        ZipInputStream zipInputStream = null;
        try {
            zipInputStream = downloadZipInputStream(zipDownloadUrl);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return getFileStringsFromZipIn(zipInputStream);
    }

    public Map<String, String> getFileStringsFromZipIn(ZipInputStream zipInputStream)  {
        Map<String, String> fileToFileStringMap = new HashMap<>();
        try {
            var entry = zipInputStream.getNextEntry();
            while(entry != null) {
                if (!entry.getName().contains(".txt")) {
                    entry = getNextValidEntry(zipInputStream);
                    continue;
                }
                // copy content of zipentry to baos
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[2048];
                int len;
                while ((len = zipInputStream.read(buffer)) > -1) {
                    baos.write(buffer, 0, len);
                }
                baos.flush();
                // close entry
                zipInputStream.closeEntry();
                // create reader to read from baos
                var r = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(baos.toByteArray()), "Cp1252"));
                StringBuilder sb = new StringBuilder();
                r.lines().forEach(line -> sb.append(line).append("\n"));
                fileToFileStringMap.put(entry.getName(), sb.toString());
                zipInputStream.closeEntry();
                // close baos and r
                r.close();
                baos.close();
                logger.info("Read entry {}.", entry.getName());

                entry = getNextValidEntry(zipInputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileToFileStringMap;
    }

    private ZipEntry getNextValidEntry(ZipInputStream zipInputStream) {
        boolean foundNextValidFile = false;
        ZipEntry entry = null;
        while(!foundNextValidFile){
            try {
                entry = zipInputStream.getNextEntry();
                foundNextValidFile = true;
            }catch (Exception exception){
                logger.info("Could not get zip entry.");
                logger.info(exception.getMessage());
            }
        }
        return entry;
    }


    private ZipInputStream downloadZipInputStream(String downloadUrl) throws IOException {
        URL url = new URL(downloadUrl != null ? downloadUrl : "https://www.sozialversicherung.at/cdscontent/load?contentid=10008.738247&version=1658833511");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        return zipInputStream;
    }

    // archived methods

    private Map<String, File> extractFilesFromZipIn(ZipInputStream zipIn) throws IOException {
        Map<String, File> fileMap = new HashMap();

        byte[] buffer = new byte[2048];
        Path outDir = Paths.get("src/main/resources/test2");

        ZipEntry entry = zipIn.getNextEntry();
        while (entry != null) {
            if(entry.getName().contains(".txt")){
                var r = new BufferedReader(new InputStreamReader(zipIn, "CP1252"));
                r.lines().forEach(logger::info);
                r.close();
            }
            File newFile = new File(outDir.toFile(), entry.getName());
            if (entry.isDirectory()) {
                if (!newFile.isDirectory() && !newFile.mkdirs()) {
                    throw new IOException("Failed to create directory " + newFile);
                }
            } else {
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }

                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;

                while ((len = zipIn.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.close();
            }
            fileMap.put(entry.getName(), newFile);

            boolean foundNextValidFile = false;
            while(!foundNextValidFile){
                try {
                    entry = zipIn.getNextEntry();
                    foundNextValidFile = true;
                }catch (Exception exception){
                    logger.info("Could not get zip entry.");
                    logger.info(exception.getMessage());
                }
            }
            }
        return fileMap;
    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }

    private String fileToString(File file, String charSetName) throws IOException {
        StringBuilder sb = new StringBuilder();
        try(var reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charSetName))){
            String line;
            while((line = reader.readLine()) != null){
                sb.append(line).append("\n");
            }
        }catch (IOException e){
            logger.info("Could not read from file: {}, with charset: {}", file, charSetName);
        }
        return sb.toString();
    }

}
