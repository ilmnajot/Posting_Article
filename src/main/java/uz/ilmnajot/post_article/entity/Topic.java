package uz.ilmnajot.post_article.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.ilmnajot.post_article.component.AbstractEntity;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Topic extends AbstractEntity {

    @Column(unique = true)
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Article> articles = new ArrayList<>();
}
