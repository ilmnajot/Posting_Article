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
@Table(
        name = "ratings",
        uniqueConstraints = @UniqueConstraint(columnNames = {"article_id", "user_id"}))
public class Rating extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private boolean isLike; // true for like, false for dislike

}
