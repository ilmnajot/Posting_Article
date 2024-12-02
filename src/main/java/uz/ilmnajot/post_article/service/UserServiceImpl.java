package uz.ilmnajot.post_article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.enums.ResponseMessage;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.payload.CourseDTO;
import uz.ilmnajot.post_article.payload.UserDTO;
import uz.ilmnajot.post_article.payload.UserProfileDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.UserRepository;
import uz.ilmnajot.post_article.service.interfaces.UserService;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public ApiResponse updateUserProfile(Long userId, UserDTO userDTO) {
        User user = userRepository.findByIdAndDeleteFalse(userId).orElseThrow(() -> new ResourceNotFoundException(ResponseMessage.NOT_FOUND.getMessage()));
        user.setEmailCode(userDTO.getEmail());

        return null;
    }

    @Override
    public UserProfileDTO getUserById(Long id) {
        User user = userRepository.findByIdAndDeleteFalse(id).orElseThrow(
                () -> new ResourceNotFoundException(ResponseMessage.NOT_FOUND.getMessage()));
        return null;

    }

    @Override
    public ApiResponse enrollInCourse(Long userId, Long courseId) {
        return null;
    }

    @Override
    public Set<CourseDTO> getEnrolledCourses(Long userId) {
        return Set.of();
    }

    @Override
    public ApiResponse unEnrollFromCourse(Long userId, Long courseId) {
        return null;
    }
}
