package com.esprit.decor.repository;

import com.esprit.decor.entity.Commande;
import com.esprit.decor.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommandeRepository extends JpaRepository<Commande, Long> {

    List<Commande> findByUtilisateur(User utilisateur);

}
