package uz.ilmnajot.post_article.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Role;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.payload.*;
import uz.ilmnajot.post_article.repository.RoleRepository;
import uz.ilmnajot.post_article.service.interfaces.CourseService;

import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private final CourseService courseService;

    public UserMapperImpl(PasswordEncoder passwordEncoder, RoleRepository roleRepository, CourseService courseService) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.courseService = courseService;
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
        dto.setBio(user.getBio());
        dto.setPhone(user.getPhoneNumber());
        return dto;
    }

    @Override
    public MentorDTO toMentorDTO(User user) {
        MentorDTO mentorDTO = new MentorDTO();
        mentorDTO.setId(user.getId());
        mentorDTO.setFName(user.getFName());
        return mentorDTO;
    }

    @Override
    public UserProfileResponseDTO toUserProfileEntity(User user) {
        UserProfileResponseDTO responseDTO = new UserProfileResponseDTO();
        responseDTO.setId(user.getId());
        responseDTO.setFName(user.getFName());
        responseDTO.setLName(user.getLName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setBio(user.getBio());
        responseDTO.setPhoneNumber(user.getPhoneNumber());
        List<CourseResponseDTO> coursesByUserId = courseService.getCoursesByUserId(user.getId());
        responseDTO.setEnrolledCoursesIds(coursesByUserId.stream().map(CourseResponseDTO::getId).toList());
        return responseDTO;
    }

    @Override
    public User toUserProfileUpdateEntity(UserProfileDTO userProfileDTO, User user) {

        if (userProfileDTO.getFName() != null) {
            user.setFName(userProfileDTO.getFName());
        }
        if (userProfileDTO.getLName() != null) {
            user.setLName(userProfileDTO.getLName());
        }
        if (userProfileDTO.getEmail() != null) {
            user.setEmail(userProfileDTO.getEmail());
        }
        if (userProfileDTO.getBio() != null) {
            user.setBio(userProfileDTO.getBio());
        }
        if (userProfileDTO.getPhoneNumber() != null) {
            user.setPhoneNumber(userProfileDTO.getPhoneNumber());
        }

        return user;
    }
}
