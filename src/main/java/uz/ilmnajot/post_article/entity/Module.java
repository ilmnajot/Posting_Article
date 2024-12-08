package uz.ilmnajot.post_article.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.ilmnajot.post_article.component.AbstractEntity;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Module extends AbstractEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @OneToMany(mappedBy = "module", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Lesson> lessons;
}
