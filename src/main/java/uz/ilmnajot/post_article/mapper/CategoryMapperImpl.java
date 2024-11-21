package uz.ilmnajot.post_article.mapper;

import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Category;
import uz.ilmnajot.post_article.payload.CategoryDTO;

@Component
public class CategoryMapperImpl implements CategoryMapper {

    public Category toCategoryEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setDescription(categoryDTO.getDescription());
        return category;
    }

    public CategoryDTO toCategoryDTO(Category category) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setName(category.getName());
        categoryDTO.setDescription(category.getDescription());
        return categoryDTO;
    }


    @Override
    public Category toUpdateEntity(Category category, CategoryDTO categoryDTO) {
        if (categoryDTO.getName()!=null){
            category.setName(categoryDTO.getName());
        }
        if (categoryDTO.getDescription()!=null){
            category.setDescription(categoryDTO.getDescription());
        }
        return category;
    }
}
