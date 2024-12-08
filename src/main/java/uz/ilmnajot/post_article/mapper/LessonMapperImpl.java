package uz.ilmnajot.post_article.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Lesson;
import uz.ilmnajot.post_article.entity.Module;
import uz.ilmnajot.post_article.payload.LessonDTO;
import uz.ilmnajot.post_article.payload.LessonRequestDTO;
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
                .duration(lesson.getDuration())
                .moduleId(lesson.getModule().getId())
                .videoURL(lesson.getVideoURL())
                .build();
    }

    @Override
    public Lesson toLessonEntity(Module module, LessonRequestDTO lessonRequestDTO) {
        Lesson lesson = new Lesson();
        lesson.setName(lessonRequestDTO.getName());
        lesson.setDescription(lessonRequestDTO.getDescription());
        lesson.setDuration(lessonRequestDTO.getDuration());
        lesson.setVideoURL(lessonRequestDTO.getVideoURL());
        lesson.setModule(module);
        return lesson;
    }

    public Lesson toUpdateLessonEntity(Lesson lesson, LessonRequestDTO lessonDTO) {
        if (lessonDTO.getName() != null) {
            lesson.setName(lessonDTO.getName());
        }
        if (lessonDTO.getDescription() != null) {
            lesson.setDescription(lessonDTO.getDescription());
        }
        if (lessonDTO.getVideoURL() != null) {
            lesson.setVideoURL(lessonDTO.getVideoURL());
        }
        if (lessonDTO.getDuration() != null) {
            lesson.setDuration(lessonDTO.getDuration());
        }

        return lesson;
    }
}
