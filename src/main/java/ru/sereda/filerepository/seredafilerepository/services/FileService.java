package ru.sereda.filerepository.seredafilerepository.services;

import org.springframework.http.InvalidMediaTypeException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileService {

    private static String DESTINATION = System.getProperty("user.dir") + "/uploads/";

    static {
        if (!new File(DESTINATION).exists()) {
            new File(DESTINATION).mkdir();
        }
    }

    public String getUploadForm() {
        return "<form method='POST' enctype='multipart/form-data' action=''>" +
                "     File to upload: <input type='file' required name='uploadedFile'><br />" +
                "     <input type='submit' value='Upload'> " +
                "</form>";
    }

    public void uploadFile(MultipartFile multipartFile) throws IOException {
        multipartFile.transferTo(new File(DESTINATION + multipartFile.getOriginalFilename()));
    }

    public String getDownloadForm() throws IOException {
        List<String> list = Files.walk(Paths.get(DESTINATION)).filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();

        sb.append("<form method='POST' action=''>");
        list.forEach(s -> sb.append(s).append("<br />"));
        sb.append("</form>");
        return sb.toString();
    }

    public File getFile(String name) {
            return new File(DESTINATION + name);
    }

    public MediaType getMediaType(File file) {
        String mimeType = URLConnection.guessContentTypeFromName(file.getName());
        MediaType mediaType;
        try {
            mediaType = MediaType.parseMediaType(mimeType);
        } catch (InvalidMediaTypeException e) {
            mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }
        return mediaType;
    }
}
