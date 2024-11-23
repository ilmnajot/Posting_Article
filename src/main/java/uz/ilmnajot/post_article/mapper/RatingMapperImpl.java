package uz.ilmnajot.post_article.mapper;

import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Article;
import uz.ilmnajot.post_article.entity.Rating;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.payload.RatingDTO;
import uz.ilmnajot.post_article.repository.ArticleRepository;
import uz.ilmnajot.post_article.repository.UserRepository;
import uz.ilmnajot.post_article.service.interfaces.ArticleService;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class RatingMapperImpl implements RatingMapper {

    private final ArticleRepository articleRepository;
    private final ArticleService articleService;
    private final UserRepository userRepository;

    public RatingMapperImpl(ArticleRepository articleRepository, ArticleService articleService, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.articleService = articleService;
        this.userRepository = userRepository;
    }

    public Rating toRatingEntity(RatingDTO ratingDTO) {
        Article article = articleRepository.findByIdAndDeleteFalse(ratingDTO.getArticleId()).orElseThrow(() -> new ResourceNotFoundException("Article not found"));
        User user = userRepository.findByIdAndDeleteFalse(ratingDTO.getUserId()).orElseThrow(()
                -> new ResourceNotFoundException("User not found"));
        Rating rating = new Rating();
        rating.setArticle(article);
        rating.setUser(user);
        rating.setLike(ratingDTO.isLike());
        return rating;
    }

    public RatingDTO toRatingDTO(Rating rating) {
        RatingDTO ratingDTO = new RatingDTO();
        ratingDTO.setId(ratingDTO.getId());
        ratingDTO.setArticleId(rating.getArticle().getId());
        ratingDTO.setUserId(rating.getUser().getId());
        ratingDTO.setLike(rating.isLike());
        return ratingDTO;

    }
}
