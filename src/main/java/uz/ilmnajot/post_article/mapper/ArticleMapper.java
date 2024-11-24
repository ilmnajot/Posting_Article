package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.Article;
import uz.ilmnajot.post_article.payload.ArticleDTO;
import uz.ilmnajot.post_article.payload.ArticleResponseDTO;

public interface ArticleMapper {

    Article toArticleEntity(ArticleDTO articleDTO);

    ArticleResponseDTO toArticleDTO(Article article);
    Article toUpdateArticleEntity(Article article, ArticleDTO articleDTO);
}
