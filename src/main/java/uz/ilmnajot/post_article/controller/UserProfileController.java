package uz.ilmnajot.post_article.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.payload.CourseDTO;
import uz.ilmnajot.post_article.payload.UserDTO;
import uz.ilmnajot.post_article.payload.UserProfileDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.interfaces.UserService;

import java.util.Set;

@RestController
@RequestMapping("/api/profiles")
public class UserProfileController {

    private final UserService userService;

    public UserProfileController(UserService userService) {
        this.userService = userService;
    }

    // Get profile details by user ID
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDTO> getUserProfile(@PathVariable(name = "userId") Long userId) {
        UserProfileDTO apiResponse = userService.getUserById(userId);
        return ResponseEntity.ok(apiResponse);
    }

    // Update profile details
    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUserProfile(@PathVariable("userId") Long userId, @RequestBody UserDTO userDTO) {
        ApiResponse response = userService.updateUserProfile(userId, userDTO);
        return ResponseEntity.ok(response);
    }

    // Enroll user in a course
    @PostMapping("/{userId}/enroll/{courseId}")
    public ResponseEntity<ApiResponse> enrollInCourse(@PathVariable Long userId, @PathVariable Long courseId) {
        ApiResponse response = userService.enrollInCourse(userId, courseId);
        return ResponseEntity.ok(response);
    }

    // Get all enrolled courses for a user
    @GetMapping("/{userId}/courses")
    public ResponseEntity<Set<CourseDTO>> getEnrolledCourses(@PathVariable Long userId) {
        Set<CourseDTO> enrolledCourses = userService.getEnrolledCourses(userId);// set<Courses>
        return ResponseEntity.ok(enrolledCourses);
    }

    // Unenroll user from a course
    @DeleteMapping("/{userId}/unEnroll/{courseId}")
    public ResponseEntity<ApiResponse> unEnrollFromCourse(@PathVariable Long userId, @PathVariable Long courseId) {
        ApiResponse response = userService.unEnrollFromCourse(userId, courseId);
        return ResponseEntity.status(response.isSuccess() ? 200 : 400).body(response);
    }
}
