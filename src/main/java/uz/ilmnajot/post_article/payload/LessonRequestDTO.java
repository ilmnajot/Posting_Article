package uz.ilmnajot.post_article.payload;

import lombok.*;

@Setter
@Getter
public class LessonRequestDTO {

    private String name;
    private String description;
    private Integer duration; // Duration in minutes
    private String videoURL; // e.g., link to hosted video content
}
