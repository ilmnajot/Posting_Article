package uz.ilmnajot.post_article.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class TopicResponseDTO {

    private UUID id;
    private String title;
    private String description;
    private UUID categoryId;
}
