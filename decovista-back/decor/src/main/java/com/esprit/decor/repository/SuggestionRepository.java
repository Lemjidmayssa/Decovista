package com.esprit.decor.repository;

import com.esprit.decor.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SuggestionRepository extends JpaRepository<Suggestion, Long> {
    List<Suggestion> findByRoomImageId(Long imageId);
}
