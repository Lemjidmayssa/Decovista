package com.esprit.decor.service;

import com.esprit.decor.dto.CommandeRequest;
import com.esprit.decor.entity.Article;
import com.esprit.decor.entity.Commande;
import com.esprit.decor.entity.LigneCommande;
import com.esprit.decor.entity.User;
import com.esprit.decor.repository.ArticleRepository;
import com.esprit.decor.repository.CommandeRepository;
import com.esprit.decor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommandeService {

    public final UserRepository userRepository;
    public final ArticleRepository articleRepository;
    private final CommandeRepository commandeRepository;

    public Commande creerCommande(CommandeRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        Commande commande = new Commande();
        commande.setUtilisateur(user);
        commande.setDate(LocalDateTime.now());

        List<LigneCommande> lignes = request.getArticles().stream().map(aq -> {
            Article article = articleRepository.findById(aq.getArticleId())
                    .orElseThrow(() -> new RuntimeException("Article introuvable"));

            LigneCommande ligne = new LigneCommande();
            ligne.setArticle(article);
            ligne.setQuantite(aq.getQuantite());
            ligne.setCommande(commande);
            return ligne;
        }).collect(Collectors.toList());

        commande.setLignes(lignes);

        return commandeRepository.save(commande);
    }


    public List<Commande> getCommandesByUserEmail(String email) {
        User user = userRepository.findByEmail(email).orElse(null);
        return user != null ? commandeRepository.findByUtilisateur(user) : List.of();
    }


}
