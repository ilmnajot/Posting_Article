package uz.ilmnajot.post_article.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.payload.LessonDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

public interface LessonService {
    ApiResponse addLesson(Long courseId, String name, String description, Integer duration, MultipartFile video);

    ApiResponse getLessonsByCourse(Long courseId);

    ApiResponse updateLesson(Long lessonId, LessonDTO updatedLesson);

    ApiResponse deleteLesson(Long lessonId);

    ApiResponse searchLessons(String keyword);
}