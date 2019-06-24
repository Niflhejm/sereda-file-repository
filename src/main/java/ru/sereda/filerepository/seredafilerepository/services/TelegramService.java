package ru.sereda.filerepository.seredafilerepository.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.net.URISyntaxException;

@Component
public class TelegramService {

    private static String TG_SEND_MESSAGE_URL = "https://api.telegram.org/bot820420099:AAFdwfHzo7gf2J93Rg1ugX9fcewevZLVVks/sendMessage?chat_id=-1001213369643&text=";
    private static String TG_SEND_FILE_URL = "https://api.telegram.org/bot820420099:AAFdwfHzo7gf2J93Rg1ugX9fcewevZLVVks/sendDocument?chat_id=-1001213369643";

    @Autowired
    private FileService fileService;

    private RestTemplate restTemplate;

    public void sendMessage(String message) {
        restTemplate = new RestTemplate();
        restTemplate.getForEntity(TG_SEND_MESSAGE_URL + message, String.class);
    }

    public void sendFile(File file) throws URISyntaxException {
        restTemplate = new RestTemplate();

        MediaType mediaType = fileService.getMediaType(file);

        final HttpHeaders headers = new HttpHeaders();
        headers.add("attachment", "filename= +name");
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.setContentType(mediaType);

        HttpEntity<File> request = new HttpEntity<File>(file, headers);

        restTemplate.postForEntity(TG_SEND_FILE_URL, request, String.class);
    }

}
