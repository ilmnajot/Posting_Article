package uz.ilmnajot.post_article.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.entity.Article;
import uz.ilmnajot.post_article.entity.Comment;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.payload.CommentDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.ArticleRepository;
import uz.ilmnajot.post_article.repository.CommentRepository;
import uz.ilmnajot.post_article.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ArticleRepository articleRepository;

    public CommentServiceImpl(CommentRepository commentRepository, UserRepository userRepository, ArticleRepository articleRepository) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.articleRepository = articleRepository;
    }

    @Override
    public ApiResponse addComment(CommentDTO commentDTO) {
        User user = userRepository.findByIdAndDeleteFalse(commentDTO.getUserId()).orElseThrow(()
                -> new ResourceNotFoundException("Article not found with id: " + commentDTO.getArticleId(), HttpStatus.NOT_FOUND));
        Article article = articleRepository.findByIdAndDeleteFalse(commentDTO.getArticleId()).orElseThrow(
                () -> new ResourceNotFoundException("Article not found with id: " + commentDTO.getArticleId(), HttpStatus.NOT_FOUND));
        Comment comment = new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setArticle(article);
        comment.setUser(user);
        Comment saved = commentRepository.save(comment);
        return new ApiResponse(true, "success", HttpStatus.CREATED, saved);
    }

    @Override
    public ApiResponse findCommentById(Long commentId) {
        Comment comment = getCommentById(commentId);
        return new ApiResponse(true, "success", HttpStatus.OK, comment);
    }

    @Override
    public ApiResponse getAllComments() {
        List<Comment> commentList = commentRepository.findAll();
        return new ApiResponse(true, "success", HttpStatus.OK, commentList);
    }

    @Override
    public ApiResponse getAllDeletedComments() {
        List<Comment> commentList = commentRepository.findAllByDeleteIsFalse();
        return new ApiResponse(true, "success", HttpStatus.OK, commentList);
    }

    @Override
    public ApiResponse deleteComment(Long commentId) {
        Comment comment = getCommentById(commentId);
        comment.setDelete(true);
        commentRepository.save(comment);
        return new ApiResponse(true, "Success", HttpStatus.OK, "Comment has been deleted");
    }

    @Override
    public ApiResponse updateComment(Long commentId, CommentDTO commentDTO) {
        Comment comment = getCommentById(commentId);

        return null;
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findByIdAndDeleteFalse(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment not found with id: " + commentId, HttpStatus.NOT_FOUND));
    }
}
