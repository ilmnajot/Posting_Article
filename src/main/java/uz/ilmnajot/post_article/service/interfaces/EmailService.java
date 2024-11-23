package uz.ilmnajot.post_article.service.interfaces;

public interface EmailService {

    void sendRegisterEmail(String email);
    void sendEmail(String email, String subject, String body);
}
