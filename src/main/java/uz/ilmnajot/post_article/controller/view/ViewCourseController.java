package uz.ilmnajot.post_article.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import uz.ilmnajot.post_article.payload.CourseDTO;
import uz.ilmnajot.post_article.service.interfaces.CourseService;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class ViewCourseController {

    private final CourseService courseService;

    public ViewCourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String listCourses(Model model) {
        List<CourseDTO> coursesList = courseService.getCoursesList();
        model.addAttribute("courses", coursesList);
        return "course-page";
    }

    @GetMapping("/{courseId}")
    public String getCourseWithLessons(@PathVariable Long courseId, Model model) {
        List<CourseDTO> courses = courseService.getCoursesList(); // Still show the list
        CourseDTO selectedCourse = courseService.getCourseById(courseId); // Get the selected course details
        model.addAttribute("courses", courses);
        model.addAttribute("selectedCourse", selectedCourse);
        return "course-page";
    }
}
