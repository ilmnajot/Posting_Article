package uz.ilmnajot.post_article.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.payload.CourseDTO;
import uz.ilmnajot.post_article.payload.CourseResponseDTO;
import uz.ilmnajot.post_article.repository.CourseRepository;
import uz.ilmnajot.post_article.repository.UserRepository;
import uz.ilmnajot.post_article.service.interfaces.UserService;

@RequiredArgsConstructor
@Component
public class CourseMapperImpl implements CourseMapper {

    public Course toCourseEntity(CourseDTO courseDTO) {

        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setMentorName(courseDTO.getMentorName());
        course.setPrice(courseDTO.getPrice());
        course.setLessonCount(courseDTO.getLessonCount());
        course.setFree(courseDTO.isFree());
//        course.setImage(image);
        return course;
    }

    public CourseResponseDTO toCourseDTO(Course course) {
        CourseResponseDTO courseDTO = new CourseResponseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setMentorName(course.getMentorName());
        courseDTO.setPrice(course.getPrice());
        courseDTO.setLessonCount(course.getLessonCount());
        courseDTO.setFree(course.isFree());
//        courseDTO.setImage(course.getImage());
        return courseDTO;
    }

    public Course toCourseUpdateEntity(Course course, CourseDTO courseDTO) {

        if (courseDTO.getTitle() != null) {
            course.setTitle(courseDTO.getTitle());
        }
        if (courseDTO.getDescription() != null) {
            course.setDescription(courseDTO.getDescription());
        }
        if (courseDTO.getPrice() != null) {
            course.setPrice(courseDTO.getPrice());
        }
        if (courseDTO.getLessonCount() != null) {
            course.setLessonCount(courseDTO.getLessonCount());
        }
        if (courseDTO.isFree()) {
            course.setFree(courseDTO.isFree());
        }
        return course;
    }


}
