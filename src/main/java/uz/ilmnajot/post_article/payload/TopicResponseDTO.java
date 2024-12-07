package uz.ilmnajot.post_article.payload;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TopicResponseDTO {

    private Long id;
    private String title;
    private String description;
    private Long categoryId;
}
