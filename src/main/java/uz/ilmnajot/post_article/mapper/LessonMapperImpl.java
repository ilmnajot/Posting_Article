package uz.ilmnajot.post_article.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Lesson;
import uz.ilmnajot.post_article.entity.Module;
import uz.ilmnajot.post_article.payload.LessonDTO;
import uz.ilmnajot.post_article.payload.LessonResponseDTO;
@RequiredArgsConstructor
@Component
public class LessonMapperImpl implements LessonMapper {



    public Lesson toLessonEntity(Module module, String name, String description, Integer duration, String videoURL) {

        return Lesson
                .builder()
                .name(name)
                .description(description)
//                .orderIndex(lessonDTO.getOrderIndex())
                .videoURL(videoURL)
                .duration(duration)
                .module(module)
                .build();
    }

    public LessonResponseDTO toLessonDTO(Lesson lesson) {
        return LessonResponseDTO
                .builder()
                .id(lesson.getId())
                .name(lesson.getName())
                .description(lesson.getDescription())
                .videoURL(lesson.getVideoURL())
                .duration(lesson.getDuration())
                .build();
    }

    public Lesson toUpdateLessonEntity(Lesson lesson, LessonDTO lessonDTO) {
        if (lessonDTO.getName() != null) {
            lesson.setName(lessonDTO.getName());
        }
        if (lessonDTO.getDescription() != null) {
            lesson.setDescription(lessonDTO.getDescription());
        }
//        if (lessonDTO.getOrderIndex() != null) {
//            lesson.setOrderIndex(lessonDTO.getOrderIndex());
//        }
//        if (lessonDTO.getVideoURL() != null) {
//            lesson.setVideoURL(lessonDTO.getVideoURL());
//        }
        if (lessonDTO.getDuration() != null) {
            lesson.setDuration(lessonDTO.getDuration());
        }

        return lesson;
    }
}
