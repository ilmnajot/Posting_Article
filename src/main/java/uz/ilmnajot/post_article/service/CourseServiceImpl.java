package uz.ilmnajot.post_article.service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.exception.AlreadyExistsException;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.mapper.CourseMapper;
import uz.ilmnajot.post_article.mapper.UserMapper;
import uz.ilmnajot.post_article.payload.CourseDTO;
import uz.ilmnajot.post_article.payload.CourseResponseDTO;
import uz.ilmnajot.post_article.payload.MentorDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.CourseRepository;
import uz.ilmnajot.post_article.repository.UserRepository;
import uz.ilmnajot.post_article.service.interfaces.CourseService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserRepository userRepository;

//    @Value("${upload.dir}")
//    private String imageDirectory;

    @Override
    public ApiResponse addCourse(CourseDTO courseDTO) {
        Optional<Course> optionalCourse = courseRepository.findByTitleAndDeleteFalse(courseDTO.getTitle());
        if (optionalCourse.isPresent()) {
            throw new AlreadyExistsException("Course already exists");
        }
//        String addedImage = addImage(image);

        Course courseEntity = courseMapper.toCourseEntity(courseDTO);
        Course addedCourse = courseRepository.save(courseEntity);
        CourseResponseDTO mapperCourseDTO = courseMapper.toCourseDTO(addedCourse);
        return new ApiResponse(true, "success", HttpStatus.CREATED, mapperCourseDTO);
    }

//    private String addImage(MultipartFile image) {
//        if (image == null || image.isEmpty()) {
//            return null;
//        }
//        try {
//            String imageFileName = image.getOriginalFilename();
//            if (imageFileName==null || imageFileName.trim().isEmpty()) {
//                throw new ResourceNotFoundException("Image name is invalid");
//            }
//            String replacedAll = imageFileName.replaceAll("[^a-zA-Z0-9._-]", "_");
//            String fileName = UUID.randomUUID() + "_" + replacedAll;
//            Path imagePath = Paths.get(imageDirectory, fileName);
//            Files.createDirectories(imagePath.getParent()); // Create directories if not exists
//            Files.write(imagePath, image.getBytes()); // Save the image
//            return "/images/" + fileName;
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to save image: " + e.getMessage());
//        }
//
//    }


    @Override
    public ApiResponse updateCourse(CourseDTO courseDTO, Long courseId) {
        Course course = courseRepository.findByIdAndDeleteFalse(courseId).orElseThrow(
                () -> new ResourceNotFoundException("Course not found"));
        Course courseUpdateEntity = courseMapper.toCourseUpdateEntity(course, courseDTO);
        Course save = courseRepository.save(courseUpdateEntity);
        CourseResponseDTO mapperCourseDTO = courseMapper.toCourseDTO(save);
        return new ApiResponse(true, "success", HttpStatus.OK, mapperCourseDTO);
    }

    @Override
    public ApiResponse getCourse(Long courseId) {
        Course course = courseRepository.findByIdAndDeleteFalse(courseId).orElseThrow(() -> new ResourceNotFoundException("Course not found"));
        CourseResponseDTO mapperCourseDTO = courseMapper.toCourseDTO(course);
        return new ApiResponse(true, "success", HttpStatus.OK, mapperCourseDTO);
    }

    @Override
    public CourseResponseDTO getCourseById(Long courseId) {
        Course course = courseRepository.findByIdAndDeleteFalse(courseId).orElseThrow(
                () -> new ResourceNotFoundException("Course not found"));
        return courseMapper.toCourseDTO(course);
    }

    @Override
    public ApiResponse getCourses() {
        List<Course> courseList = courseRepository.findAll();
        List<CourseResponseDTO> courseDTOList = courseList
                .stream()
                .map(courseMapper::toCourseDTO)
                .toList();
        return new ApiResponse(true, "success", HttpStatus.OK, courseDTOList);
    }


    @Override
    public List<CourseResponseDTO> getCoursesList() {
        List<Course> courseList = courseRepository.findAll();
        return courseList
                .stream()
                .map(courseMapper::toCourseDTO)
                .toList();
    }

    @Override
    public ApiResponse deleteCourse(Long courseId) {
        Course course = courseRepository.findByIdAndDeleteFalse(courseId).orElseThrow(
                () -> new ResourceNotFoundException("Course not found"));
        course.setDelete(true);
        courseRepository.save(course);
        return new ApiResponse(true, "success", HttpStatus.OK, "Course has been deleted");
    }

//    public List<MentorDTO> getAllMentors(){
//        List<User> userList = userRepository.findAll();
//        return userList.stream().map(userMapper::toMentorDTO).toList();
//    }

    public Course getCourseByCourseId(Long courseId) {
        return courseRepository.findByIdAndDeleteFalse(courseId).orElseThrow(
                () -> new ResourceNotFoundException("Course not found"));
    }
    public List<CourseResponseDTO> getCoursesByUserId(Long userId) {
        Set<Course> coursesByUserId = courseRepository.getCoursesByUserId(userId);
        return coursesByUserId
                .stream()
                .map(courseMapper::toCourseDTO)
                .toList();
    }

}
