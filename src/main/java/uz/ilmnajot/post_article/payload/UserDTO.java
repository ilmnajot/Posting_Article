package uz.ilmnajot.post_article.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDTO {

    private String fName;
    private String lName;
    private String email;
    private String password;
}
