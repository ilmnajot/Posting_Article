package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.Category;
import uz.ilmnajot.post_article.payload.CategoryDTO;

public interface CategoryMapper {

    CategoryDTO toCategoryDTO(Category category);

    Category toCategoryEntity(CategoryDTO categoryDTO);

    Category toUpdateEntity(Category category,CategoryDTO categoryDTO);
}
