package uz.ilmnajot.post_article.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.entity.Role;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.payload.MentorDTO;
import uz.ilmnajot.post_article.payload.UserDTO;
import uz.ilmnajot.post_article.payload.UserProfileResponseDTO;
import uz.ilmnajot.post_article.payload.UserResponseDTO;
import uz.ilmnajot.post_article.repository.CourseRepository;
import uz.ilmnajot.post_article.repository.RoleRepository;

import java.util.Set;

@Component
public class UserMapperImpl implements UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final CourseRepository courseRepository;

    public UserMapperImpl(PasswordEncoder passwordEncoder, RoleRepository roleRepository, CourseRepository courseRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.courseRepository = courseRepository;
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

    @Override
    public MentorDTO toMentorDTO(User user) {
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setId(user.getId());
        mentorDTO.setFName(user.getFName());
        return mentorDTO;
    }
}
