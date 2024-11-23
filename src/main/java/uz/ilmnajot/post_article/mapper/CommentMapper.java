package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.Comment;
import uz.ilmnajot.post_article.payload.CommentDTO;

public interface CommentMapper {

    Comment toUpdateCommentEntity(CommentDTO commentDTO, Comment comment);

    CommentDTO toCommentDTO(Comment comment);

    Comment toCommentEntity(CommentDTO commentDTO);
}
