package uz.ilmnajot.post_article.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Role;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.enums.RoleName;
import uz.ilmnajot.post_article.repository.RoleRepository;
import uz.ilmnajot.post_article.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${spring.sql.init.mode}")
    private String mode;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        if (mode.equals("always")) {

            Role userRole = new Role();
            userRole.setName(RoleName.USER.name());
            userRole.setRole(RoleName.USER);
            roleRepository.save(userRole);

            Role authorRole = new Role();
            authorRole.setName(RoleName.AUTHOR.name());
            authorRole.setRole(RoleName.AUTHOR);
            roleRepository.save(authorRole);

            Role adminRole = new Role();
            adminRole.setName(RoleName.ADMIN.name());
            adminRole.setRole(RoleName.ADMIN);
            roleRepository.save(adminRole);

            User user = new User(
                    "user",
                    "user",
                    "user@gmail.com",
                    passwordEncoder.encode("user"),
                    userRole,
                    true);

            User author = new User(
                    "author",
                    "author",
                    "author@gmail.com",
                    passwordEncoder.encode("author"),
                    authorRole,
                    true);

            User admin = new User(
                    "admin",
                    "admin",
                    "admin@gmail.com",
                    passwordEncoder.encode("admin"),
                    adminRole,
                    true);
            userRepository.save(user);
            userRepository.save(author);
            userRepository.save(admin);
        }

    }
}
