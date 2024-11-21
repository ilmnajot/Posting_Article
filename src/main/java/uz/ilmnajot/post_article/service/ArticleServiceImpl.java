package uz.ilmnajot.post_article.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.entity.Article;
import uz.ilmnajot.post_article.exception.AlreadyExistsException;
import uz.ilmnajot.post_article.mapper.ArticleMapper;
import uz.ilmnajot.post_article.payload.ArticleDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.ArticleRepository;

import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;

    public ArticleServiceImpl(ArticleRepository articleRepository, ArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    @Override
    public ApiResponse addArticle(ArticleDTO articleDTO) {
        Optional<Article> optionalArticle = articleRepository.findByTitle(articleDTO.getTitle());
        if (optionalArticle.isPresent()) {
            throw new AlreadyExistsException("Article already exists", HttpStatus.BAD_REQUEST);
        }
        Article articleEntity = articleMapper.toArticleEntity(articleDTO);
        Article saved = articleRepository.save(articleEntity);
        ArticleDTO mapperArticleDTO = articleMapper.toArticleDTO(saved);
        return new ApiResponse(true, "success", HttpStatus.CREATED, mapperArticleDTO);
    }
}
