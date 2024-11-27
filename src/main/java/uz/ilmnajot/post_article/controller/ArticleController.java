package uz.ilmnajot.post_article.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.payload.ArticleDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.interfaces.ArticleService;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'AUTHOR')")
    @PostMapping("/addArticle")
    public HttpEntity<ApiResponse> addArticle(@RequestBody ArticleDTO articleDTO) {
        ApiResponse apiResponse = articleService.addArticle(articleDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER','AUTHOR')")
    @GetMapping("/getArticle/{articleId}")
    public HttpEntity<ApiResponse> getArticle(@PathVariable(name = "articleId") Long articleId) {
        ApiResponse apiResponse = articleService.getArticle(articleId);
        return ResponseEntity.ok(apiResponse);
    }
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER','AUTHOR')")
    @GetMapping("/getAllArticles")
    public HttpEntity<ApiResponse> getAllArticles() {
        ApiResponse apiResponse = articleService.getAllArticles();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER','AUTHOR')")
    @GetMapping("/getActiveArticles")
    public HttpEntity<ApiResponse> getActiveArticles() {
        ApiResponse apiResponse = articleService.getActiveArticle();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER','AUTHOR')")
    @GetMapping("/getDeletedArticles")
    public HttpEntity<ApiResponse> getDeletedArticles() {
        ApiResponse apiResponse = articleService.getAllDeletedArticles();
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteArticle/{articleId}")
    public HttpEntity<ApiResponse> deleteArticle(@PathVariable(name = "articleId") Long articleId) {
        ApiResponse apiResponse = articleService.deleteArticle(articleId);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateArticle/{articleId}")
    public HttpEntity<ApiResponse> updateArticle(@PathVariable(name = "articleId") Long articleId, @RequestBody ArticleDTO articleDTO) {
        ApiResponse apiResponse = articleService.updateArticle(articleId, articleDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getArticlesByTopicId/{topicId}")
    public HttpEntity<ApiResponse> getArticlesByTopicId(@PathVariable(name = "topicId") Long topicId) {
        articleService.getArticlesByTopicId(topicId);
    }

}
