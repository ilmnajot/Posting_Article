package uz.ilmnajot.post_article.security.config;
import lombok.NonNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import uz.ilmnajot.post_article.utils.UserSession;

import java.util.Optional;

@Configuration
@EnableJpaAuditing
public class HibernateConfig implements AuditorAware<Long> {

    private final UserSession userSession;

    public HibernateConfig(UserSession userSession) {
        this.userSession = userSession;
    }

    @Override
    @NonNull
    public Optional<Long> getCurrentAuditor() {
        if (userSession.getUserDTO() != null && userSession.getUserDTO().getId() != null) {
            return Optional.of(userSession.getUserDTO().getId());
        }
        return Optional.empty();
    }
}
