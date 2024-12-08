package uz.ilmnajot.post_article.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.payload.NewsDTO;
import uz.ilmnajot.post_article.payload.NewsRequestDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.interfaces.NewsService;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private final NewsService newsService;

    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    // Get all news
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping
    public HttpEntity<ApiResponse> getAllNews() {
        ApiResponse apiResponse = newsService.getAllNews();
        return ResponseEntity.ok(apiResponse);
    }

    // Get a single news by ID
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    @GetMapping("/get_news/{id}")
    public HttpEntity<ApiResponse> getNewsById(@PathVariable Long id) {
        ApiResponse apiResponse = newsService.getNewsById(id);
        return ResponseEntity.ok(apiResponse);
    }

    @ApiOperation(value = "Add News", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping(value = "/add_news", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createNews(@RequestPart("news") NewsRequestDTO newsDTO,
                                        @RequestPart("image") MultipartFile image) {
        return ResponseEntity.ok(newsService.createNews(newsDTO, image));
    }

    // Update an existing news item
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/update_news/{id}")
    public ResponseEntity<ApiResponse> updateNews(@PathVariable Long id, @RequestBody NewsRequestDTO newsDetails) {
        return ResponseEntity.ok(newsService.updateNews(id, newsDetails));
    }

    // Delete a news item
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/delete_news/{id}")
    public ResponseEntity<ApiResponse> deleteNews(@PathVariable Long id) {
        ApiResponse apiResponse = newsService.deleteNews(id);
        return ResponseEntity.ok(apiResponse);
    }


}
