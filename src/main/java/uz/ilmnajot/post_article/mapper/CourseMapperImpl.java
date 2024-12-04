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

@RequiredArgsConstructor
@Component
public class CourseMapperImpl implements CourseMapper {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;


    public Course toCourseEntity(CourseDTO courseDTO) {
        User mentor = userRepository.findByIdAndDeleteFalse(courseDTO.getMentorId()).orElseThrow(
                () -> new ResourceNotFoundException("Mentor not found"));
        Course course = new Course();
        course.setTitle(courseDTO.getTitle());
        course.setDescription(courseDTO.getDescription());
        course.setPrice(courseDTO.getPrice());
        course.setLessonCount(courseDTO.getLessonCount());
        course.setFree(courseDTO.isFree());
//        course.setImage(image);
        course.setMentor(mentor);
        return course;
    }

    public CourseResponseDTO toCourseDTO(Course course) {
        CourseResponseDTO courseDTO = new CourseResponseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setTitle(course.getTitle());
        courseDTO.setDescription(course.getDescription());
        courseDTO.setPrice(course.getPrice());
        courseDTO.setLessonCount(course.getLessonCount());
        courseDTO.setFree(course.isFree());
//        courseDTO.setImage(course.getImage());
        courseDTO.setMentorId(course.getMentor().getId());
        return courseDTO;
    }

    public Course toCourseUpdateEntity(Course course, CourseDTO courseDTO) {

        User mentor = userRepository.findByIdAndDeleteFalse(courseDTO.getMentorId()).orElseThrow(
                () -> new ResourceNotFoundException("Mentor not found"));

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
        if (courseDTO.getMentorId() != null) {
            course.setMentor(mentor);
        }
        return course;
    }


}
