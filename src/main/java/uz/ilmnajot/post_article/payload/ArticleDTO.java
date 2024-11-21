package uz.ilmnajot.post_article.payload;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import uz.ilmnajot.post_article.entity.Category;
import uz.ilmnajot.post_article.entity.User;

@Setter
@Getter
public class ArticleDTO {

    private Long id;
    private String title;
    private String content;
    private Long authorId;
    private Long categoryId;
}
