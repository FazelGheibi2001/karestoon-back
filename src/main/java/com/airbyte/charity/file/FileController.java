package com.airbyte.charity.file;

import com.airbyte.charity.dto.ResponseFile;
import com.airbyte.charity.dto.ResponseMessage;
import com.airbyte.charity.model.FileDB;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.airbyte.charity.permission.ManagePermission.*;

@RestController
@RequestMapping("/api/v1/file")
@CrossOrigin("*")
public class FileController {
    private final FileStorageService storageService;

    public FileController(FileStorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping
    @PreAuthorize(FILE_WRITE)
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        Map<String, String> message = new HashMap<>();
        try {
            FileDB fileDB = storageService.store(file);
            message.put("Message", "Uploaded the file successfully: " + file.getOriginalFilename());
            message.put("id", fileDB.getId());
            message.put("name", fileDB.getName());
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
        } catch (Exception e) {
            message.put("Message:", "Could not upload the file: " + file.getOriginalFilename() + "!");
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
        }
    }

    @GetMapping
    public ResponseEntity<List<ResponseFile>> getListFiles() {
        List<ResponseFile> files = storageService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = "/api/v1/file/" + dbFile.getId();

            return new ResponseFile(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id, HttpServletResponse response) {
        FileDB fileDB = storageService.getFile(id);
        response.addHeader("fileName", fileDB.getName());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(FILE_WRITE)
    public ResponseEntity<Void> deleteFile(@PathVariable(name = "id") String id) {
        storageService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
