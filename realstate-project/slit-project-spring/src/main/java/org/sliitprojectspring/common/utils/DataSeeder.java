package org.sliitprojectspring.common.utils;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.sliitprojectspring.user.model.User;
import org.sliitprojectspring.user.repository.UserRepository;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataSeeder(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Create Admin
            User admin = new User();
            admin.setName("Admin User");
            admin.setUsername("admin");
            admin.setEmail("admin@estateview.com");
            admin.setRole("admin");
            admin.setPassword(PasswordEncoder.hashPassword("admin123"));
            admin.setPhoneNumber("0112233445");
            userRepository.save(admin);

            // Create a regular user
            User user = new User();
            user.setName("John Doe");
            user.setUsername("john");
            user.setEmail("john@example.com");
            user.setRole("user");
            user.setPassword(PasswordEncoder.hashPassword("user123"));
            user.setPhoneNumber("0771122334");
            userRepository.save(user);

            System.out.println("Default users seeded to the database.");
        }
    }
}
