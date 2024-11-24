package uz.ilmnajot.post_article.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.payload.ArticleDTO;
import uz.ilmnajot.post_article.payload.CategoryDTO;
import uz.ilmnajot.post_article.payload.CategoryResponseDTO;
import uz.ilmnajot.post_article.payload.UserDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.auth.AuthService;
import uz.ilmnajot.post_article.service.interfaces.ArticleService;
import uz.ilmnajot.post_article.service.interfaces.CategoryService;

@RequestMapping("/")
@Controller
public class ViewController {

    private final AuthService authService;
    private final CategoryService categoryService;
    private final ArticleService articleService;

    public ViewController(AuthService authService, CategoryService categoryService, ArticleService articleService) {
        this.authService = authService;
        this.categoryService = categoryService;
        this.articleService = articleService;
    }

    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/sign-up")
    public String showSignUpPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpUser(@ModelAttribute UserDTO userDTO, Model model) {
        ApiResponse user = authService.signUp(userDTO);
        if (user.isSuccess()){
            model.addAttribute("user", user);
            return "redirect:/email-verify?email=" + userDTO.getEmail();
        }
        model.addAttribute("error", user.getMessage());
        model.addAttribute("user", userDTO);
        return "sign-up";
    }
    @GetMapping("/email-verify")
    public String showVerificationPage(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "email-verify";
    }

    @PostMapping("/verify-email")
    public String verifyEmail(@RequestParam String email, @RequestParam String emailCode, Model model) {
        ApiResponse response = authService.verifyEmail(email, emailCode);
        if (response.isSuccess()) {
            return "redirect:/verification-success";
        }
        model.addAttribute("email", email);
        model.addAttribute("error", response.getMessage());
        return "email-verify";
    }

    @GetMapping("/verification-success")
    public String showVerificationSuccessPage() {
        return "verification-success";
    }

//************************CATEGORY********************//

//    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    @GetMapping("/addCategory")
    public String showCategoryPage(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "category";
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    @PostMapping("/addCategory")
    public String addCategory(@ModelAttribute("category") @Valid CategoryDTO categoryDTO,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "category"; // Show errors on the same form
        }
        try {
            categoryService.addCategory(categoryDTO);
            model.addAttribute("success", "Category added successfully!");
            model.addAttribute("category", new CategoryDTO()); // Clear the form
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/category-list?success";
    }

    @GetMapping("/category-list")
    public String getCategories(Model model) {
        ApiResponse categories = categoryService.getAllExistsCategories();
        model.addAttribute("categories", categories);
        return "category-list";
    }


    //************************ARTICLE********************//
    @GetMapping("/addArticle")
    public String showAddArticlePage(Model model) {
        model.addAttribute("article", new ArticleDTO());
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add-article";
    }

    @PostMapping("/addArticle")
    public String addArticle(@ModelAttribute("article") @Valid ArticleDTO articleDTO,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllCategories());
            return "add-article";
        }
        try {
            articleService.addArticle(articleDTO);
            model.addAttribute("success", "Article added successfully!");
            model.addAttribute("article", new ArticleDTO()); // Clear form
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("categories", categoryService.getAllCategories());
        return "add-article";
    }




}
