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
        // Get file from website
        Map<String, File> files = null;
        try {
             files = extractFilesFromZipIn(zipInputStream);
        } catch (IOException e) {
            logger.info(e.getMessage());
        }
        if(files == null) return fileToFileStringMap;


        for(Map.Entry<String, File> entry : files.entrySet()){
            if(!entry.getKey().contains(".txt")) continue;

            logger.info("----------------------------------------------------");
            logger.info("Current File: " + entry.getKey());
            try {
                String fileString = fileToString(entry.getValue(), "Cp1252");
                fileToFileStringMap.put(entry.getKey(), fileString);
            } catch (IOException e) {
                logger.info("Could not read file.");
                logger.info(e.getMessage());
            }
            logger.info("---------------------------------------------------");
        }

        // Delete temp directory
        boolean deletedDirectory = false;
        if(!files.isEmpty()) {
            deletedDirectory = deleteDirectory(files.entrySet().stream().findFirst().get().getValue().getParentFile());
        }
        logger.debug("Deleted directory : " + deletedDirectory + ".");
        return  fileToFileStringMap;
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

    private ZipInputStream downloadZipInputStream(String downloadUrl) throws IOException {
        URL url = new URL(downloadUrl != null ? downloadUrl : "https://www.sozialversicherung.at/cdscontent/load?contentid=10008.738247&version=1658833511");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        InputStream inputStream = connection.getInputStream();
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        return zipInputStream;
    }

    private Map<String, File> extractFilesFromZipIn(ZipInputStream zipIn) throws IOException {
        Map<String, File> fileMap = new HashMap();

        byte[] buffer = new byte[2048];
        Path outDir = Paths.get("src/main/resources/test2");

        ZipEntry entry = zipIn.getNextEntry();
        while (entry != null) {
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
