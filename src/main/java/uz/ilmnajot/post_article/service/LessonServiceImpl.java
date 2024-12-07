package uz.ilmnajot.post_article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.entity.Lesson;
import uz.ilmnajot.post_article.entity.Module;
import uz.ilmnajot.post_article.enums.ResponseMessage;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.mapper.LessonMapper;
import uz.ilmnajot.post_article.mapper.ModuleMapper;
import uz.ilmnajot.post_article.payload.LessonDTO;
import uz.ilmnajot.post_article.payload.LessonResponseDTO;
import uz.ilmnajot.post_article.payload.ModuleDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.CourseRepository;
import uz.ilmnajot.post_article.repository.LessonRepository;
import uz.ilmnajot.post_article.repository.ModuleRepository;
import uz.ilmnajot.post_article.service.interfaces.LessonService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {


    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final LessonMapper lessonMapper;
    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;

    @Value("${video.upload.dir}")
    private String videoDirectory;


    @Override
    public ApiResponse addLesson(Long moduleId, String name, String description, Integer duration, MultipartFile video) {
        Module module = moduleRepository.findByIdAndDeleteFalse(moduleId).orElseThrow(
                () -> new ResourceNotFoundException(ResponseMessage.ERROR.getMessage()));
        String videoFile = savedVideoFile(video);
        Lesson lessonEntity = lessonMapper.toLessonEntity(module, name, description, duration, videoFile);
        int maxOrderIndex = lessonRepository.findByModule_IdOrderByOrderIndex(moduleId)
                .stream()
                .mapToInt(Lesson::getOrderIndex)
                .max()
                .orElse(0);
        lessonEntity.setOrderIndex(maxOrderIndex + 1);
        Lesson saved = lessonRepository.save(lessonEntity);
        LessonResponseDTO lessonDTO1 = lessonMapper.toLessonDTO(saved);
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.CREATED, lessonDTO1);
    }

    private String savedVideoFile(MultipartFile videoFile) {
        try {
            File file = new File(videoDirectory);
            if (!file.exists()) {
                file.mkdirs(); // Create the directory if it doesn't exist
            }

            // Save the file
            String fileName = UUID.randomUUID().toString() + "_" + videoFile.getOriginalFilename();
            Path filePath = Paths.get(file.getAbsolutePath(), fileName);
            Files.copy(videoFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return "/videos/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload vide file", e);
        }
    }


    @Override
    public ApiResponse getModulesByCourse(Long courseId) {
        List<Module> moduleList = moduleRepository.findByCourse_IdAndDeleteFalse(courseId);
        List<ModuleDTO> moduleDTOList = moduleList.stream().map(moduleMapper::toModuleDTO).toList();
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.OK, moduleDTOList);
    }

    @Override
    public ApiResponse updateLesson(Long lessonId, LessonDTO updatedLesson) {
        Lesson lesson = findLessonById(lessonId);
        Lesson updateLessonEntity = lessonMapper.toUpdateLessonEntity(lesson, updatedLesson);
        Lesson saved = lessonRepository.save(updateLessonEntity);
        LessonResponseDTO lessonDTO1 = lessonMapper.toLessonDTO(saved);
        return new ApiResponse(true, "success", HttpStatus.OK, lessonDTO1);
    }

    @Override
    public ApiResponse deleteLesson(Long lessonId) {
        Lesson lesson = findLessonById(lessonId);
        lesson.setDelete(true);
        lessonRepository.save(lesson);
        return new ApiResponse(true, "success", HttpStatus.NO_CONTENT, "Lesson has been successfully deleted");
    }

    @Override
    public ApiResponse searchLessons(String keyword) {
        List<Lesson> lessonList = lessonRepository.searchLessonsByName(keyword);
        List<LessonResponseDTO> lessonDTOList = lessonList.stream().map(lessonMapper::toLessonDTO).toList();
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.OK, lessonDTOList);
    }

    @Override
    public ApiResponse getLessonsByModule(Long moduleId) {
        List<Lesson> lessonList = lessonRepository.findByModule_IdOrderByOrderIndex(moduleId);
        List<LessonResponseDTO> lessonDTOList = lessonList.stream().map(lessonMapper::toLessonDTO).toList();
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.OK, lessonDTOList);
    }

    private Lesson findLessonById(Long lessonId) {
        return lessonRepository.findByIdAndDeleteFalse(lessonId).orElseThrow(
                () -> new ResourceNotFoundException("Lesson not found"));
    }
}
