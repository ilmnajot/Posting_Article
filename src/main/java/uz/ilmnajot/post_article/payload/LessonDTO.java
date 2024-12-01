package uz.ilmnajot.post_article.payload;

import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import uz.ilmnajot.post_article.entity.Course;

@Setter
@Getter
@Builder
public class LessonDTO {

    private String name;
    private String description;
//    private String videoURL; // e.g., link to hosted video content
    private Integer duration; // Duration in minutes

}
