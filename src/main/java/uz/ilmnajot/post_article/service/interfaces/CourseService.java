package uz.ilmnajot.post_article.service.interfaces;

import uz.ilmnajot.post_article.payload.CourseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

public interface CourseService {
    ApiResponse addCourse(CourseDTO courseDTO);

    ApiResponse updateCourse(CourseDTO courseDTO, Long courseId);

    ApiResponse getCourse(Long courseId);

    ApiResponse getCourses();

    ApiResponse deleteCourse(Long courseId);
}
