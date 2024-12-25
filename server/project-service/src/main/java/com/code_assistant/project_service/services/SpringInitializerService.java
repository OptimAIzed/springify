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
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
public class SpringInitializerService {
    public ResponseEntity<byte[]> downloadAndModifyZip(String groupId, String artifactId, String name, String description,
                                                       String packageName, String packaging, String javaVersion, String type,
                                                       String language, String bootVersion, String baseDir, String dependencies) throws IOException {

        String url = "https://start.spring.io/starter.zip?groupId=" + groupId + "&artifactId=" + artifactId
                + "&name=" + name + "&description=" + description + "&packageName=" + packageName + "&packaging=" + packaging
                + "&javaVersion=" + javaVersion + "&type=" + type + "&language=" + language + "&bootVersion=" + bootVersion
                + "&baseDir=" + baseDir  + "&dependencies=" + dependencies;

        HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");

        InputStream inputStream = connection.getInputStream();

        Path tempDir = Files.createTempDirectory("modified-zip");
        Path extractedDir = tempDir.resolve("extracted");
        Files.createDirectories(extractedDir);

        // 1. Extract ZIP
        extractZip(inputStream, extractedDir);

        // 2. Create and Add the User related files
        createTableFiles(extractedDir, artifactId, packageName);

        // 3. Re-Zip
        byte[] modifiedZipData = createZipFromDirectory(extractedDir);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 4. Clean-up tempDir
        cleanUp(tempDir);

        return new ResponseEntity<>(modifiedZipData, headers, HttpStatus.OK);
    }

    private void createTableFiles(Path extractedDir, String artifactId, String packageName) throws IOException {
        Path javaSrcDir = extractedDir.resolve(artifactId+"/src/main/java");
        Path packageDir = javaSrcDir.resolve(packageName.replace(".", "/"));

        // 1. Create model Directory
        Path modelDir = packageDir.resolve("model");
        Files.createDirectories(modelDir);
        String content = "X";
        Files.write(modelDir.resolve("User.java"), content.getBytes());

        // 2. Create repository Directory
        Path repositoryDir = packageDir.resolve("repository");
        Files.createDirectories(repositoryDir);
        content = "X";
        Files.write(repositoryDir.resolve("UserRepository.java"), content.getBytes());

        // 3. Create service Directory
        Path serviceDir = packageDir.resolve("service");
        Files.createDirectories(serviceDir);
        content = "X";
        Files.write(serviceDir.resolve("UserService.java"), content.getBytes());

        // 4. Create controller Directory
        Path controllerDir = packageDir.resolve("controller");
        Files.createDirectories(controllerDir);
        content = "X";
        Files.write(controllerDir.resolve("UserController.java"), content.getBytes());
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