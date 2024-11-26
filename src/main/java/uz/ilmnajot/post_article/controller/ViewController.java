package uz.ilmnajot.post_article.controller;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.payload.*;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.TopicService;
import uz.ilmnajot.post_article.service.auth.AuthService;
import uz.ilmnajot.post_article.service.interfaces.ArticleService;
import uz.ilmnajot.post_article.service.interfaces.CategoryService;

import java.util.List;

@RequestMapping("/")
@Controller
public class ViewController {

    private final AuthService authService;
    private final CategoryService categoryService;
    private final ArticleService articleService;
    private final TopicService topicService;

    public ViewController(AuthService authService, CategoryService categoryService, ArticleService articleService, TopicService topicService) {
        this.authService = authService;
        this.categoryService = categoryService;
        this.articleService = articleService;
        this.topicService = topicService;
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
        if (user.isSuccess()) {
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

    @GetMapping("/category-list")
    public String getCategories(Model model) {
        List<CategoryResponseDTO> categories = categoryService.getAllExistsCategories();
        model.addAttribute("categories", categories);
        return "category-list";
    }


    //************************TOPIC********************//

    //    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    @GetMapping("/addTopic")
    public String showTopicPage(Model model) {
        model.addAttribute("topic", new TopicRequestDTO());
        return "category";
    }

    //    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    @PostMapping("/addTopic")
    public String addTopic(@ModelAttribute("topic") @Valid TopicRequestDTO topicRequestDTO,
                              BindingResult result,
                              Model model) {
        if (result.hasErrors()) {
            return "topic"; // Show errors on the same form
        }
        try {
            ApiResponse topic = topicService.addTopic(topicRequestDTO);
            model.addAttribute("success", "Topic added successfully!");
            model.addAttribute("topic", topic); // Clear the form
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/topic-list?success";
    }

    @GetMapping("/topic-list")
    public String getTopics(Model model) {
        List<TopicResponseDTO> allTopics = topicService.getAllTopics();
        model.addAttribute("topics", allTopics);
        return "topic-list";
    }



    //************************ARTICLE********************//
    @GetMapping("/addArticle")
    public String showAddArticlePage(Model model) {
        model.addAttribute("article", new ArticleDTO());
        model.addAttribute("categories", categoryService.getAllExistsCategories());
        return "add-article";
    }

    @PostMapping("/addArticle")
    public String addArticle(@ModelAttribute("article") @Valid ArticleDTO articleDTO,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAllExistsCategories());
            return "add-article";
        }
        try {
            articleService.addArticle(articleDTO);
            model.addAttribute("success", "Article added successfully!");
            model.addAttribute("article", new ArticleDTO()); // Clear form
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("categories", categoryService.getAllExistsCategories());
        return "category-list";
    }



}
