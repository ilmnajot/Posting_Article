package uz.ilmnajot.post_article.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.ilmnajot.post_article.payload.ArticleDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.ArticleService;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {

    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/addArticle")
    public HttpEntity<ApiResponse> addArticle(@RequestBody ArticleDTO articleDTO) {
        ApiResponse apiResponse = articleService.addArticle(articleDTO);
        return ResponseEntity.ok(apiResponse);
    }

}
