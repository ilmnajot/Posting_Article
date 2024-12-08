package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.payload.CourseDTO;
import uz.ilmnajot.post_article.payload.CourseResponseDTO;

public interface CourseMapper {

    Course toCourseEntity(CourseDTO courseDTO);
    CourseResponseDTO toCourseDTO(Course course);
    Course toCourseUpdateEntity(Course course, CourseDTO courseDTO);
}
