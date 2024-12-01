package uz.ilmnajot.post_article.service.interfaces;

import uz.ilmnajot.post_article.payload.CourseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

import java.util.List;

public interface CourseService {
    ApiResponse addCourse(CourseDTO courseDTO);

    ApiResponse updateCourse(CourseDTO courseDTO, Long courseId);

    ApiResponse getCourse(Long courseId);
    CourseDTO getCourseById(Long courseId);

    ApiResponse getCourses();
    List<CourseDTO> getCoursesList();

    ApiResponse deleteCourse(Long courseId);
}
