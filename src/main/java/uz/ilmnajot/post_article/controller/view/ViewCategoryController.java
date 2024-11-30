package uz.ilmnajot.post_article.controller.view;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.payload.CategoryDTO;
import uz.ilmnajot.post_article.payload.CategoryResponseDTO;
import uz.ilmnajot.post_article.service.interfaces.CategoryService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ViewCategoryController {

    private final CategoryService categoryService;


//    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    @GetMapping("/addCategory")
    public String showCategoryPage(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "category";
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute("category") @Valid CategoryDTO categoryDTO,
                              @RequestParam("image") MultipartFile image,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "category"; // Show errors on the same form
        }
        try {

            categoryService.addCategory(categoryDTO, image);
            model.addAttribute("success", "Category added successfully!");
            model.addAttribute("category", new CategoryDTO()); // Clear the form
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/category-list?success";
    }

//    @PreAuthorize("hasAnyAuthority('USER', 'AUTHOR','ADMIN')")
    @GetMapping("/category-list")
    public String getCategories(Model model) {
        List<CategoryResponseDTO> categories = categoryService.getAllExistsCategories();
        model.addAttribute("categories", categories);
        return "category-list";
    }

}
