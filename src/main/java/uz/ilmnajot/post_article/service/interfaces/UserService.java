package uz.ilmnajot.post_article.service.interfaces;

import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.payload.CourseDTO;
import uz.ilmnajot.post_article.payload.UserDTO;
import uz.ilmnajot.post_article.payload.UserProfileDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

import java.util.Set;

public interface UserService {
    UserProfileDTO getUserById(Long id);

    ApiResponse updateUserProfile(Long userId, UserDTO userDTO);

    ApiResponse enrollInCourse(Long userId, Long courseId);

    Set<CourseDTO> getEnrolledCourses(Long userId);

    ApiResponse unEnrollFromCourse(Long userId, Long courseId);
}
