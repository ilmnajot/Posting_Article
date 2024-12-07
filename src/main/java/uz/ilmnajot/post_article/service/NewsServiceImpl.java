package uz.ilmnajot.post_article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.entity.News;
import uz.ilmnajot.post_article.exception.AlreadyExistsException;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.mapper.NewsMapper;
import uz.ilmnajot.post_article.payload.NewsDTO;
import uz.ilmnajot.post_article.payload.NewsRequestDTO;
import uz.ilmnajot.post_article.payload.NewsResponseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.NewsRepository;
import uz.ilmnajot.post_article.service.interfaces.CategoryService;
import uz.ilmnajot.post_article.service.interfaces.NewsService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    @Value("${upload.dir}")
    private String imageDirectory;

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;
    private final CategoryService categoryService;

    @Override
    public ApiResponse getAllNews() {
        List<News> newsList = newsRepository.findAll();
        List<NewsResponseDTO> newsDTOList = newsList.stream().map(newsMapper::toNewsDTO).toList();
        return new ApiResponse(true, "success", HttpStatus.CREATED, newsDTOList);
    }

    @Override
    public List<NewsResponseDTO> getAllNewsList() {
        List<News> newsList = newsRepository.findAll();
        return newsList
                .stream()
                .map(newsMapper::toNewsDTO)
                .toList();
    }

    @Override
    public ApiResponse getNewsById(Long id) {
        News news = newsRepository.findByIdAndDeleteFalse(id).orElseThrow(
                () -> new ResourceNotFoundException("news not found"));
        NewsResponseDTO newsDTO = newsMapper.toNewsDTO(news);
        return new ApiResponse(true, "success", HttpStatus.OK, newsDTO);
    }

    @Override
    public NewsResponseDTO getNews(Long id) {
        News news = newsRepository.findByIdAndDeleteFalse(id).orElseThrow(
                () -> new ResourceNotFoundException("news not found"));
        return newsMapper.toNewsDTO(news);
    }

    @Override
    public NewsResponseDTO createNews(NewsRequestDTO newsDTO, MultipartFile image) {
        Optional<News> optionalNews = newsRepository.findByTitleAndDeleteFalse(newsDTO.getTitle());
        if (optionalNews.isPresent()) {
            throw new AlreadyExistsException("News already exists");
        }
        String addedImage = addImage(image);
        News newsEntity = newsMapper.toNewsEntity(newsDTO, addedImage);
        News news = newsRepository.save(newsEntity);
        return newsMapper.toNewsDTO(news);
    }


    private String addImage(MultipartFile image) {
        if (image == null || image.isEmpty()) {
            return null;
        }
        try {
            String imageFileName = image.getOriginalFilename();
            if (imageFileName == null || imageFileName.trim().isEmpty()) {
                throw new ResourceNotFoundException("Image name is invalid");
            }
            String replacedAll = imageFileName.replaceAll("[^a-zA-Z0-9._-]", "_");
            String fileName = UUID.randomUUID() + "_" + replacedAll;
            Path imagePath = Paths.get(imageDirectory, fileName);
            Files.createDirectories(imagePath.getParent()); // Create directories if not exists
            Files.write(imagePath, image.getBytes()); // Save the image
            return "/images/" + fileName;
        } catch (Exception e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        }

    }

    @Override
    public ApiResponse updateNews(Long id, NewsRequestDTO newsDetails) {
        News news = newsRepository.findByIdAndDeleteFalse(id).orElseThrow(
                () -> new ResourceNotFoundException("News not found"));
        News updateNewsEntity = newsMapper.toUpdateNewsEntity(news, newsDetails);
        News news1 = newsRepository.save(updateNewsEntity);
        NewsResponseDTO newsDTO = newsMapper.toNewsDTO(news1);
        return new ApiResponse(true, "success", HttpStatus.OK, newsDTO);
    }

    @Override
    public ApiResponse deleteNews(Long id) {
        News news = newsRepository.findByIdAndDeleteFalse(id).orElseThrow(() -> new ResourceNotFoundException("News not found with ID" + id));
        news.setDelete(true);
        newsRepository.save(news);
        return new ApiResponse(true, "success", HttpStatus.OK);
    }
}
