package uz.ilmnajot.post_article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.enums.ResponseMessage;
import uz.ilmnajot.post_article.exception.AlreadyExistsException;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.mapper.CourseMapper;
import uz.ilmnajot.post_article.mapper.UserMapper;
import uz.ilmnajot.post_article.mapper.UserMapperImpl;
import uz.ilmnajot.post_article.payload.CourseResponseDTO;
import uz.ilmnajot.post_article.payload.UserProfileDTO;
import uz.ilmnajot.post_article.payload.UserProfileResponseDTO;
import uz.ilmnajot.post_article.payload.UserResponseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.CourseRepository;
import uz.ilmnajot.post_article.repository.UserRepository;
import uz.ilmnajot.post_article.service.interfaces.CourseService;
import uz.ilmnajot.post_article.service.interfaces.UserService;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserMapperImpl userMapperImpl;
    private final CourseRepository courseRepository;
    private final CourseService courseService;
    private final CourseMapper courseMapper;


    @Override
    public ApiResponse updateUserProfile(Long userId, UserProfileDTO userDTO) {
        User user = userRepository.findByIdAndDeleteFalse(userId).orElseThrow(
                () -> new ResourceNotFoundException(ResponseMessage.NOT_FOUND.getMessage()));
        User updateEntity = userMapper.toUserProfileUpdateEntity(userDTO, user);
        User saved = userRepository.save(updateEntity);
        UserResponseDTO userResponseDTO = userMapper.toUserResponseDTO(saved);
        return new ApiResponse(true, "success", HttpStatus.OK, "Your profile has been successfully updated to: " + userResponseDTO);
    }

    @Override
    public ApiResponse getUserById(Long id) {
        User user = userRepository.findByIdAndDeleteFalse(id).orElseThrow(
                () -> new ResourceNotFoundException(ResponseMessage.NOT_FOUND.getMessage()));
        UserProfileResponseDTO userProfileResponseDTO = userMapper.toUserProfileEntity(user);
        return new ApiResponse(true, "success", HttpStatus.OK, userProfileResponseDTO);
    }

    @Override
    public ApiResponse enrollInCourse(Long userId, Long courseId) {
        Course course = courseService.getCourseByCourseId(courseId);
        User user = getUser(userId);
        Set<Course> enrolledCourses = user.getEnrolledCourses();
        if (enrolledCourses.contains(course)) {
            throw new AlreadyExistsException("You have already enrolled a course");
        }
        enrolledCourses.add(course);
        userRepository.save(user);
        return new ApiResponse(true, "success", HttpStatus.CREATED, "You have successfully enrolled a course");
    }

    @Override
    public List<CourseResponseDTO> getEnrolledCourses(Long userId) {
        User user = getUser(userId);
        Set<Course> enrolledCourses = user.getEnrolledCourses();
        return enrolledCourses
                .stream()
                .map(courseMapper::toCourseDTO)
                .toList();
    }

    @Override
    public ApiResponse unEnrollFromCourse(Long userId, Long courseId) {
        User user = getUser(userId);
        Course course = courseService.getCourseByCourseId(courseId);
        Set<Course> enrolledCourses = user.getEnrolledCourses();
        enrolledCourses.remove(course);
        userRepository.save(user);
        return new ApiResponse(true, "success", HttpStatus.CREATED, "You have successfully unEnrolled a course");
    }


    public User getUser(Long userId) {
        return userRepository.findByIdAndDeleteFalse(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));
    }
}
