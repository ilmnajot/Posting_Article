package uz.ilmnajot.post_article.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.mapper.UserMapper;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.payload.UserResponseDTO;

@Component
public class UserSession {

    private final UserMapper userMapper;

    public UserSession(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public UserResponseDTO getUserDTO() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return userMapper.toUserResponseDTO((User) authentication.getPrincipal());
        }
        return null; // Return null if no user is authenticated
    }
}
