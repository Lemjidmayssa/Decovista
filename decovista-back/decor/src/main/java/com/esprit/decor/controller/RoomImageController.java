package com.esprit.decor.controller;

import com.esprit.decor.entity.RoomImage;
import com.esprit.decor.service.RoomImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/chambre")
@RequiredArgsConstructor
public class RoomImageController {

    private final RoomImageService roomImageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(
            @RequestParam("image") MultipartFile file,
            @RequestParam("username") String username,
            @RequestParam("roomType") String roomType
    ) {
        try {
            RoomImage image = roomImageService.uploadImage(file, username, roomType);
            return ResponseEntity.ok(image);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}

