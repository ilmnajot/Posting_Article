package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.Article;
import uz.ilmnajot.post_article.payload.ArticleDTO;

public interface ArticleMapper {

    Article toArticleEntity(ArticleDTO articleDTO);

    ArticleDTO toArticleDTO(Article article);
    Article toUpdateArticleEntity(Article article, ArticleDTO articleDTO);
}
