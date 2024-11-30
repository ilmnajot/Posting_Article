package uz.ilmnajot.post_article.controller.view;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "error"; // return the name of the error page (without .html)
    }
}
