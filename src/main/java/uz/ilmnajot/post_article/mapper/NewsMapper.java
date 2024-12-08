package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.News;
import uz.ilmnajot.post_article.payload.NewsDTO;
import uz.ilmnajot.post_article.payload.NewsRequestDTO;
import uz.ilmnajot.post_article.payload.NewsResponseDTO;

public interface NewsMapper {

    News toNewsEntity(NewsRequestDTO newsDTO, String imageURL);
    NewsResponseDTO toNewsDTO(News news);
    News toUpdateNewsEntity(News news, NewsRequestDTO newsDTO);
}
