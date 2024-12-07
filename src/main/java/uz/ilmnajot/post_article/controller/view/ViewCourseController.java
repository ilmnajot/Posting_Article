package uz.ilmnajot.post_article.controller.view;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.ilmnajot.post_article.payload.CourseDTO;
import uz.ilmnajot.post_article.payload.CourseResponseDTO;
import uz.ilmnajot.post_article.payload.MentorDTO;
import uz.ilmnajot.post_article.service.interfaces.CourseService;

import java.util.List;

@Controller
@RequestMapping("/")
public class ViewCourseController {

    private final CourseService courseService;

    public ViewCourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/addCourse")
    public String addCourse(Model model) {
        CourseDTO courseDTO = new CourseDTO();
//        List<MentorDTO> allMentors = courseService.getAllMentors();
        model.addAttribute("course", courseDTO);
//        model.addAttribute("mentors", allMentors);
        return "add-course";
    }

    @PostMapping("/addCourse")
    public String addCourse(@ModelAttribute("course") @Valid CourseDTO courseDTO,
                            BindingResult result,
                            Model model) {
        if (result.hasErrors()) {
            return "add-course";
        }
        try {

            courseService.addCourse(courseDTO);
            model.addAttribute("success", "Course has been successfully!");
            model.addAttribute("course", new CourseDTO());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "redirect:/course-list?success";
    }


    @GetMapping("/course-list")
    public String listCourses(Model model) {
        List<CourseResponseDTO> coursesList = courseService.getCoursesList();
        model.addAttribute("courses", coursesList);
        return "course-list";
    }

    @GetMapping("/course-list/{courseId}")
    public String getCourseWithLessons(@PathVariable("courseId") Long courseId, Model model) {
        List<CourseResponseDTO> courses = courseService.getCoursesList(); // Still show the list
        CourseResponseDTO selectedCourse = courseService.getCourseById(courseId); // Get the selected course details
        model.addAttribute("courses", courses);
        model.addAttribute("selectedCourse", selectedCourse);
        return "course-page";
    }
}
