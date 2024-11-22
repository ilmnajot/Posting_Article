package uz.ilmnajot.post_article.service;

import uz.ilmnajot.post_article.payload.CommentDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

public interface CommentService {
    ApiResponse addComment(CommentDTO commentDTO);

    ApiResponse findCommentById(Long commentId);

    ApiResponse getAllComments();

    ApiResponse getAllDeletedComments();

    ApiResponse deleteComment(Long commentId);

    ApiResponse updateComment(Long commentId, CommentDTO commentDTO);
}
