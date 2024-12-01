package uz.ilmnajot.post_article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.exception.AlreadyExistsException;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.mapper.CourseMapper;
import uz.ilmnajot.post_article.payload.CourseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.CourseRepository;
import uz.ilmnajot.post_article.service.interfaces.CourseService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;


    @Override
    public ApiResponse addCourse(CourseDTO courseDTO) {
        Optional<Course> optionalCourse = courseRepository.findByTitleAndDeleteFalse(courseDTO.getTitle());
        if (optionalCourse.isPresent()) {
            throw new AlreadyExistsException("Course already exists");
        }

        Course courseEntity = courseMapper.toCourseEntity(courseDTO);
        Course addedCourse = courseRepository.save(courseEntity);
        CourseDTO mapperCourseDTO = courseMapper.toCourseDTO(addedCourse);
        return new ApiResponse(true, "success", HttpStatus.CREATED, mapperCourseDTO);
    }

    @Override
    public ApiResponse updateCourse(CourseDTO courseDTO, Long courseId) {
        Course course = courseRepository.findByIdAndDeleteFalse(courseId).orElseThrow(
                () -> new ResourceNotFoundException("Course not found"));
        Course courseUpdateEntity = courseMapper.toCourseUpdateEntity(course, courseDTO);
        Course save = courseRepository.save(courseUpdateEntity);
        CourseDTO mapperCourseDTO = courseMapper.toCourseDTO(save);
        return new ApiResponse(true, "success", HttpStatus.OK, mapperCourseDTO);
    }

    @Override
    public ApiResponse getCourse(Long courseId) {
        Course course = courseRepository.findByIdAndDeleteFalse(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        CourseDTO mapperCourseDTO = courseMapper.toCourseDTO(course);
        return new ApiResponse(true, "success", HttpStatus.OK, mapperCourseDTO);
    }

    @Override
    public CourseDTO getCourseById(Long courseId) {
        Course course = courseRepository.findByIdAndDeleteFalse(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        return courseMapper.toCourseDTO(course);
    }

    @Override
    public ApiResponse getCourses() {
        List<Course> courseList = courseRepository.findAll();
        List<CourseDTO> courseDTOList = courseList.stream().map(courseMapper::toCourseDTO).toList();
        return new ApiResponse(true, "success", HttpStatus.OK, courseDTOList);
    }


    @Override
    public List<CourseDTO> getCoursesList() {
        List<Course> courseList = courseRepository.findAll();
        return courseList
                .stream()
                .map(courseMapper::toCourseDTO)
                .toList();
    }

    @Override
    public ApiResponse deleteCourse(Long courseId) {
        Course course = courseRepository.findByIdAndDeleteFalse(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        course.setDelete(true);
        courseRepository.save(course);
        return new ApiResponse(true, "success", HttpStatus.OK, "Course has been deleted");
    }


}
