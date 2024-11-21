package uz.ilmnajot.post_article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import lombok.*;
import uz.ilmnajot.post_article.component.AbstractEntity;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Builder
public class User extends AbstractEntity {

    private String fName;
    private String lName;

    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;

    private String password;
    @ManyToOne
    private Role role;

}
