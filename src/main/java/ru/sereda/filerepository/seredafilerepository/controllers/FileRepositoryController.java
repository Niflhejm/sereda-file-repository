package ru.sereda.filerepository.seredafilerepository.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sereda.filerepository.seredafilerepository.services.FileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

//import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
//import ru.sereda.filerepository.seredafilerepository.services.TelegramService;

@RestController
@RequestMapping("/repository")
public class FileRepositoryController {

    @Autowired
    private FileService fileService;
//    @Autowired
//    private TelegramService telegramService;

    @RequestMapping(path = "/upload", method = RequestMethod.GET)
    public String getUploadForm() {
        return fileService.getUploadForm();
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("uploadedFile") MultipartFile uploadedFile,
                         @RequestParam(value = "sendTelegram", required = false) boolean sendTelegram) throws IOException {
        if (!uploadedFile.isEmpty()) {
            fileService.uploadFile(uploadedFile);
            if (sendTelegram) {
//            try {
//                telegramService.sendDocUploadingAFile(-1001213369643L, fileService.getFile(uploadedFile.getOriginalFilename()));
//            } catch (TelegramApiException e) {
//                return uploadedFile.getOriginalFilename() + " uploaded to repository but failed to send to telegram";
//            }
                return uploadedFile.getOriginalFilename() + " uploaded to repository and sent to telegram";
            }
            return uploadedFile.getOriginalFilename() + " uploaded to repository";
        }
        return "file is empty";
    }

    @RequestMapping(path = "/download", method = RequestMethod.GET)
    public String getFilesList() throws IOException {
        return fileService.getDownloadForm();
    }

    @RequestMapping(path = "/download", method = RequestMethod.POST)
    public ResponseEntity<Resource> download(@RequestParam String name) throws IOException {
        File file = fileService.getFile(name);
        if (file.exists()) {
            MediaType mediaType = fileService.getMediaType(file);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header("attachment", "filename= +name")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + name)
                    .header("Cache-Control", "no-cache, no-store, must-revalidate")
                    .header("Pragma", "no-cache")
                    .header("Expires", "0")
                    .contentType(mediaType)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().header("Reason", "File not found").build();
        }
    }
}