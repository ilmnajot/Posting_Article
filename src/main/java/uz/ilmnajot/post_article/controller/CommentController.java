package uz.ilmnajot.post_article.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.payload.CommentDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.interfaces.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @PostMapping("/addComment")
    public HttpEntity<ApiResponse> addComment(@RequestBody CommentDTO commentDTO) {
        ApiResponse apiResponse = commentService.addComment(commentDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/getComment/{commentId}")
    public HttpEntity<ApiResponse> getComment(@PathVariable Long commentId) {
        ApiResponse apiResponse = commentService.findCommentById(commentId);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllComments")
    public HttpEntity<ApiResponse> getAllComments() {
        ApiResponse allComments = commentService.getAllComments();
        return ResponseEntity.ok(allComments);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/getAllDeletedComments")
    public HttpEntity<ApiResponse> getAllDeletedComments() {
        ApiResponse comments = commentService.getAllDeletedComments();
        return ResponseEntity.ok(comments);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteComment/{commentId}")
    public HttpEntity<ApiResponse> deleteComment(@PathVariable Long commentId) {
        ApiResponse apiResponse = commentService.deleteComment(commentId);
        return ResponseEntity.ok(apiResponse);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/updateComment/{userId}/{commentId}")
    public HttpEntity<ApiResponse> updateComment(@PathVariable Long commentId, @RequestBody CommentDTO commentDTO) {
        ApiResponse apiResponse = commentService.updateComment(commentId, commentDTO);
        return ResponseEntity.ok(apiResponse);
    }
}
