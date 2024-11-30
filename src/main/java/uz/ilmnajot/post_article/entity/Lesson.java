package uz.ilmnajot.post_article.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.ilmnajot.post_article.component.AbstractEntity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Lesson extends AbstractEntity {


    @ManyToOne
    private Course course;

}
