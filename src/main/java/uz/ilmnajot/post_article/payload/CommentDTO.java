package uz.ilmnajot.post_article.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDTO {

    private Long id;
    private String comment;
    private Long articleId;
    private Long userId;
}
