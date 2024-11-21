package uz.ilmnajot.post_article.service;

import uz.ilmnajot.post_article.payload.CommentDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

public interface CommentService {
    ApiResponse addComment(CommentDTO commentDTO);
}
