package uz.ilmnajot.post_article.controller.view;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.payload.UserDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.auth.AuthService;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class ViewAuthController {


    private final AuthService authService;


    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/sign-up")
    public String showSignUpPage(Model model) {
        model.addAttribute("user", new UserDTO());
        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpUser(@ModelAttribute UserDTO userDTO, Model model) {
        ApiResponse user = authService.signUp(userDTO);
        if (user.isSuccess()) {
            model.addAttribute("user", user);
            return "redirect:/email-verify?email=" + userDTO.getEmail();
        }
        model.addAttribute("error", user.getMessage());
        model.addAttribute("user", userDTO);
        return "sign-up";
    }

    @GetMapping("/email-verify")
    public String showVerificationPage(@RequestParam String email, Model model) {
        model.addAttribute("email", email);
        return "email-verify";
    }

    @PostMapping("/verify-email")
    public String verifyEmail(@RequestParam String email, @RequestParam String emailCode, Model model) {
        ApiResponse response = authService.verifyEmail(email, emailCode);
        if (response.isSuccess()) {
            return "redirect:/verification-success";
        }
        model.addAttribute("email", email);
        model.addAttribute("error", response.getMessage());
        return "email-verify";
    }

    @GetMapping("/verification-success")
    public String showVerificationSuccessPage() {
        return "verification-success";
    }

}
