package uz.ilmnajot.post_article.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import uz.ilmnajot.post_article.component.AbstractEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "course")
@Builder
public class Course extends AbstractEntity {

    @NotBlank
    @Size(max = 100) // Adjust max length as needed
    @Column(nullable = false, unique = true)
    private String title;

    @NotBlank
    @Size(max = 100) // Adjust max length as needed
    @Column(nullable = false)
    private String mentorName;

    @Size(max = 500) // Optional for descriptions
    private String description;

    @PositiveOrZero // Ensures price is non-negative
    private Double price;

    private Integer lessonCount;

    private boolean free;

    @ManyToOne
    private User manager; // The admin who created the course

    @ManyToMany(mappedBy = "enrolledCourses")
    private Set<User> enrolledUsers;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true) //it won't be created...
    private List<Module> modules;

//    @ManyToOne
//    private Category category;

}
