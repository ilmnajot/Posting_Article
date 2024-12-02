package uz.ilmnajot.post_article.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.payload.CategoryDTO;
import uz.ilmnajot.post_article.payload.CategoryResponseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.interfaces.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','AUTHOR')")
    @PostMapping("/addCategory")
    public HttpEntity<ApiResponse> addCategory(@RequestBody CategoryDTO categoryDTO,
                                               @RequestParam("image") MultipartFile image) {
        ApiResponse apiResponse = categoryService.addCategory(categoryDTO,image);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/getCategory/{categoryId}")
    public HttpEntity<ApiResponse> getCategory(@PathVariable("categoryId") Long categoryId) {
        ApiResponse category = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(category);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/getAllCategories")
    public HttpEntity<ApiResponse> getAllCategories() {
        ApiResponse allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/getAllExistsCategories")
    public HttpEntity<?> getAllExistsCategories() {
        List<CategoryResponseDTO> allExistsCategories = categoryService.getAllExistsCategories();
        return ResponseEntity.ok(List.of(allExistsCategories));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllRemovedCategories")
    public HttpEntity<ApiResponse> getAllRemovedCategories() {
        ApiResponse allExistsCategories = categoryService.getAllRemovedCategories();
        return ResponseEntity.ok(allExistsCategories);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteCategory/{categoryId}")
    public HttpEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        ApiResponse apiResponse = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateCategory/{categoryId}")
    public HttpEntity<ApiResponse> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody CategoryDTO categoryDTO) {
        ApiResponse apiResponse = categoryService.updateCategory(categoryId, categoryDTO);
        return ResponseEntity.ok(apiResponse);
    }
}
