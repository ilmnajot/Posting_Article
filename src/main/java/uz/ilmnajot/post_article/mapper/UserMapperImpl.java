package uz.ilmnajot.post_article.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Role;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.payload.UserDTO;
import uz.ilmnajot.post_article.payload.UserResponseDTO;
import uz.ilmnajot.post_article.repository.RoleRepository;

@Component
public class UserMapperImpl implements UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserMapperImpl(PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public User toUserEntity(UserDTO userDTO) {
        User user = new User();
        Role role = roleRepository.findByNameAndDeleteFalse("USER").orElseThrow(
                () -> new ResourceNotFoundException("Role not found"));
        user.setFName(userDTO.getFName());
        user.setLName(userDTO.getLName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setRole(role);
        return user;
    }

    public UserResponseDTO toUserResponseDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setId(user.getId());
        dto.setFName(user.getFName());
        dto.setLName(user.getLName());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
