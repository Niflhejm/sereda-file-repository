package ru.sereda.filerepository.seredafilerepository.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class TelegramService {

    private static String TG_SEND_URL = "https://api.telegram.org/bot820420099:AAFdwfHzo7gf2J93Rg1ugX9fcewevZLVVks/sendMessage?chat_id=-1001213369643&text=";

    private RestTemplate restTemplate;

    public void sendMessage(String message) {
        restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(TG_SEND_URL+message, String.class);
    }

}
