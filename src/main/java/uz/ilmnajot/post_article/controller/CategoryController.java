package uz.ilmnajot.post_article.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
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

    @Operation(summary = "Add a category with an image file")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category has been added successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Invalid input")})
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/addCategory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public HttpEntity<ApiResponse> addCategory(@RequestParam(name = "name") String name,
                                               @RequestParam(name = "description") String description,
                                               @RequestParam("image") MultipartFile image) {
        ApiResponse apiResponse = categoryService.addCategory(name, description, image);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Get a category by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category retrieved successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/getCategory/{categoryId}")
    public HttpEntity<ApiResponse> getCategory(@PathVariable("categoryId") Long categoryId) {
        ApiResponse category = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(category);
    }

    @Operation(summary = "Get all categories")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/getAllCategories")
    public HttpEntity<ApiResponse> getAllCategories() {
        ApiResponse allCategories = categoryService.getAllCategories();
        return ResponseEntity.ok(allCategories);
    }

    @Operation(summary = "Get all exists categories")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/getAllExistsCategories")
    public HttpEntity<?> getAllExistsCategories() {
        List<CategoryResponseDTO> allExistsCategories = categoryService.getAllExistsCategories();
        return ResponseEntity.ok(List.of(allExistsCategories));
    }

    @Operation(summary = "Get all deleted categories")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Categories retrieved successfully")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllRemovedCategories")
    public HttpEntity<ApiResponse> getAllRemovedCategories() {
        ApiResponse allExistsCategories = categoryService.getAllRemovedCategories();
        return ResponseEntity.ok(allExistsCategories);
    }

    @Operation(summary = "Delete a category by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category deleted successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteCategory/{categoryId}")
    public HttpEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Long categoryId) {
        ApiResponse apiResponse = categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(apiResponse);
    }

    @Operation(summary = "Update an existing category")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Category updated successfully"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "404", description = "Category not found")
    })
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateCategory/{categoryId}")
    public HttpEntity<ApiResponse> updateCategory(@PathVariable("categoryId") Long categoryId, @RequestBody CategoryDTO categoryDTO) {
        ApiResponse apiResponse = categoryService.updateCategory(categoryId, categoryDTO);
        return ResponseEntity.ok(apiResponse);
    }
}
