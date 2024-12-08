package uz.ilmnajot.post_article.mapper;

import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.entity.Module;
import uz.ilmnajot.post_article.enums.ResponseMessage;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.payload.ModuleCreateDTO;
import uz.ilmnajot.post_article.payload.ModuleDTO;
import uz.ilmnajot.post_article.repository.CourseRepository;

@Component
public class ModuleMapperImpl implements ModuleMapper {


    private final CourseRepository courseRepository;

    public ModuleMapperImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }


    @Override
    public Module toModuleEntity(ModuleCreateDTO moduleDTO, Course course) {
        Module module = new Module();
        module.setName(moduleDTO.getName());
        module.setCourse(course);
        return module;
    }

    public ModuleDTO toModuleDTO(Module module) {
        ModuleDTO moduleDTO = new ModuleDTO();
        moduleDTO.setId(module.getId());
        moduleDTO.setName(module.getName());
        moduleDTO.setCourseId(module.getCourse().getId());
        return moduleDTO;
    }

    public Module toUpdateModuleEntity(Module module, ModuleDTO moduleDTO) {
        Course course = courseRepository.findByIdAndDeleteFalse(moduleDTO.getCourseId()).orElseThrow(
                () -> new ResourceNotFoundException(ResponseMessage.NOT_FOUND.getMessage()));
        if (moduleDTO.getName() != null) {
            module.setName(moduleDTO.getName());
        }
        if (moduleDTO.getCourseId() != null) {
            module.setCourse(course);
        }
        return module;
    }
}
