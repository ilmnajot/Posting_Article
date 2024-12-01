package uz.ilmnajot.post_article.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import uz.ilmnajot.post_article.component.AbstractEntity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "lesson")
@Builder
public class Lesson extends AbstractEntity {

    @NotBlank
    @Size(max = 100)
    private String name;

    @Size(max = 500) // Optional length for descriptions
    private String description;

    private Integer orderIndex;
    private String videoURL; // e.g., link to hosted video content
    private Integer duration; // Duration in minutes

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Module module;

}
