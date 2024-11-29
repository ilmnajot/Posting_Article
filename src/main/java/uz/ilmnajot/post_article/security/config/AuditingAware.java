package uz.ilmnajot.post_article.security.config;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import uz.ilmnajot.post_article.entity.User;

import java.util.Optional;
public class AuditingAware implements AuditorAware<Long> {

    @Override
    public Optional<Long> getCurrentAuditor() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            System.out.println("Authentication: " + authentication.getName());
            System.out.println("Principal: " + authentication.getPrincipal());
            if (!authentication.getPrincipal().equals("anonymousUser")) {
                User user = (User) authentication.getPrincipal();
                return Optional.of(user.getId());
            }
        }
        System.out.println("No authenticated user found or anonymous user.");
        return Optional.empty();
    }





















//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null
//        && authentication.isAuthenticated() &&
//        authentication.getPrincipal().equals("anonymousUser")) {
//            User user = (User)authentication.getPrincipal();
//            return Optional.of(user.getId());
//        }
//        return Optional.empty();
    }

