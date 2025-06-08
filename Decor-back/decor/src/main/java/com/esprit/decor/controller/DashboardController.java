package com.esprit.decor.controller;


import com.esprit.decor.repository.ArticleRepository;
import com.esprit.decor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    @GetMapping
    public Map<String, Object> getDashboardStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalArticles", articleRepository.count());
        stats.put("totalUsers", userRepository.count());
        stats.put("nouveauxArticles", articleRepository.countByNouveauTrue());
        stats.put("prixTotal", articleRepository.sumPrix());
        return stats;
    }
}
