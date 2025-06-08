package com.esprit.decor.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ArticleDTO {

    private Long id;

    private String titre;
    private String description;
    private Double prix;
    private Double ancienPrix;
    private Double longueur;
    private Double largeur;
    private String image;
    private Boolean nouveau;
    private String type;
}
