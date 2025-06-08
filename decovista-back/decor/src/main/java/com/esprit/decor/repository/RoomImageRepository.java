package com.esprit.decor.repository;

import com.esprit.decor.entity.RoomImage;
import com.esprit.decor.entity.Suggestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RoomImageRepository extends JpaRepository<RoomImage, Long> {
    List<RoomImage> findByUtilisateurUsername(String username);

}

