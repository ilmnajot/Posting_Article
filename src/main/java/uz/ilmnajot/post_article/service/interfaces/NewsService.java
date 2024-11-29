package uz.ilmnajot.post_article.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.payload.NewsDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

import java.util.List;

public interface NewsService {
    ApiResponse getAllNews();
    List<NewsDTO> getAllNewsList();

    ApiResponse getNewsById(Long id);

    NewsDTO getNews(Long id);

    NewsDTO createNews(NewsDTO newsDTO, MultipartFile image);

    ApiResponse updateNews(Long id, NewsDTO newsDetails);

    ApiResponse deleteNews(Long id);
}
