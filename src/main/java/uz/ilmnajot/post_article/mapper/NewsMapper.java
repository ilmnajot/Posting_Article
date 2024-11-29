package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.News;
import uz.ilmnajot.post_article.payload.NewsDTO;

public interface NewsMapper {

    News toNewsEntity(NewsDTO newsDTO, String imageURL);
    NewsDTO toNewsDTO(News news);
    News toUpdateNewsEntity(News news, NewsDTO newsDTO);
}
