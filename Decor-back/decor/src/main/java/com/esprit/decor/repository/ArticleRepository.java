package com.esprit.decor.repository;

import com.esprit.decor.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    long countByNouveauTrue();

    @Query("SELECT SUM(a.prix) FROM Article a")
    Double sumPrix();

    @Query("SELECT a FROM Article a WHERE :surface <= (a.largeur * a.longueur)")
    List<Article> findBySurfaceMin(@Param("surface") double surface);

    @Query("SELECT a FROM Article a WHERE (a.largeur * a.longueur) <= :surface")
    List<Article> findBySurfaceMax(@Param("surface") double surface);

    @Query("SELECT a FROM Article a WHERE (a.largeur * a.longueur) BETWEEN :min AND :max")
    List<Article> findBySurfaceBetween(@Param("min") double min, @Param("max") double max);

    @Query("SELECT a FROM Article a WHERE a.type = :type AND (a.longueur * a.largeur) <= :maxSurface")
    List<Article> findByTypeAndSurfaceMax(@Param("type") String type, @Param("maxSurface") double maxSurface);

}
