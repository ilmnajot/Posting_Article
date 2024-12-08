package uz.ilmnajot.post_article.service.interfaces;

import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.payload.LessonDTO;
import uz.ilmnajot.post_article.payload.LessonRequestDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

public interface LessonService {
    ApiResponse addLesson(Long moduleId,String name, String description, Integer duration, MultipartFile video);

    ApiResponse getModulesByCourse(Long courseId);

    ApiResponse updateLesson(Long lessonId, LessonRequestDTO updatedLesson);

    ApiResponse deleteLesson(Long lessonId);

    ApiResponse searchLessons(String keyword);

    ApiResponse getLessonsByModule(Long moduleId);

    void videoStream(ServerHttpResponse httpResponse, String httpRangeList, String fileType, String fileName);

    ApiResponse addLessonFromYoutube(Long moduleId, LessonRequestDTO lessonRequestDTO);
}
