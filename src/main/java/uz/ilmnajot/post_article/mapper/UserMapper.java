package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.payload.*;

public interface UserMapper {

    User toUserEntity(UserDTO userDTO);

    UserResponseDTO toUserResponseDTO(User user);

    MentorDTO toMentorDTO(User user);

    UserProfileResponseDTO toUserProfileEntity(User user);
    User toUserProfileUpdateEntity(UserProfileDTO userProfileDTO,User user);



}
