package uz.ilmnajot.post_article.service;

import uz.ilmnajot.post_article.payload.ArticleDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

public interface ArticleService {
    ApiResponse addArticle(ArticleDTO articleDTO);
}
