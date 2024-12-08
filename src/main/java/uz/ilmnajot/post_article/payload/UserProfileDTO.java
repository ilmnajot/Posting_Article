package uz.ilmnajot.post_article.payload;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.entity.Role;

import java.util.List;
import java.util.Set;

@Setter
@Getter
public class UserProfileDTO {

//    private Long id;
    private String fName;
    private String lName;
    private String email;
//    private String profileImageURL;
    private String bio;
    private String phoneNumber;
//    private List<Long> coursesId;

}
