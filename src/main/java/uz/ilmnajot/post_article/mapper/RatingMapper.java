package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.Rating;
import uz.ilmnajot.post_article.payload.RatingDTO;

public interface RatingMapper {

    RatingDTO toRatingDTO(Rating rating);

    Rating toRatingEntity(RatingDTO ratingDTO);
}
