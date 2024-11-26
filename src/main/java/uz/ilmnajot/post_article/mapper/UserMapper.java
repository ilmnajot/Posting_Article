package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.payload.UserDTO;
import uz.ilmnajot.post_article.payload.UserResponseDTO;

public interface UserMapper {

   User toUserEntity(UserDTO userDTO);
    public UserResponseDTO toUserResponseDTO(User user);

}
