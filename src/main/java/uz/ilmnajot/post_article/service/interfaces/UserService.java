package uz.ilmnajot.post_article.service.interfaces;

import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.payload.*;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

import java.util.List;

public interface UserService {
    ApiResponse getUserById(Long id);

    ApiResponse updateUserProfile(Long userId, UserProfileDTO userDTO);

    ApiResponse enrollInCourse(Long userId, Long courseId);

    List<CourseResponseDTO> getEnrolledCourses(Long userId);

    ApiResponse unEnrollFromCourse(Long userId, Long courseId);

    User getUser(Long userId);


}
