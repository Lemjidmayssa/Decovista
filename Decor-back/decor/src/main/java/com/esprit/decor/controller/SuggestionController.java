package com.esprit.decor.controller;

import com.esprit.decor.entity.Suggestion;
import com.esprit.decor.repository.SuggestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/suggestions")
@RequiredArgsConstructor
public class SuggestionController {

    private final SuggestionRepository suggestionRepository;

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<Suggestion>> getSuggestions(@PathVariable Long roomId) {
        List<Suggestion> suggestions = suggestionRepository.findByRoomImageId(roomId);
        return ResponseEntity.ok(suggestions);
    }
}

