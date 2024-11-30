package uz.ilmnajot.post_article.controller.view;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.entity.Article;
import uz.ilmnajot.post_article.entity.Topic;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.payload.ArticleDTO;
import uz.ilmnajot.post_article.payload.ArticleResponseDTO;
import uz.ilmnajot.post_article.payload.TopicResponseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.ArticleRepository;
import uz.ilmnajot.post_article.repository.TopicRepository;
import uz.ilmnajot.post_article.service.interfaces.ArticleService;
import uz.ilmnajot.post_article.service.interfaces.TopicService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/")
public class ViewArticleController {

    private final ArticleService articleService;
    private final TopicService topicService;
    private final ArticleRepository articleRepository;
    private final TopicRepository topicRepository;


//    @PreAuthorize("hasAnyAuthority('USER','ADMIN','AUTHOR')")
    @GetMapping("/add-article")
    public String showAddArticlePage(Model model) {
        List<TopicResponseDTO> allTopics = topicService.getAllTopics();
        model.addAttribute("article", new ArticleDTO());
        model.addAttribute("topics", allTopics);
        return "add-article";
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN','AUTHOR')")
    @PostMapping("/add-article")
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
        return "articles";
    }

//    @PreAuthorize("hasAnyAuthority('USER','ADMIN','AUTHOR')")
    @GetMapping("/articles")
    public String getAllArticles(Model model) {
        ApiResponse articles = articleService.getAllArticles();// Replace with actual service/repository call
        model.addAttribute("articles", articles);
        return "article-list"; //changed from articles to article-list
    }

//    @PreAuthorize("hasAnyAuthority('USER','ADMIN','AUTHOR')")
    @GetMapping("/topics/{topicId}/articles")
    public String viewArticle(@PathVariable Long topicId, Model model) {
        TopicResponseDTO topic = topicService.getTopicByID(topicId);
        List<ArticleResponseDTO> articles = articleService.getArticlesByTopicId(topicId);
        model.addAttribute("topic", topic);
        model.addAttribute("articles", articles);
        return "article-list"; //changed from articles to article-list
    }


//    @PreAuthorize("hasAnyAuthority('USER','ADMIN','AUTHOR')")
    @GetMapping("/topics/{topicId}/articles/{articleId}")
    public String getArticleDetails(@PathVariable Long topicId, @PathVariable Long articleId, Model model) {
        ArticleResponseDTO article = articleService(topicId, articleId); // Replace with service or repo call
        model.addAttribute("article", article);
        return "article-detail";
    }

    private ArticleResponseDTO articleService(Long topicId, Long articleId) {
        Article article = articleRepository.findByIdAndDeleteFalse(articleId).orElseThrow(
                () -> new ResourceNotFoundException("article not found"));
        Topic topic = topicRepository.findByIdAndDeleteFalse(topicId).orElseThrow(
                () -> new ResourceNotFoundException("topic not found"));
        return new ArticleResponseDTO(article.getId(), "Title", "content", topic.getId());
    }

}
