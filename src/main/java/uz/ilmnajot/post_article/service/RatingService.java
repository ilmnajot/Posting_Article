package uz.ilmnajot.post_article.service;

import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.payload.common.ApiResponse;


public interface RatingService {
    ApiResponse addRating(Long userId, Long articleId, boolean isLike);
}
