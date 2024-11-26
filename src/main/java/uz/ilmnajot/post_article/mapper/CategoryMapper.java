package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.Category;
import uz.ilmnajot.post_article.payload.CategoryDTO;
import uz.ilmnajot.post_article.payload.CategoryResponseDTO;

public interface CategoryMapper {

    CategoryResponseDTO toCategoryDTO(Category category);

    Category toCategoryEntity(CategoryDTO categoryDTO, String imageURL);

    Category toUpdateEntity(Category category,CategoryDTO categoryDTO);
}
