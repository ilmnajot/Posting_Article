package uz.ilmnajot.post_article.controller;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.payload.CourseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.interfaces.CourseService;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @PostMapping("/addCourse")
    public HttpEntity<ApiResponse> addCourse(@RequestBody CourseDTO courseDTO) {
        ApiResponse apiResponse = courseService.addCourse(courseDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("updateCourse/{courseId}")
    public HttpEntity<ApiResponse> updateCourse(@RequestBody CourseDTO courseDTO,
                                                @PathVariable Long courseId) {
        ApiResponse apiResponse = courseService.updateCourse(courseDTO, courseId);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("getCourse/{courseId}")
    public HttpEntity<ApiResponse> getCourse(@PathVariable Long courseId) {
        ApiResponse course = courseService.getCourse(courseId);
        return ResponseEntity.ok(course);
    }

    @GetMapping("/getCourses")
    public HttpEntity<ApiResponse> getCourses() {
        ApiResponse courses = courseService.getCourses();
        return ResponseEntity.ok(courses);
    }

    @DeleteMapping("deleteCourse/{courseId}")
    public HttpEntity<ApiResponse> deleteCourse(@PathVariable Long courseId) {
        ApiResponse apiResponse = courseService.deleteCourse(courseId);
        return ResponseEntity.ok(apiResponse);
    }

}
