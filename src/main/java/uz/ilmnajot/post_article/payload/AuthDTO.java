package uz.ilmnajot.post_article.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthDTO {

    private Long id;
    private String fName;
    private String lName;
    private String email;
    private String password;
}
