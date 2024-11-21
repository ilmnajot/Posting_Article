package uz.ilmnajot.post_article.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.ilmnajot.post_article.component.AbstractEntity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Comment extends AbstractEntity {

    private String comment;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
