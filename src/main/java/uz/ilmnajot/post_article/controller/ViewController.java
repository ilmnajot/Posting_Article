package uz.ilmnajot.post_article.controller;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.entity.Article;
import uz.ilmnajot.post_article.entity.Topic;
import uz.ilmnajot.post_article.exception.AlreadyExistsException;
import uz.ilmnajot.post_article.payload.*;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.CategoryRepository;
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
    private final CategoryRepository categoryRepository;

    public ViewController(AuthService authService, CategoryService categoryService, ArticleService articleService, TopicService topicService, CategoryRepository categoryRepository) {
        this.authService = authService;
        this.categoryService = categoryService;
        this.articleService = articleService;
        this.topicService = topicService;
        this.categoryRepository = categoryRepository;
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
    @GetMapping("/add-topic")
    public String showAddTopicPage(Model model) {
        List<CategoryResponseDTO> categories = categoryService.getAllExistsCategories();
        model.addAttribute("topic", new TopicRequestDTO());
        model.addAttribute("categories", categories);
        return "add-topic";
    }

    //    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    @PostMapping("/add-topic")
    public String addTopic(@ModelAttribute("topic") @Valid TopicRequestDTO topicRequestDTO,
                           BindingResult result,
                           Model model) {
        if (result.hasErrors()) {
            model.addAttribute("topics", topicService.getAllTopics()); // Include existing topics if needed
            return "add-topic"; // Show validation errors on the same form
        }
        try {
            topicService.addTopic(topicRequestDTO);
            model.addAttribute("success", "Topic added successfully!");
            return "redirect:/topic-list"; // Redirect to avoid duplicate submissions
        } catch (AlreadyExistsException e) {
            model.addAttribute("error", "Topic with this title already exists.");
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred. Please try again later.");
        }
        model.addAttribute("topics", topicService.getAllTopics()); // Reload topics list in case of errors
        return "add-topic"; // Return to the same form with error messages
    }


    @GetMapping("/topic-list")
    public String listTopics(Model model) {
        List<TopicResponseDTO> topics = topicService.getAllTopics();
        if (topics == null || topics.isEmpty()) {
            model.addAttribute("message", "No topics found.");
        }
        model.addAttribute("topics", topics);
        return "topic-list";
    }


    //************************ARTICLE********************//
    @GetMapping("/addArticle")
    public String showAddArticlePage(Model model) {
        List<TopicResponseDTO> allTopics = topicService.getAllTopics();
        model.addAttribute("article", new ArticleDTO());
        model.addAttribute("topics", allTopics);
        return "add-article";
    }

    @PostMapping("/addArticle")
    public String addArticle(@ModelAttribute("article") @Valid ArticleDTO articleDTO,
                             BindingResult result,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("topics", topicService.getAllTopics());
            return "add-article";
        }
        try {
            articleService.addArticle(articleDTO);
            model.addAttribute("success", "Article added successfully!");
            model.addAttribute("article", new ArticleDTO()); // Clear form
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        model.addAttribute("topics", topicService.getAllTopics());
        return "topic-list";
    }

    @GetMapping("/categories/{categoryId}/topics")
    public String getTopicsByCategoryId(@PathVariable(name = "categoryId") Long categoryId, Model model) {
        List<TopicResponseDTO> responseDTOList = topicService.getTopicsByCategoryId(categoryId);
        model.addAttribute("topics", responseDTOList);
        return "topic-list";
    }

    @GetMapping("/categories/{categoryId}/articles")
    public String viewTopicsByCategory(@PathVariable Long categoryId, Model model) {
        CategoryResponseDTO category = categoryService.getCategoryByID(categoryId);
        model.addAttribute("categoryName", category.getName());
        model.addAttribute("topics", topicService.getTopicsByCategoryId(categoryId));
        return "topic-list"; // Points to topics.html
    }

    @GetMapping("/topics/{topicId}/article")
    public String viewArticle(@PathVariable Long topicId, Model model) {
        TopicResponseDTO topic = topicService.getTopicByID(topicId);
        model.addAttribute("topic", topic);
        return "article-detail";
    }




}
