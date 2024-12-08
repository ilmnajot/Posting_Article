package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.Course;
import uz.ilmnajot.post_article.entity.Module;
import uz.ilmnajot.post_article.payload.ModuleCreateDTO;
import uz.ilmnajot.post_article.payload.ModuleDTO;

public interface ModuleMapper {

    Module toModuleEntity(ModuleCreateDTO moduleDTO, Course course);
    ModuleDTO toModuleDTO(Module module);
    Module toUpdateModuleEntity(Module module, ModuleDTO moduleDTO);
}
