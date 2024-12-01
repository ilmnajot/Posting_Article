package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.entity.Lesson;
import uz.ilmnajot.post_article.payload.LessonDTO;

public interface LessonMapper {

    Lesson toLessonEntity(Course course, String name, String description, Integer duration, String videoURL);
    LessonDTO toLessonDTO(Lesson lesson);
    Lesson toUpdateLessonEntity(Lesson lesson, LessonDTO lessonDTO);

}
