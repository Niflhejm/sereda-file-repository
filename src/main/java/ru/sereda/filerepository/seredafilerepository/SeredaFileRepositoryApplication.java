package ru.sereda.filerepository.seredafilerepository;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class SeredaFileRepositoryApplication {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        SpringApplication.run(SeredaFileRepositoryApplication.class, args);
    }

}
