package com.esprit.decor.mapper;

import com.esprit.decor.dto.ArticleDTO;
import com.esprit.decor.entity.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ArticleMapper {

    @Mapping(target = "image", expression = "java(articleDTO.getImage() != null && !articleDTO.getImage().isEmpty() ? " +
            "java.util.Base64.getDecoder().decode(articleDTO.getImage()) : null)")
    Article toArticle(ArticleDTO articleDTO);


    @Mapping(target = "image", expression = "java(article.getImage() != null ? " +
            "java.util.Base64.getEncoder().encodeToString(article.getImage()) : null)")
    ArticleDTO toDTO(Article article);
    
    
}
