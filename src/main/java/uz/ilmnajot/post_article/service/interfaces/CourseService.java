package uz.ilmnajot.post_article.service.interfaces;

import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.payload.CourseDTO;
import uz.ilmnajot.post_article.payload.CourseResponseDTO;
import uz.ilmnajot.post_article.payload.MentorDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

import java.util.List;

public interface CourseService {

    ApiResponse addCourse(CourseDTO courseDTO);

    ApiResponse updateCourse(CourseDTO courseDTO, Long courseId);

    ApiResponse getCourse(Long courseId);

    CourseResponseDTO getCourseById(Long courseId);

    ApiResponse getCourses();
    List<CourseResponseDTO> getCoursesList();

    ApiResponse deleteCourse(Long courseId);

//    List<MentorDTO> getAllMentors();

    Course getCourseByCourseId(Long courseId);

    List<CourseResponseDTO> getCoursesByUserId(Long userId);
}
