package uz.ilmnajot.post_article.mapper;

import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.News;
import uz.ilmnajot.post_article.payload.NewsDTO;
import uz.ilmnajot.post_article.payload.NewsRequestDTO;
import uz.ilmnajot.post_article.payload.NewsResponseDTO;

@Component
public class NewsMapperImpl implements NewsMapper {

    public News toNewsEntity(NewsRequestDTO newsDTO, String imageURL) {
        News news = new News();
        news.setTitle(newsDTO.getTitle());
        news.setContent(newsDTO.getContent());
        news.setImageURL(imageURL);
        return news;
    }

    public NewsResponseDTO toNewsDTO(News news) {
        NewsResponseDTO newsDTO = new NewsResponseDTO();
        newsDTO.setId(news.getId());
        newsDTO.setTitle(news.getTitle());
        newsDTO.setContent(news.getContent());
        newsDTO.setImageURL(news.getImageURL());
        newsDTO.setCreatedAt(news.getCreatedAt());
        newsDTO.setCreatedBy(news.getCreatedBy());
        return newsDTO;
    }

    public News toUpdateNewsEntity(News news, NewsRequestDTO newsDTO) {
        if (newsDTO.getTitle() != null) {
            news.setTitle(newsDTO.getTitle());
        }
        if (newsDTO.getContent() != null) {
            news.setContent(newsDTO.getContent());
        }
//        if (newsDTO.getImageURL() != null) {
//            news.setImageURL(newsDTO.getImageURL());
//        }
        return news;
    }
}
