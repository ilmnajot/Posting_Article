package uz.ilmnajot.post_article.payload;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
public class RatingDTO {

    private Long id;
    private Long articleId;
    private Long userId;
    private boolean isLike;
}
