package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {

    private FileService fileService;
    private UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping("/{fileid}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable Integer fileid) {
        try {
            File file = fileService.getFile(fileid);
            Resource resource = fileService.loadFileAsResource(file);
            return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                    "attachment; filename=\"" + file.getFilename() + "\"").body(resource);
        } catch(Exception e) {
            return null;
        }
    }

    @PostMapping()
    public String handleFileUpload(Authentication authentication, @RequestParam("fileUpload") MultipartFile fileUpload, Model model) {
        String resultError = null;
        String username = authentication.getName();
        User user = userService.getUser(username);
        if (user == null) {
            resultError = "User doesn't exist";
        }

        if (resultError == null) {
            try {
                byte[] bytes = fileUpload.getBytes();
                if (bytes.length != 0) {
                    String filename = fileUpload.getOriginalFilename();
                    String contentType =  fileUpload.getContentType();
                    String filesize = String.valueOf(fileUpload.getSize());
                    int result = fileService.saveFile(new File(null, filename, contentType, filesize, user.getUserid(), bytes));
                    if (result < 0) {
                        resultError = "Failed to save file";
                    }
                } else {
                    resultError = "No file uploaded";
                }
            } catch (Exception e) {
                resultError = e.getLocalizedMessage();
            }
        }

        if (resultError == null) {
            model.addAttribute("resultSuccess", true);
        } else {
            model.addAttribute("resultError", resultError);
        }
        return "result";
    }

    @GetMapping("/delete/{fileid}")
    public String deleteNote(@PathVariable Integer fileid, Model model) {
        String resultError = null;
        int result = fileService.deleteFile(fileid);

        if (result < 0) {
            resultError = "Failed to delete note";
        }

        if (resultError == null) {
            model.addAttribute("resultSuccess", true);
        } else {
            model.addAttribute("resultError", resultError);
        }

        return "result";
    }
}
