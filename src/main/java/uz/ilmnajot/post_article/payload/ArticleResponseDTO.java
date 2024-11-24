package uz.ilmnajot.post_article.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ArticleResponseDTO {

    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private Long categoryId;
}
