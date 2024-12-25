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

        // 2. Create and Add the File
        Path testFile = extractedDir.resolve("testXXX.txt");
        Files.write(testFile, "This is a test file added to the ZIP".getBytes());

        // 3. Re-Zip
        byte[] modifiedZipData = createZipFromDirectory(extractedDir);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // 4. Clean-up tempDir
        cleanUp(tempDir);

        return new ResponseEntity<>(modifiedZipData, headers, HttpStatus.OK);
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

    private static void createZipEntry(ZipOutputStream zos, Path folderPath, String fileName, String fileContent) throws IOException {
        Path filePath = folderPath.resolve(fileName);
        ZipEntry zipEntry = new ZipEntry(filePath.toString().replace("\\", "/")); // Use forward slashes

        zos.putNextEntry(zipEntry);

        try (InputStream is = new ByteArrayInputStream(fileContent.getBytes())) {
            byte[] bytes = new byte[1024];
            int length;
            while ((length = is.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
        }

        zos.closeEntry();
    }

    private static String createRepositoryContent(){
        return  "package com.example.demo.repositories;\n" +
                "\n" +
                "public class UserRepository {\n" +
                "    // Repository logic here\n" +
                "}\n";
    }

    private static String createControllerContent(){
        return "package com.example.demo.controllers;\n" +
                "\n" +
                "public class UserController {\n" +
                "    // Controller logic here\n" +
                "}\n";
    }

    private static String createEntityContent(){
        return "package com.example.demo.entities;\n" +
                "\n" +
                "public class User {\n" +
                "    // Entity properties here\n" +
                "}\n";
    }

    private static String createServiceContent(){
        return  "package com.example.demo.services;\n" +
                "\n" +
                "public class UserService {\n" +
                "    // Service logic here\n" +
                "}\n";
    }
}