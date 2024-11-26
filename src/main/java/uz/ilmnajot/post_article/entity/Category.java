package uz.ilmnajot.post_article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Size;
import lombok.*;
import uz.ilmnajot.post_article.component.AbstractEntity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Category extends AbstractEntity {

    @Column(nullable = false, unique = true)
    @Size(min = 2, max=150)
    private String name;

    private String description;

    private String imageURL;// image url to fetch an image.

}
