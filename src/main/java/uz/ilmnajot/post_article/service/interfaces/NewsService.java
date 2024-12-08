package uz.ilmnajot.post_article.service.interfaces;

import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.payload.NewsDTO;
import uz.ilmnajot.post_article.payload.NewsRequestDTO;
import uz.ilmnajot.post_article.payload.NewsResponseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

import java.util.List;

public interface NewsService {
    ApiResponse getAllNews();
    List<NewsResponseDTO> getAllNewsList();

    ApiResponse getNewsById(Long id);

    NewsResponseDTO getNews(Long id);

    NewsResponseDTO createNews(NewsRequestDTO newsDTO, MultipartFile image);

    ApiResponse updateNews(Long id, NewsRequestDTO newsDetails);

    ApiResponse deleteNews(Long id);
}
