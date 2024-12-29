package com.code_assistant.project_service.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
public class SpringInitializerService {
    public ResponseEntity<byte[]> downloadAndModifyZip(String groupId, String artifactId, String name, String description,
                                                       String packageName, String packaging, String javaVersion, String type,
                                                       String language, String bootVersion, String baseDir, String dependencies,
                                                       HashMap<String, List<HashMap<String, String>>> content) throws IOException {

        String url = "https://start.spring.io/starter.zip?groupId=" + groupId + "&artifactId=" + artifactId
                + "&name=" + name + "&description=" + description + "&packageName=" + packageName + "&packaging=" + packaging
                + "&javaVersion=" + javaVersion + "&type=" + type + "&language=" + language + "&bootVersion=" + bootVersion
                + "&baseDir=" + baseDir  + "&dependencies=" + dependencies;
        System.out.println("this is the content : " + content);
        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream = connection.getInputStream();

        Path tempDir = Files.createTempDirectory("modified-zip");
        Path extractedDir = tempDir.resolve("extracted");
        Files.createDirectories(extractedDir);
        // 1. Extract ZIP
        extractZip(inputStream, extractedDir);

        // 2. Create and Add the User related files
        Path javaSrcDir = extractedDir.resolve(artifactId+"/src/main/java");
        Path packageDir = javaSrcDir.resolve(packageName.replace(".", "/"));

        // 2.Model
        List<HashMap<String, String>> modelFiles = content.get("model");
        if (modelFiles != null) {
            for (HashMap<String, String> fileMap : modelFiles) {
                for (Map.Entry<String, String> fileEntry : fileMap.entrySet()){
                    String fileName = extractFileName(fileEntry.getKey());
                    String fileContent = fileEntry.getValue();
                    createModelFiles(packageDir, fileName, fileContent);
                }
            }
        }
        // 2.Repository
        List<HashMap<String, String>> repositoryFiles = content.get("repository");
        if (repositoryFiles != null) {
            for (HashMap<String, String> fileMap : repositoryFiles) {
                for (Map.Entry<String, String> fileEntry : fileMap.entrySet()){
                    String fileName = extractFileName(fileEntry.getKey());
                    String fileContent = fileEntry.getValue();
                    createRepositoryFiles(packageDir, fileName, fileContent);
                }
            }
        }
        // 2.Service
        List<HashMap<String, String>> serviceFiles = content.get("service");
        if (serviceFiles != null) {
            for (HashMap<String, String> fileMap : serviceFiles) {
                for (Map.Entry<String, String> fileEntry : fileMap.entrySet()){
                    String fileName = extractFileName(fileEntry.getKey());
                    String fileContent = fileEntry.getValue();
                    createServiceFiles(packageDir, fileName, fileContent);
                }
            }
        }
        // 2.Controller
        List<HashMap<String, String>> controllerFiles = content.get("controller");
        if (controllerFiles != null) {
            for (HashMap<String, String> fileMap : controllerFiles) {
                for (Map.Entry<String, String> fileEntry : fileMap.entrySet()){
                    String fileName = extractFileName(fileEntry.getKey());
                    String fileContent = fileEntry.getValue();
                    createControllerFiles(packageDir, fileName, fileContent);
                }
            }
        }

        // 3. Re-Zip
        byte[] modifiedZipData = createZipFromDirectory(extractedDir);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 4. Clean-up tempDir
        cleanUp(tempDir);

        return new ResponseEntity<>(modifiedZipData, headers, HttpStatus.OK);
    }

    private void createModelFiles(Path packageDir, String filename, String content) throws IOException {
        Path modelDir = packageDir.resolve("model");
        Files.createDirectories(modelDir);
        Files.write(modelDir.resolve(filename), content.getBytes());
    }

    private void createRepositoryFiles(Path packageDir, String filename, String content) throws IOException {
        Path repositoryDir = packageDir.resolve("repository");
        Files.createDirectories(repositoryDir);
        Files.write(repositoryDir.resolve(filename), content.getBytes());
    }

    private void createServiceFiles(Path packageDir, String filename, String content) throws IOException {
        Path serviceDir = packageDir.resolve("service");
        Files.createDirectories(serviceDir);
        Files.write(serviceDir.resolve(filename), content.getBytes());
    }

    private void createControllerFiles(Path packageDir, String filename, String content) throws IOException {
        Path controllerDir = packageDir.resolve("controller");
        Files.createDirectories(controllerDir);
        Files.write(controllerDir.resolve(filename), content.getBytes());
    }

    public static String extractFileName(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return null;
        }

        Path path = Paths.get(filePath);
        Path fileNamePath = path.getFileName();

        int lastDotIndex = filePath.lastIndexOf('.');
        int secondToLastDotIndex = filePath.lastIndexOf('.', lastDotIndex - 1);

        if (secondToLastDotIndex != -1)
        {
            return filePath.substring(secondToLastDotIndex + 1);
        }
        if (fileNamePath != null) {
            return fileNamePath.toString();
        }
        return null;
    }

    private static void cleanUp(Path tempDir) throws IOException {
        Files.walk(tempDir)
                .sorted((p1,p2)-> p2.compareTo(p1))
                .forEach(file -> {
                    try {
                        Files.delete(file);
                    }catch (IOException e){
                        //log error message
                    }
                });
    }

    private void extractZip(InputStream inputStream, Path targetDir) throws IOException {
        try (ZipInputStream zipInputStream = new ZipInputStream(inputStream)) {
            ZipEntry zipEntry;
            while ((zipEntry = zipInputStream.getNextEntry()) != null) {
                Path entryPath = targetDir.resolve(zipEntry.getName());
                if (zipEntry.isDirectory()) {
                    Files.createDirectories(entryPath);
                } else {
                    Files.createDirectories(entryPath.getParent());
                    try (FileOutputStream fos = new FileOutputStream(entryPath.toFile())) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zipInputStream.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
                zipInputStream.closeEntry();
            }
        }
    }

    private byte[] createZipFromDirectory(Path sourceDir) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            Files.walk(sourceDir)
                    .filter(path -> !path.equals(sourceDir))
                    .forEach(path -> {
                        try{
                            Path relativePath = sourceDir.relativize(path);
                            if(Files.isDirectory(path)) {
                                zipOutputStream.putNextEntry(new ZipEntry(relativePath.toString() + "/"));
                                zipOutputStream.closeEntry();
                            }else {
                                zipOutputStream.putNextEntry(new ZipEntry(relativePath.toString()));
                                Files.copy(path,zipOutputStream);
                                zipOutputStream.closeEntry();
                            }

                        }catch (IOException e){
                            //Log the error
                        }
                    });
        }
        return byteArrayOutputStream.toByteArray();
    }
}