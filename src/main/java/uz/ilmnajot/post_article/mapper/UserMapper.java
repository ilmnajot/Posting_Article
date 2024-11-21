package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.payload.AuthDTO;

public interface UserMapper {

    public User toUserEntity(AuthDTO authDTO);
    public AuthDTO toAuthDTO(User user);

}
