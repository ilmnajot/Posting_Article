package uz.ilmnajot.post_article.mapper;

import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.News;
import uz.ilmnajot.post_article.payload.NewsDTO;

@Component
public class NewsMapperImpl implements NewsMapper {

    public News toNewsEntity(NewsDTO newsDTO, String imageURL) {
        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setImageURL(imageURL);
        return news;
    }

    public NewsDTO toNewsDTO(News news) {
        NewsDTO newsDTO = new NewsDTO();
        newsDTO.setId(news.getId());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setContent(news.getContent());
        newsDTO.setImageURL(news.getImageURL());
        newsDTO.setCreatedAt(news.getCreatedAt());
        newsDTO.setCreatedBy(news.getCreatedBy());
        return newsDTO;
    }

    public News toUpdateNewsEntity(News news, NewsDTO newsDTO) {
        if (newsDTO.getTitle() != null) {
            news.setTitle(newsDTO.getTitle());
        }
        if (newsDTO.getContent() != null) {
            news.setContent(newsDTO.getContent());
        }
        if (newsDTO.getImageURL() != null) {
            news.setImageURL(newsDTO.getImageURL());
        }
        return news;
    }
}
