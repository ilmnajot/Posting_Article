package uz.ilmnajot.post_article.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.payload.TopicRequestDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.TopicService;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

    private final TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }


//    @PreAuthorize("hasAnyAuthority('ADMIN','AUTHOR')")
    @PostMapping("/addTopic")
    public HttpEntity<ApiResponse> addTopic(@RequestBody TopicRequestDTO topicRequestDTO) {
        ApiResponse apiResponse = topicService.addTopic(topicRequestDTO);
        return ResponseEntity.ok(apiResponse);
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/getTopic/{topicId}")
    public HttpEntity<ApiResponse> getCategory(@PathVariable("topicId") Long topicId) {
        ApiResponse category = topicService.getTopic(topicId);
        return ResponseEntity.ok(category);
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/getAllTopics")
    public HttpEntity<ApiResponse> getAllTopics() {
        ApiResponse allCategories = topicService.getAllTopics();
        return ResponseEntity.ok(allCategories);
    }
//
//    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
//    @GetMapping("/getAllExistsCategories")
//    public HttpEntity<?> getAllExistsCategories() {
//        List<CategoryResponseDTO> allExistsCategories = topicService.getAllExistsCategories();
//        return ResponseEntity.ok(List.of(allExistsCategories));
//    }
//
//    @PreAuthorize("hasAuthority('ADMIN')")
//    @GetMapping("/getAllRemovedCategories")
//    public HttpEntity<ApiResponse> getAllRemovedCategories() {
//        ApiResponse allExistsCategories = topicService.getAllRemovedCategories();
//        return ResponseEntity.ok(allExistsCategories);
//    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteTopic/{topicId}")
    public HttpEntity<ApiResponse> deleteTopic(@PathVariable("topicId") Long topicId) {
        ApiResponse apiResponse = topicService.deleteTopic(topicId);
        return ResponseEntity.ok(apiResponse);
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateTopic/{topicId}")
    public HttpEntity<ApiResponse> updateTopic(@PathVariable("topicId") Long topicId, @RequestBody TopicRequestDTO topicRequestDTO) {
        ApiResponse apiResponse = topicService.updateTopic(topicId, topicRequestDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getTopicsByCategoryId/{categoryId}")
    public HttpEntity<ApiResponse> getAllTopicsByCategory(@PathVariable("categoryId")Long categoryId) {
        topicService.getTopicsByCategoryId(categoryId);

    }
}
