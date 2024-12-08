package uz.ilmnajot.post_article.service.interfaces;

import uz.ilmnajot.post_article.payload.ModuleCreateDTO;
import uz.ilmnajot.post_article.payload.ModuleDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

public interface ModuleService {
    ApiResponse addModule(Long courseId, ModuleCreateDTO moduleDTO);

    ApiResponse getModulesInCourse(Long courseId);

    ApiResponse getModule(Long moduleId);

    ApiResponse getAllModules();

    ApiResponse updateModule(Long moduleId, ModuleDTO moduleDTO);

    ApiResponse deleteModule(Long moduleId);
}
