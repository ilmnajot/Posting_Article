package uz.ilmnajot.post_article.payload;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class LessonResponseDTO {

    private Long id;
    private String name;
    private String description;
    private String videoURL; // e.g., link to hosted video content
    private Integer duration; // Duration in minutes

}
