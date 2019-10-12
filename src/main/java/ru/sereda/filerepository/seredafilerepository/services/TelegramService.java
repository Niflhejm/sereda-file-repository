//package ru.sereda.filerepository.seredafilerepository.services;
//
//import org.springframework.stereotype.Component;
//import org.telegram.telegrambots.bots.TelegramLongPollingBot;
//import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
//import org.telegram.telegrambots.meta.api.objects.Update;
//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//
//import java.io.File;
//
//@Component
//public class TelegramService extends TelegramLongPollingBot {
//
//    public void sendDocUploadingAFile(Long chatId, File file) throws TelegramApiException {
//
//        SendDocument sendDocumentRequest = new SendDocument();
//        sendDocumentRequest.setChatId(chatId);
//        sendDocumentRequest.setDocument(file);
//        execute(sendDocumentRequest);
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//
//    }
//
//    @Override
//    public String getBotUsername() {
//        return "Sereda_test_bot";
//    }
//
//    @Override
//    public String getBotToken() {
//        return "820420099:AAFdwfHzo7gf2J93Rg1ugX9fcewevZLVVks";
//    }
//}
