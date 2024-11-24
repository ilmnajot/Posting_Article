package uz.ilmnajot.post_article.service.interfaces;

import jakarta.validation.Valid;
import uz.ilmnajot.post_article.payload.CategoryDTO;
import uz.ilmnajot.post_article.payload.CategoryResponseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

public interface CategoryService {
    ApiResponse addCategory(CategoryDTO categoryDTO);

    ApiResponse getCategory(Long categoryId);

    ApiResponse getAllCategories();

    ApiResponse getAllExistsCategories();

    ApiResponse deleteCategory(Long categoryId);

    ApiResponse updateCategory(Long categoryId, CategoryDTO categoryDTO);

    ApiResponse getAllRemovedCategories();
}
