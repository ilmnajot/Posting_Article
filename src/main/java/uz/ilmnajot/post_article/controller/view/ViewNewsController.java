package uz.ilmnajot.post_article.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.payload.NewsDTO;
import uz.ilmnajot.post_article.payload.NewsRequestDTO;
import uz.ilmnajot.post_article.payload.NewsResponseDTO;
import uz.ilmnajot.post_article.service.interfaces.NewsService;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class ViewNewsController {

    private final NewsService newsService;

//    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    @GetMapping("/news/details/{id}")
    public String viewNewsDetails(@PathVariable Long id, Model model) {
        NewsResponseDTO news = newsService.getNews(id);
        model.addAttribute("news", news);
        return "details";
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/news/add-news")
    public String showAddNews(Model model) {
        model.addAttribute("news", new NewsDTO());
        return "add-news";
    }

//    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/news/add-news")
    public String addNews(@ModelAttribute("news") NewsRequestDTO newsDTO,
                          @RequestParam("image") MultipartFile image,
                          BindingResult result,
                          Model model) {
        if (result.hasErrors()) {
            return "/add-news"; // Show errors on the same form
        }
        try {
            newsService.createNews(newsDTO, image);
            model.addAttribute("success", "News added successfully!");
            model.addAttribute("news", new NewsDTO()); // Clear the form
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/news-list?success";
    }

//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/news-list")
    public String viewNewsList(Model model) {
        List<NewsResponseDTO> news = newsService.getAllNewsList();
        model.addAttribute("newsList", news);
        return "news-list";
    }


//    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @GetMapping("/news")
    public String showNewsPage(Model model) {
        List<NewsResponseDTO> news = newsService.getAllNewsList();
        model.addAttribute("newsList", news);
        return "news";
    }


}

