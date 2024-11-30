package uz.ilmnajot.post_article.controller.view;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.exception.AlreadyExistsException;
import uz.ilmnajot.post_article.payload.ArticleResponseDTO;
import uz.ilmnajot.post_article.payload.CategoryResponseDTO;
import uz.ilmnajot.post_article.payload.TopicRequestDTO;
import uz.ilmnajot.post_article.payload.TopicResponseDTO;
import uz.ilmnajot.post_article.service.interfaces.ArticleService;
import uz.ilmnajot.post_article.service.interfaces.CategoryService;
import uz.ilmnajot.post_article.service.interfaces.TopicService;

import java.util.List;
@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class ViewTopicController {

    private final TopicService topicService;
    private final CategoryService categoryService;
    private final ArticleService articleService;

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


//    @PreAuthorize("hasAnyAuthority('USER','ADMIN','AUTHOR')")
    @GetMapping("/topic-list")
    public String listTopics(Model model) {
        List<TopicResponseDTO> topics = topicService.getAllTopics();
        if (topics == null || topics.isEmpty()) {
            model.addAttribute("message", "No topics found.");
        }
        model.addAttribute("topics", topics);
        return "topic-list";
    }

//    @PreAuthorize("hasAnyAuthority('USER','ADMIN','AUTHOR')")
    @GetMapping("/categories/{categoryId}/articles")
    public String viewTopicsByCategory(@PathVariable Long categoryId, Model model) {
        CategoryResponseDTO category = categoryService.getCategoryByID(categoryId);
        model.addAttribute("categoryName", category.getName());
        model.addAttribute("topics", topicService.getTopicsByCategoryId(categoryId));
        return "topic-list"; // Points to topics.html
    }
//    @PreAuthorize("hasAnyAuthority('USER','ADMIN','AUTHOR')")
    @GetMapping("/categories/{categoryId}/topics")
    public String getTopicsByCategoryId(@PathVariable(name = "categoryId") Long categoryId, Model model) {
        List<TopicResponseDTO> responseDTOList = topicService.getTopicsByCategoryId(categoryId);
        model.addAttribute("topics", responseDTOList);
        return "topic-list";
    }
//
//    @GetMapping("/topics/{topicId}/articles")
//    public String getArticlesByTopicId(@PathVariable(name = "topicId") Long topicId, Model model) {
//        List<ArticleResponseDTO> articles = articleService.getArticlesByTopicId(topicId);
//        model.addAttribute("articles", articles);
//        return "article-detail";
//    }


}
