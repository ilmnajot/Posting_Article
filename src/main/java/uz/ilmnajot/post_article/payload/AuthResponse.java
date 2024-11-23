package uz.ilmnajot.post_article.payload;

import lombok.Getter;
import lombok.Setter;
import uz.ilmnajot.post_article.entity.User;

@Getter
@Setter
public class AuthResponse {

    private String token;
    private User user;
}
