package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.entity.Lesson;
import uz.ilmnajot.post_article.entity.Module;
import uz.ilmnajot.post_article.payload.LessonDTO;
import uz.ilmnajot.post_article.payload.LessonResponseDTO;

public interface LessonMapper {

    Lesson toLessonEntity(Module module, String name, String description, Integer duration, String videoURL);
    LessonResponseDTO toLessonDTO(Lesson lesson);
    Lesson toUpdateLessonEntity(Lesson lesson, LessonDTO lessonDTO);

}
