package com.esprit.decor.controller;

import com.esprit.decor.dto.CommandeRequest;
import com.esprit.decor.entity.Commande;
import com.esprit.decor.service.CommandeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
public class CommandeController {

    private final CommandeService commandeService;

    @PostMapping
    public ResponseEntity<?> passerCommande(@RequestBody CommandeRequest request) {
        try {
            Commande commande = commandeService.creerCommande(request);
            return ResponseEntity.ok(commande);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur : " + e.getMessage());
        }
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<Commande>> getCommandesByUser(@PathVariable String email) {
        List<Commande> commandes = commandeService.getCommandesByUserEmail(email);
        return ResponseEntity.ok(commandes);
    }

}

