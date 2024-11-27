package uz.ilmnajot.post_article.service.interfaces;

import uz.ilmnajot.post_article.payload.ArticleDTO;
import uz.ilmnajot.post_article.payload.ArticleResponseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

import java.util.List;

public interface ArticleService {
    ApiResponse addArticle(ArticleDTO articleDTO);

    ApiResponse getArticle(Long articleId);
    ArticleResponseDTO getArticleById(Long articleId);

    ApiResponse getAllArticles();

    ApiResponse getActiveArticle();

    ApiResponse getAllDeletedArticles();

    ApiResponse deleteArticle(Long articleId);

    ApiResponse updateArticle(Long articleId, ArticleDTO articleDTO);

    List<ArticleResponseDTO> getArticlesByTopicId(Long topicId);

    ArticleDTO addArticleToTopic(Long topicId, ArticleDTO articleRequestDTO);

    ArticleResponseDTO getArticleByTopicId(Long topicId);
}
