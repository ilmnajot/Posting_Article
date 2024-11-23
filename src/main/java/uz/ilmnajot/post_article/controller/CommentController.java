package uz.ilmnajot.post_article.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.payload.CommentDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/addComment")
    public HttpEntity<ApiResponse> addComment(@RequestBody CommentDTO commentDTO) {
        ApiResponse apiResponse = commentService.addComment(commentDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getComment/{commentId}")
    public HttpEntity<ApiResponse> getComment(@PathVariable Long commentId) {
        ApiResponse apiResponse = commentService.findCommentById(commentId);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/getAllComments")
    public HttpEntity<ApiResponse> getAllComments() {
        ApiResponse allComments = commentService.getAllComments();
        return ResponseEntity.ok(allComments);
    }

    @GetMapping("/getAllDeletedComments")
    public HttpEntity<ApiResponse> getAllDeletedComments() {
        ApiResponse comments = commentService.getAllDeletedComments();
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/deleteComment/{commentId}")
    public HttpEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {
        ApiResponse apiResponse = commentService.deleteComment(commentId);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/updateComment/{userId}/{commentId}")
    public HttpEntity<ApiResponse> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        ApiResponse apiResponse = commentService.updateComment(commentId, commentDTO);
        return ResponseEntity.ok(apiResponse);
    }
}
