package uz.ilmnajot.post_article.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserProfileResponseDTO {

    private Long id;
    private String fName;
    private String lName;
    private String email;
    private String profileImageURL;
    private String bio;
    private String phoneNumber;
    private List<Long> enrolledCoursesIds;

}
