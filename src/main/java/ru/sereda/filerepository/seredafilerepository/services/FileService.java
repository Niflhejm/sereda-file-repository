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
        return "<html lang=\"en\">\n" +
                "  <head>\n" +
                "    <meta charset=\"utf-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <title>Sereda Repository</title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <h3>Sereda Repository</h3>\n" +
                "\n" +
                "    <form method='POST' enctype='multipart/form-data' action=''>\n" +
                "                File to upload: <input type='file' required name='uploadedFile'><br />\n" +
                "                <br />\n" +
                "                Send to Telegram <input type='checkbox' checked name='sendTelegram'><br />\n" +
                "                <br />\n" +
                "                <input  type='submit' value='Upload'>\n" +
                "                </form>\n" +
                "  </body>\n" +
                "</html>";
    }

    public void uploadFile(MultipartFile multipartFile) throws IOException {
        multipartFile.transferTo(new File(DESTINATION + multipartFile.getOriginalFilename()));
    }

    public String getDownloadForm() throws IOException {
        List<String> list = Files.walk(Paths.get(DESTINATION)).filter(Files::isRegularFile)
                .map(x -> x.getFileName().toString()).collect(Collectors.toList());

        StringBuilder sb = new StringBuilder();

        sb.append("<head>\n" +
                "    <meta charset=\\\"utf-8\\\">\n" +
                "    <meta http-equiv=\\\"X-UA-Compatible\\\" content=\\\"IE=edge\\\">\n" +
                "    <meta name=\\\"viewport\\\" content=\\\"width=device-width, initial-scale=1\\\">\n" +
                "    <title>Sereda Repository</title>\n" +
                "  </head>\n" +
                "     <body>\n" +
                "       <h3>Sereda Repository</h3>");
        sb.append("<form method='POST' action=''>");
        sb.append("<select class=\"form-control\" multiple=\"\" required name='name'>");
        list.forEach(s -> sb.append("<option>").append(s).append("</option>"));
        sb.append("</select>");
        sb.append("<br />");
        sb.append("<br />");
        sb.append("<input  type='submit' value='Download'>");
        sb.append("</form>");
        sb.append("</body>");
        sb.append("</html>");
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
