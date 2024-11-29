package uz.ilmnajot.post_article.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleResponseDTO {

    private Long id;
    private String title;
    private String content;
//    private Long authorId;
    private Long topicId;
}
