package uz.ilmnajot.post_article.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.entity.Category;
import uz.ilmnajot.post_article.enums.ResponseMessage;
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

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Value("${upload.dir}")
    private String imageDirectory;

    public CategoryServiceImpl(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }


    @Override
    public ApiResponse addCategory(String name, String description, MultipartFile image) {
        logger.info("Attempting to add category with name {}", name);
        Optional<Category> optionalCategory = categoryRepository.findByNameAndDeleteFalse(name);
        if (optionalCategory.isPresent()) {
            logger.warn("Category with name {} already exists", name);
            throw new AlreadyExistsException("Category already exists");
        }
        String addedImage = addImage(image);
        logger.debug("Image successfully uploaded to the database: {}", addedImage);
        Category categoryEntity = categoryMapper.toCategoryEntity(name, description, addedImage);
        Category saved = categoryRepository.save(categoryEntity);
        logger.info("Category '{}' successfully saved", name);
        CategoryResponseDTO mapperCategoryDTO = categoryMapper.toCategoryDTO(saved);
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.CREATED, mapperCategoryDTO);
    }

    private String addImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            logger.warn("No image provided for the category.");
            return null;
        }
        try {
            logger.debug("Uploading image: {}", image.getOriginalFilename());
            String imageFileName = image.getOriginalFilename();
            if (imageFileName == null || imageFileName.trim().isEmpty()) {
                logger.error("Invalid image with provided");
                throw new ResourceNotFoundException("Image name is invalid");
            }
            String replacedAll = imageFileName.replaceAll("[^a-zA-Z0-9._-]", "_");
            String fileName = UUID.randomUUID() + "_" + replacedAll;
            Path imagePath = Paths.get(imageDirectory, fileName);

            logger.debug("saving image to the path: {}", imagePath);

            Files.createDirectories(imagePath.getParent()); // Create directories if not exists
            Files.write(imagePath, image.getBytes()); // Save the image
            logger.info("Image successfully saved with name: {}", fileName);
            return "/images/" + fileName;
        } catch (Exception e) {
            logger.error("Failed to save image: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }

    }

    @Override
    public ApiResponse getCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);
        CategoryResponseDTO categoryDTO = categoryMapper.toCategoryDTO(category);
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.OK, categoryDTO);
    }

    @Override
    public CategoryResponseDTO getCategoryByID(Long categoryId) {
        Category category = getCategoryById(categoryId);
        return categoryMapper.toCategoryDTO(category);
    }

    @Override
    public ApiResponse getAllCategories() {
        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryDTOList.add(categoryMapper.toCategoryDTO(category));
        }
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.OK, categoryDTOList);
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
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.OK, "Category has been deleted: " + categoryDTO);
    }

    @Override
    public ApiResponse updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Category category = getCategoryById(categoryId);
        Category updateEntity = categoryMapper.toUpdateEntity(category, categoryDTO);
        Category saved = categoryRepository.save(updateEntity);
        CategoryResponseDTO categoryDTOSaved = categoryMapper.toCategoryDTO(saved);
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.OK, "Category has been updated: " + categoryDTOSaved);
    }

    @Override
    public ApiResponse getAllRemovedCategories() {
        List<Category> categoryList = categoryRepository.findAllByDeleteIsTrue();
        List<CategoryResponseDTO> categoryDTOList = new ArrayList<>();
        for (Category category : categoryList) {
            categoryDTOList.add(categoryMapper.toCategoryDTO(category));
        }
        return new ApiResponse(true, ResponseMessage.SUCCESS.getMessage(), HttpStatus.OK, categoryDTOList);
    }

    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findByIdAndDeleteFalse(categoryId).orElseThrow(
                () -> new ResourceNotFoundException(MessageKey.CATEGORY_NOT_FOUND));
    }
}
