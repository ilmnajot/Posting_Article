package uz.ilmnajot.post_article.service;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.entity.Article;
import uz.ilmnajot.post_article.exception.AlreadyExistsException;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.mapper.ArticleMapper;
import uz.ilmnajot.post_article.payload.ArticleDTO;
import uz.ilmnajot.post_article.payload.ArticleResponseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.ArticleRepository;
import uz.ilmnajot.post_article.service.interfaces.ArticleService;

import java.util.ArrayList;
import java.util.List;
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
        Optional<Article> optionalArticle = articleRepository.findByTitleAndDeleteFalse(articleDTO.getTitle());
        if (optionalArticle.isPresent()) {
            throw new AlreadyExistsException("Article already exists");
        }
        Article articleEntity = articleMapper.toArticleEntity(articleDTO);
        Article saved = articleRepository.save(articleEntity);
        ArticleResponseDTO mapperArticleDTO = articleMapper.toArticleDTO(saved);
        return new ApiResponse(true, "success", HttpStatus.CREATED, mapperArticleDTO);
    }

    @Override
    public ApiResponse getArticle(Long articleId) {
        Article article = articleRepository.findByIdAndDeleteFalse(articleId).orElseThrow(
                () -> new ResourceNotFoundException("Article not found"));
        return new ApiResponse(true, "success", HttpStatus.OK, article);
    }

    @Override
    public ApiResponse getAllArticles() {
        List<Article> articleList = articleRepository.findAll();
        List<ArticleResponseDTO> articleDTOList = new ArrayList<>();
        for (Article article : articleList) {
            articleDTOList.add(articleMapper.toArticleDTO(article));
        }
        return new ApiResponse(true, "success", HttpStatus.OK, articleDTOList);
    }

    @Override
    public ApiResponse getActiveArticle() {
        List<Article> articleList = articleRepository.findAllByDeleteIsFalse();
        List<ArticleResponseDTO> articleDTOList = new ArrayList<>();
        for (Article article : articleList) {
            articleDTOList.add(articleMapper.toArticleDTO(article));
        }
        return new ApiResponse(true, "success", HttpStatus.OK, articleDTOList);
    }

    @Override
    public ApiResponse getAllDeletedArticles() {
        List<Article> articleList = articleRepository.findAllByDeleteIsTrue();
        List<ArticleResponseDTO> articleDTOList = new ArrayList<>();
        for (Article article : articleList) {
            articleDTOList.add(articleMapper.toArticleDTO(article));
        }
        return new ApiResponse(true, "success", HttpStatus.OK, articleDTOList);
    }

    @Override
    public ApiResponse deleteArticle(Long articleId) {
        Article article = articleRepository.findByIdAndDeleteFalse(articleId).orElseThrow(
                () -> new ResourceNotFoundException("Article not found"));
        article.setDelete(true);
        articleRepository.save(article);
        return new ApiResponse(true, "success", HttpStatus.OK, "The Article has been deleted successfully");
    }

    @Override
    public ApiResponse updateArticle(Long articleId, ArticleDTO articleDTO) {
        Article article = articleRepository.findByIdAndDeleteFalse(articleId).orElseThrow(
                () -> new ResourceNotFoundException("Article not found"));
        Article articleEntity = articleMapper.toUpdateArticleEntity(article, articleDTO);
        Article saved = articleRepository.save(articleEntity);
        ArticleResponseDTO mapperArticleDTO = articleMapper.toArticleDTO(saved);
        return new ApiResponse(true, "success", HttpStatus.OK, mapperArticleDTO);
    }

    @Override
    public ApiResponse getArticlesByTopicId(Long topicId) {
        List<Article> allByTopicId = articleRepository.findAllByTopic_Id(topicId);
        return new ApiResponse(true, "success", HttpStatus.OK, allByTopicId);
    }

}
