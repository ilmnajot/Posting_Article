package uz.ilmnajot.post_article.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Email;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.ilmnajot.post_article.component.AbstractEntity;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "users")
@Builder
public class User extends AbstractEntity implements UserDetails {

    private String fName;
    private String lName;

    @Email(message = "Email should be valid")
    @Column(unique = true)
    private String email;
    private String emailCode;// 6-digit email code to verify email

    private String password;

    @ManyToOne
    private Role role;

    private boolean isEnable;

    public User(String fName, String lName, String email, String password, Role role, boolean isEnable) {
        this.fName = fName;
        this.lName = lName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.isEnable = isEnable;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.getRole().name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnable;
    }
}
