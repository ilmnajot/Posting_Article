package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.payload.CourseDTO;

public interface CourseMapper {

    Course toCourseEntity(CourseDTO courseDTO);
    CourseDTO toCourseDTO(Course course);
    public Course toCourseUpdateEntity(Course course, CourseDTO courseDTO);
}
