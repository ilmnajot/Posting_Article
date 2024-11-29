package uz.ilmnajot.post_article.service;

import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.entity.Category;
import uz.ilmnajot.post_article.exception.AlreadyExistsException;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.mapper.CategoryMapper;
import uz.ilmnajot.post_article.payload.CategoryDTO;
import uz.ilmnajot.post_article.payload.CategoryResponseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.CategoryRepository;
import uz.ilmnajot.post_article.service.interfaces.CategoryService;
import uz.ilmnajot.post_article.utils.MessageKey;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@NoArgsConstructor(force = true)
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Value("${upload.dir}")
    private String imageDirectory;



    @Override
    public ApiResponse addCategory(CategoryDTO categoryDTO, MultipartFile image) {
        Optional<Category> optionalCategory = categoryRepository.findByNameAndDeleteFalse(categoryDTO.getName());
        if (optionalCategory.isPresent()) {
            throw new AlreadyExistsException("Category already exists");
        }
        String addedImage = addImage(image);
        Category categoryEntity = categoryMapper.toCategoryEntity(categoryDTO, addedImage);
        Category saved = categoryRepository.save(categoryEntity);
        CategoryResponseDTO mapperCategoryDTO = categoryMapper.toCategoryDTO(saved);
        return new ApiResponse(true, "success", HttpStatus.CREATED, mapperCategoryDTO);
    }

    private String addImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return null;
        }
        try {
            String imageFileName = image.getOriginalFilename();
            if (imageFileName==null || imageFileName.trim().isEmpty()) {
                throw new ResourceNotFoundException("Image name is invalid");
            }
            String replacedAll = imageFileName.replaceAll("[^a-zA-Z0-9._-]", "_");
            String fileName = UUID.randomUUID() + "_" + replacedAll;
            Path imagePath = Paths.get(imageDirectory, fileName);
            Files.createDirectories(imagePath.getParent()); // Create directories if not exists
            Files.write(imagePath, image.getBytes()); // Save the image
            return "/images/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }

    }

    @Override
    public ApiResponse getCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);
        CategoryResponseDTO categoryDTO = categoryMapper.toCategoryDTO(category);
        return new ApiResponse(true, "success", HttpStatus.OK, categoryDTO);
    }
    @Override
    public CategoryResponseDTO getCategoryByID(Long categoryId) {
        Category category = getCategoryById(categoryId);
        CategoryResponseDTO categoryDTO = categoryMapper.toCategoryDTO(category);
        return categoryDTO;
    }

    @Override
    public ApiResponse getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryDTOList.add(categoryMapper.toCategoryDTO(category));
        }
        return new ApiResponse(true, "success", HttpStatus.OK, categoryDTOList);
    }

    @Override
    public List<CategoryResponseDTO> getAllExistsCategories() {
        List<Category> categoryList = categoryRepository.findAllByDeleteIsFalse();
        List<CategoryResponseDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryDTOList.add(categoryMapper.toCategoryDTO(category));
        }
        return categoryDTOList;
    }

    @Override
    public ApiResponse deleteCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);
        category.setDelete(true);
        Category saved = categoryRepository.save(category);
        CategoryResponseDTO categoryDTO = categoryMapper.toCategoryDTO(saved);
        return new ApiResponse(true, "success", HttpStatus.OK, "Category has been deleted: " + categoryDTO);
    }

    @Override
    public ApiResponse updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = getCategoryById(categoryId);
        Category updateEntity = categoryMapper.toUpdateEntity(category, categoryDTO);
        Category saved = categoryRepository.save(updateEntity);
        CategoryResponseDTO categoryDTOSaved = categoryMapper.toCategoryDTO(saved);
        return new ApiResponse(true, "success", HttpStatus.OK, "Category has been updated: " + categoryDTOSaved);
    }

    @Override
    public ApiResponse getAllRemovedCategories() {
        List<Category> categoryList = categoryRepository.findAllByDeleteIsTrue();
        List<CategoryResponseDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryDTOList.add(categoryMapper.toCategoryDTO(category));
        }
        return new ApiResponse(true, "success", HttpStatus.OK, categoryDTOList);
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findByIdAndDeleteFalse(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(MessageKey.CATEGORY_NOT_FOUND));
    }
}
