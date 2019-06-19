package ru.sereda.filerepository.seredafilerepository.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.sereda.filerepository.seredafilerepository.services.FileService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
@RequestMapping("/repository")
public class FileRepositoryController {

    @Autowired
    private FileService fileService;

    @RequestMapping(path = "/upload", method = RequestMethod.GET)
    public String getUploadForm() {
        return fileService.getUploadForm();
    }

    @RequestMapping(path = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("uploadedFile") MultipartFile uploadedFile) throws IOException {
        if (!uploadedFile.isEmpty()) {
            fileService.uploadFile(uploadedFile);
            return uploadedFile.getOriginalFilename() + " uploaded.";
        }
        return "file is empty";
    }

    @RequestMapping(path = "/filesList", method = RequestMethod.GET)
    public String getFilesList() throws IOException {
        return fileService.getDownloadForm();
    }

    @RequestMapping(path = "/download/{name}", method = RequestMethod.GET)
    public ResponseEntity<Resource> download(@PathVariable String name) throws FileNotFoundException {

        File file = fileService.getFile(name);
        if (file.exists()) {
            MediaType mediaType = fileService.getMediaType(file);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(mediaType)
                    .body(resource);
        } else {
            return ResponseEntity.notFound().header("Reason", "File not found").build();
        }
    }
}
