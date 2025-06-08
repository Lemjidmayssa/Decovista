package com.esprit.decor.service;

import com.esprit.decor.dto.ArticleDTO;
import com.esprit.decor.entity.Article;
import com.esprit.decor.mapper.ArticleMapper;
import com.esprit.decor.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public List<ArticleDTO> getAll() {
        return articleRepository.findAll()
                .stream()
                .map(articleMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ArticleDTO getById(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        return articleMapper.toDTO(article);
    }

    public Article create(ArticleDTO article) {
        Article articleEntity = articleMapper.toArticle(article);
        return articleRepository.save(articleEntity);
    }

    public Article update(Long id, ArticleDTO updatedArticle) {
        Article existing = articleRepository.findById(id).orElse(null);
        if (existing == null) return null;
        Article article = articleMapper.toArticle(updatedArticle);
        article.setId(id);
        return articleRepository.save(article);
    }

    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}

