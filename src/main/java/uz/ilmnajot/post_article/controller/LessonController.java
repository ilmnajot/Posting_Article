package uz.ilmnajot.post_article.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.payload.LessonDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.interfaces.LessonService;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @Operation(summary = "Add a lesson with a video file")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Lesson added successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping(value = "/addLesson/{moduleId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HttpEntity<ApiResponse> addLesson(
            @PathVariable(name = "moduleId") Long moduleId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("duration") Integer duration,
            @RequestParam("video") MultipartFile video) {
        ApiResponse apiResponse = lessonService.addLesson(moduleId, name, description, duration, video);
        return ResponseEntity.ok(apiResponse);
    }

    // Get lessons by course
    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse> getModulesByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(lessonService.getModulesByCourse(courseId));
    }

    @GetMapping("/module/{moduleId}")
    public ResponseEntity<ApiResponse> getLessonsByModule(@PathVariable Long moduleId) {
        ApiResponse lessonsByModule = lessonService.getLessonsByModule(moduleId);
        return ResponseEntity.ok(lessonsByModule);
    }


    // Update a lesson
    @PutMapping("/{lessonId}")
    public ResponseEntity<ApiResponse> updateLesson(
            @PathVariable Long lessonId, @RequestBody @Valid LessonDTO updatedLesson) {
        ApiResponse apiResponse = lessonService.updateLesson(lessonId, updatedLesson);
        return ResponseEntity.ok(apiResponse);
    }

    // Delete a lesson
    @DeleteMapping("/{lessonId}")
    public ResponseEntity<ApiResponse> deleteLesson(@PathVariable Long lessonId) {
        ApiResponse apiResponse = lessonService.deleteLesson(lessonId);
        return ResponseEntity.ok(apiResponse);
    }

    // Search lessons by keyword
    @GetMapping("/search")
    public ResponseEntity<ApiResponse> searchLessons(@RequestParam String keyword) {
        return ResponseEntity.ok(lessonService.searchLessons(keyword));
    }
}
