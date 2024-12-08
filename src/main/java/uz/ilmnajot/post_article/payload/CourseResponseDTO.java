package uz.ilmnajot.post_article.payload;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CourseResponseDTO {

    private Long id;
    private String title;
    private String description;
    private String mentorName;
    private Double price;
    private Integer lessonCount;
    private boolean free;
//    private String image;
}
