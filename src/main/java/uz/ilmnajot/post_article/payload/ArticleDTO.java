package uz.ilmnajot.post_article.payload;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import uz.ilmnajot.post_article.entity.Category;
import uz.ilmnajot.post_article.entity.User;

@Setter
@Getter
public class ArticleDTO {

        @NotEmpty(message = "Title cannot be empty")
        private String title;

        @NotEmpty(message = "Content cannot be empty")
        private String content;

////        @NotNull(message = "Author ID cannot be null")
//        private Long authorId;

//        @NotNull(message = "Category ID cannot be null")
        private Long topicId;


}
