package uz.ilmnajot.post_article.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.payload.CourseResponseDTO;
import uz.ilmnajot.post_article.payload.UserProfileDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.interfaces.UserService;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/{userId}/enroll/{courseId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApiResponse> enrollInCourse(@PathVariable Long userId, @PathVariable Long courseId) {
        ApiResponse response = userService.enrollInCourse(userId, courseId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getProfile/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApiResponse> getUserProfile(@PathVariable(name = "userId") Long userId) {
        ApiResponse apiResponse = userService.getUserById(userId);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/updateProfile/{userId}")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApiResponse> updateUserProfile(@PathVariable("userId") Long userId, @RequestBody UserProfileDTO userProfileDTO) {
        ApiResponse response = userService.updateUserProfile(userId, userProfileDTO);
        return ResponseEntity.ok(response);
    }

    // Get all enrolled courses for a user
    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/{userId}/courses")
    public ResponseEntity<List<CourseResponseDTO>> getEnrolledCourses(@PathVariable Long userId) {
        List<CourseResponseDTO> enrolledCourses = userService.getEnrolledCourses(userId);// set<Courses>
        return ResponseEntity.ok(enrolledCourses);
    }

    @PreAuthorize("hasAuthority('USER')")
    @DeleteMapping("/{userId}/unEnroll/{courseId}")
    public ResponseEntity<ApiResponse> unEnrollFromCourse(@PathVariable Long userId, @PathVariable Long courseId) {
        ApiResponse response = userService.unEnrollFromCourse(userId, courseId);
        return ResponseEntity.ok(response);
    }
}
