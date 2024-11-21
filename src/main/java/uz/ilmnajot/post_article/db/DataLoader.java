package uz.ilmnajot.post_article.db;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Role;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.enums.RoleName;
import uz.ilmnajot.post_article.repository.RoleRepository;
import uz.ilmnajot.post_article.repository.UserRepository;

import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Value("${spring.sql.init.mode}")
    private String mode;

    public DataLoader(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (mode.equals("always")) {
            Role role = new Role();
            role.setName("USER");
            role.setRole(RoleName.USER);
            roleRepository.save(role);

            User user = new User();
            user.setFName("User");
            user.setLName("User");
            user.setEmail("user@gmail.com");
            user.setPassword("user");
            user.setRole(role);
            userRepository.save(user);



        }

    }
}
