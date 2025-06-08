package com.esprit.decor.dto;

import lombok.Data;

import java.util.List;

@Data
public class CommandeRequest {
    private String email;
    private List<ArticleQuantite> articles;

    @Data
    public static class ArticleQuantite {
        private Long articleId;
        private int quantite;
    }
}

