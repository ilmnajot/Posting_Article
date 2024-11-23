package uz.ilmnajot.post_article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.repository.UserRepository;
import uz.ilmnajot.post_article.service.interfaces.EmailService;

import java.security.SecureRandom;

@RequiredArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;
    private final UserRepository userRepository;

    @Value("${spring.mail.username}")
    private String fromAccount;

    public void sendEmail(String email, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAccount);
        message.setSubject(subject);
        message.setText(body);
        message.setTo(email);
        javaMailSender.send(message);
    }

    public void sendRegisterEmail(String email) {
        String subject = "Complete Registration";

        // Use a secure random code generator
        String generatedCode = String.format("%06d", new SecureRandom().nextInt(1_000_000));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found", HttpStatus.NOT_FOUND));

        user.setEmailCode(generatedCode);
        userRepository.save(user);

        // Construct the email body
        String body = String.format("Your verification code is: %s. Please verify within the next 10 minutes.", generatedCode);
        sendEmail(email, subject, body);
    }


}

