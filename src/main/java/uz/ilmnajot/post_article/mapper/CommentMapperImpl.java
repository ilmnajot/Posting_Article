package uz.ilmnajot.post_article.mapper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Article;
import uz.ilmnajot.post_article.entity.Comment;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.payload.CommentDTO;
import uz.ilmnajot.post_article.repository.ArticleRepository;
import uz.ilmnajot.post_article.repository.UserRepository;

@Component
public class CommentMapperImpl implements CommentMapper {

    private final ArticleRepository articleRepository;
    private final UserRepository userRepository;

    public CommentMapperImpl(ArticleRepository articleRepository, UserRepository userRepository) {
        this.articleRepository = articleRepository;
        this.userRepository = userRepository;
    }

    public Comment toCommentEntity(CommentDTO commentDTO) {
        Article article = articleRepository.findByIdAndDeleteFalse(commentDTO.getArticleId()).orElseThrow(()
                -> new ResourceNotFoundException("Comment not found"));
        User user = userRepository.findByIdAndDeleteFalse(commentDTO.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));
        Comment comment = new Comment();
        comment.setComment(commentDTO.getComment());
        comment.setArticle(article);
        comment.setUser(user);
        return comment;
    }

    public CommentDTO toCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentDTO.getId());
        commentDTO.setComment(comment.getComment());
        commentDTO.setArticleId(comment.getArticle().getId());
        commentDTO.setUserId(comment.getUser().getId());
        return commentDTO;
    }

    public Comment toUpdateCommentEntity(CommentDTO commentDTO, Comment comment) {

        Article article = articleRepository.findByIdAndDeleteFalse(commentDTO.getArticleId()).orElseThrow(()
                -> new ResourceNotFoundException("Comment not found"));
        User user = userRepository.findByIdAndDeleteFalse(commentDTO.getUserId()).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));

        if (commentDTO.getComment() != null) {
            comment.setComment(commentDTO.getComment());
        }
        if (commentDTO.getArticleId() != null) {
            comment.setArticle(article);
        }
        if (commentDTO.getUserId() != null) {
            comment.setUser(user);
        }
        return comment;
    }
}
