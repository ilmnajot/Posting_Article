package uz.ilmnajot.post_article.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.interfaces.RatingService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/rating/{userId}/{articleId}")
    public HttpEntity<ApiResponse> addRating(@PathVariable(name = "userId") Long userId,
                                             @PathVariable(name="articleId") Long articleId,
                                             @RequestParam("isLike") boolean isLike
                                             ){
        ApiResponse apiResponse = ratingService.addRating(userId, articleId, isLike);
        return ResponseEntity.ok(apiResponse);
    }
}

