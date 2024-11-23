package uz.ilmnajot.post_article.service;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.entity.Article;
import uz.ilmnajot.post_article.entity.Rating;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.mapper.RatingMapper;
import uz.ilmnajot.post_article.payload.RatingDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.ArticleRepository;
import uz.ilmnajot.post_article.repository.RatingRepository;
import uz.ilmnajot.post_article.repository.UserRepository;
import uz.ilmnajot.post_article.service.interfaces.RatingService;

@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;
    private final RatingMapper ratingMapper;

    public RatingServiceImpl(
            RatingRepository ratingRepository,
            UserRepository userRepository,
            ArticleRepository articleRepository,
            RatingMapper ratingMapper) {
        this.ratingRepository = ratingRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
        this.ratingMapper = ratingMapper;
    }

    @Override
    public ApiResponse addRating(Long userId, Long articleId, boolean isLike) {
        User user = userRepository.findByIdAndDeleteFalse(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));
        Article article = articleRepository.findByIdAndDeleteFalse(articleId).orElseThrow(
                () -> new ResourceNotFoundException("Article not found"));
        Rating rating = new Rating();
        rating.setArticle(article);
        rating.setUser(user);
        rating.setLike(true);
        Rating saved = ratingRepository.save(rating);
        RatingDTO ratingDTO = ratingMapper.toRatingDTO(saved);
        return new ApiResponse(true, "success", HttpStatus.CREATED, ratingDTO);
    }
}
