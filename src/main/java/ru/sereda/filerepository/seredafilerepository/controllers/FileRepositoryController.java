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
    public ResponseEntity<Resource> download(@PathVariable String name) throws IOException {

        File file = fileService.getFile(name);
        if (file.exists()) {
            MediaType mediaType = fileService.getMediaType(file);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .header("attachment", "filename= +name")
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+name)
                    .header("Cache-Control", "no-cache, no-store, must-revalidate")
                    .header("Pragma", "no-cache")
                    .header("Expires", "0")
                    .contentType(mediaType)
//                    .contentLength(resource.contentLength())
                    .body(resource);
        } else {
            return ResponseEntity.notFound().header("Reason", "File not found").build();
        }
    }
}