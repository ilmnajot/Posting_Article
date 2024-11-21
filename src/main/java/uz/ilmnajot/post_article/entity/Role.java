package uz.ilmnajot.post_article.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import uz.ilmnajot.post_article.component.AbstractEntity;
import uz.ilmnajot.post_article.enums.RoleName;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Role extends AbstractEntity {

    private String name;

    @Enumerated(EnumType.STRING)
    private RoleName role;
}
