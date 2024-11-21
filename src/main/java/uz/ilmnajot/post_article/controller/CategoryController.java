package uz.ilmnajot.post_article.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.payload.CategoryDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.CategoryService;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/addCategory")
    public HttpEntity<ApiResponse> addCategory(@RequestBody CategoryDTO categoryDTO) {
        ApiResponse apiResponse = categoryService.addCategory(categoryDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getCategory/{categoryId}")
    public HttpEntity<ApiResponse> getCategory(@PathVariable("categoryId") Long categoryId) {
        ApiResponse category = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/getAllCategories")
    public HttpEntity<ApiResponse> getAllCategories() {
        ApiResponse allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @GetMapping("/getAllExistsCategories")
    public HttpEntity<ApiResponse> getAllExistsCategories() {
        ApiResponse allExistsCategories = categoryService.getAllExistsCategories();
        return ResponseEntity.ok(allExistsCategories);
    }

    @GetMapping("/getAllRemovedCategories")
    public HttpEntity<ApiResponse> getAllRemovedCategories() {
        ApiResponse allExistsCategories = categoryService.getAllRemovedCategories();
        return ResponseEntity.ok(allExistsCategories);
    }

    @DeleteMapping("/deleteCategory/{categoryId}")
    public HttpEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        ApiResponse apiResponse = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/updateCategory/{category}")
    public HttpEntity<ApiResponse> updateCategory(@PathVariable("category") Long categoryId, @RequestBody CategoryDTO categoryDTO) {
        ApiResponse apiResponse = categoryService.updateCategory(categoryId, categoryDTO);
        return ResponseEntity.ok(apiResponse);
    }
}
