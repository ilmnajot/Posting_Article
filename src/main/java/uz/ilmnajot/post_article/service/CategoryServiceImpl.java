package uz.ilmnajot.post_article.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.entity.Category;
import uz.ilmnajot.post_article.exception.AlreadyExistsException;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.mapper.CategoryMapper;
import uz.ilmnajot.post_article.payload.CategoryDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.CategoryRepository;
import uz.ilmnajot.post_article.utils.MessageKey;

import java.rmi.AlreadyBoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ApiResponse addCategory(CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findByNameAndDeleteFalse(categoryDTO.getName());
        if (optionalCategory.isPresent()) {
            throw new AlreadyExistsException("Category already exists", HttpStatus.BAD_REQUEST);
        }
        Category categoryEntity = categoryMapper.toCategoryEntity(categoryDTO);
        Category saved = categoryRepository.save(categoryEntity);
        CategoryDTO mapperCategoryDTO = categoryMapper.toCategoryDTO(saved);
        return new ApiResponse(true, "success", HttpStatus.CREATED, mapperCategoryDTO);
    }

    @Override
    public ApiResponse getCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);
        CategoryDTO categoryDTO = categoryMapper.toCategoryDTO(category);
        return new ApiResponse(true, "success", HttpStatus.OK, categoryDTO);
    }

    @Override
    public ApiResponse getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryDTOList.add(categoryMapper.toCategoryDTO(category));
        }
        return new ApiResponse(true, "success", HttpStatus.OK, categoryDTOList);
    }

    @Override
    public ApiResponse getAllExistsCategories() {
        List<Category> categoryList = categoryRepository.findAllByDeleteIsFalse();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryDTOList.add(categoryMapper.toCategoryDTO(category));
        }
        return new ApiResponse(true, "success", HttpStatus.OK, categoryDTOList);
    }

    @Override
    public ApiResponse deleteCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);
        category.setDelete(true);
        Category saved = categoryRepository.save(category);
        CategoryDTO categoryDTO = categoryMapper.toCategoryDTO(saved);
        return new ApiResponse(true, "success", HttpStatus.OK, "Category has been deleted: " + categoryDTO);
    }

    @Override
    public ApiResponse updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = getCategoryById(categoryId);
        Category updateEntity = categoryMapper.toUpdateEntity(category, categoryDTO);
        Category saved = categoryRepository.save(updateEntity);
        CategoryDTO categoryDTOSaved = categoryMapper.toCategoryDTO(saved);
        return new ApiResponse(true, "success", HttpStatus.OK, "Category has been updated: " + categoryDTOSaved);
    }

    @Override
    public ApiResponse getAllRemovedCategories() {
        List<Category> categoryList = categoryRepository.findAllByDeleteIsTrue();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryDTOList.add(categoryMapper.toCategoryDTO(category));
        }
        return new ApiResponse(true, "success", HttpStatus.OK, categoryDTOList);
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findByIdAndDeleteFalse(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(MessageKey.CATEGORY_NOT_FOUND, HttpStatus.NOT_FOUND));
    }
}