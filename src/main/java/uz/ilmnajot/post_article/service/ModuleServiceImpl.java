package uz.ilmnajot.post_article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.entity.Module;
import uz.ilmnajot.post_article.enums.ResponseMessage;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.mapper.ModuleMapper;
import uz.ilmnajot.post_article.payload.ModuleCreateDTO;
import uz.ilmnajot.post_article.payload.ModuleDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.CourseRepository;
import uz.ilmnajot.post_article.repository.ModuleRepository;
import uz.ilmnajot.post_article.service.interfaces.ModuleService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final ModuleMapper moduleMapper;
    private final CourseRepository courseRepository;

    @Override
    public ApiResponse addModule(Long courseId, ModuleCreateDTO moduleDTO) {
        Course course = courseRepository.findByIdAndDeleteFalse(courseId).orElseThrow(
                () -> new ResourceNotFoundException(ResponseMessage.NOT_FOUND.getMessage()));
        Module moduleEntity = moduleMapper.toModuleEntity(moduleDTO, course);
        moduleEntity = moduleRepository.save(moduleEntity);
        ModuleDTO moduleDTO1 = moduleMapper.toModuleDTO(moduleEntity);
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.CREATED, moduleDTO1);
    }

    @Override
    public ApiResponse getModulesInCourse(Long courseId) {
        List<Module> moduleList = moduleRepository.findByCourse_IdAndDeleteFalse(courseId);
        List<ModuleDTO> moduleDTOList = moduleList.stream().map(moduleMapper::toModuleDTO).toList();
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.OK, moduleDTOList);
    }

    @Override
    public ApiResponse getModule(Long moduleId) {
        Module module = getModuleById(moduleId);
        ModuleDTO moduleDTO = moduleMapper.toModuleDTO(module);
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.OK, moduleDTO);
    }

    @Override
    public ApiResponse getAllModules() {
        List<Module> moduleList = moduleRepository.findAll();
        List<ModuleDTO> moduleDTOList = moduleList.stream().map(moduleMapper::toModuleDTO).toList();
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.OK, moduleDTOList);
    }

    @Override
    public ApiResponse updateModule(Long moduleId, ModuleDTO moduleDTO) {
        Module module = moduleRepository.findByIdAndDeleteFalse(moduleId).orElseThrow(
                () -> new ResourceNotFoundException(ResponseMessage.NOT_FOUND.getMessage()));
        Module updateModuleEntity = moduleMapper.toUpdateModuleEntity(module, moduleDTO);
        module = moduleRepository.save(updateModuleEntity);
        ModuleDTO mapperModuleDTO = moduleMapper.toModuleDTO(module);
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.OK, mapperModuleDTO);
    }

    @Override
    public ApiResponse deleteModule(Long moduleId) {
        Module module = getModuleById(moduleId);
        module.setDelete(true);
        ModuleDTO moduleDTO = moduleMapper.toModuleDTO(module);
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.OK, "Module has been successfully deleted: " + moduleDTO);
    }

    private Module getModuleById(Long moduleId) {
        return moduleRepository.findByIdAndDeleteFalse(moduleId).orElseThrow(
                () -> new ResourceNotFoundException(ResponseMessage.NOT_FOUND.getMessage()));
    }
}
