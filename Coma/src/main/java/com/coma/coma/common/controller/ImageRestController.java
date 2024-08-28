package com.coma.coma.common.controller;

import com.coma.coma.common.service.StorageService;
import com.coma.coma.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/images")
public class ImageRestController {

    private final StorageService storageService;

    public ImageRestController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file,
                                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String imageUrl;
        try {
            imageUrl = storageService.uploadFile(file, customUserDetails.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Image upload failed");
        }

        return ResponseEntity.ok(imageUrl);
    }

    @DeleteMapping
    public ResponseEntity deleteImage(String imageUrl, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        if (customUserDetails == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }
        storageService.deleteFile(imageUrl);
        return ResponseEntity.ok().build();
    }
}
