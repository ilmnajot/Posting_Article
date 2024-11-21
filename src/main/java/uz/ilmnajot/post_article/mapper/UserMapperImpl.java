package uz.ilmnajot.post_article.mapper;

import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.payload.AuthDTO;

@Component
public class UserMapperImpl implements UserMapper {

    public User toUserEntity(AuthDTO authDTO) {
        User user = new User();
        user.setFName(authDTO.getFName());
        user.setLName(authDTO.getLName());
        user.setEmail(authDTO.getEmail());
        user.setPassword(authDTO.getPassword());
        return user;
    }

    public AuthDTO toAuthDTO(User user) {
        AuthDTO authDTO = new AuthDTO();
        authDTO.setId(user.getId());
        authDTO.setFName(user.getFName());
        authDTO.setLName(user.getLName());
        authDTO.setEmail(user.getEmail());
        authDTO.setPassword(user.getPassword());
        return authDTO;
    }
}
