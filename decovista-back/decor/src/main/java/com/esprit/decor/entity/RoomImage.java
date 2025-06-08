package com.esprit.decor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "room_images")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RoomImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private byte[] image;
    private LocalDateTime uploadDate;
    private Double surface; // en m²

    @ManyToOne
    private User utilisateur;
}

