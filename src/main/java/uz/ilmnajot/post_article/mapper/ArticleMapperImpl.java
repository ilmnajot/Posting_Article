package uz.ilmnajot.post_article.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Article;
import uz.ilmnajot.post_article.entity.Category;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.payload.ArticleDTO;
import uz.ilmnajot.post_article.repository.ArticleRepository;
import uz.ilmnajot.post_article.repository.CategoryRepository;
import uz.ilmnajot.post_article.repository.UserRepository;
import uz.ilmnajot.post_article.utils.MessageKey;

@Component
public class ArticleMapperImpl implements ArticleMapper {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final ArticleRepository articleRepository;

    public ArticleMapperImpl(UserRepository userRepository, CategoryRepository categoryRepository, ArticleRepository articleRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.articleRepository = articleRepository;
    }

    public Article toArticleEntity(ArticleDTO articleDTO) {
        User user = userRepository.findByIdAndDeleteFalse(articleDTO.getAuthorId()).orElseThrow(
                () -> new ResourceNotFoundException(MessageKey.USER_NOT_FOUND));
        Category category = categoryRepository.findByIdAndDeleteFalse(articleDTO.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException(MessageKey.CATEGORY_NOT_FOUND));
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
        article.setAuthor(user);
        article.setCategory(category);
        return article;
    }

    public ArticleDTO toArticleDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setTitle(article.getTitle());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setAuthorId(article.getAuthor().getId());
        articleDTO.setCategoryId(article.getCategory().getId());
        return articleDTO;
    }

    public Article toUpdateArticleEntity(Article article, ArticleDTO articleDTO) {

        User user = this.userRepository.findByIdAndDeleteFalse(articleDTO.getAuthorId()).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));
        Category category = this.categoryRepository.findByIdAndDeleteFalse(articleDTO.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category not found"));

        if (articleDTO.getTitle() != null) {
            article.setTitle(articleDTO.getTitle());
        }
        if (articleDTO.getContent() != null) {
            article.setContent(articleDTO.getContent());
        }
        if (articleDTO.getAuthorId() != null) {
            article.setAuthor(user);
        }
        if (articleDTO.getCategoryId() != null) {
            article.setCategory(category);
        }
        return article;

    }
}
