package uz.ilmnajot.post_article.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Article;
import uz.ilmnajot.post_article.entity.Category;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.payload.ArticleDTO;
import uz.ilmnajot.post_article.repository.CategoryRepository;
import uz.ilmnajot.post_article.repository.UserRepository;
import uz.ilmnajot.post_article.utils.MessageKey;

@Component
public class ArticleMapperImpl implements ArticleMapper {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    public ArticleMapperImpl(UserRepository userRepository, CategoryRepository categoryRepository) {
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
    }

    public Article toArticleEntity(ArticleDTO articleDTO) {
        User user = userRepository.findByIdAndDeleteFalse(articleDTO.getAuthorId()).orElseThrow(
                () -> new ResourceNotFoundException(MessageKey.USER_NOT_FOUND, HttpStatus.NOT_FOUND));
        Category category = categoryRepository.findByIdAndDeleteFalse(articleDTO.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException(MessageKey.CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND));
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
}
