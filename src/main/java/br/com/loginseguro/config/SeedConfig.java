package br.com.loginseguro.config;

import br.com.loginseguro.model.Role;
import br.com.loginseguro.model.User;
import br.com.loginseguro.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Set;

@Configuration
public class SeedConfig {
    @Bean CommandLineRunner seedAdmin(UserRepository repo, PasswordEncoder encoder,
        @Value("${app.seed.admin.enabled:false}") boolean enabled,
        @Value("${app.seed.admin.username:admin}") String username,
        @Value("${app.seed.admin.email:admin@example.com}") String email,
        @Value("${app.seed.admin.password:ChangeMe_123!}") String password) {
        return args -> { if (enabled && repo.findByUsername(username).isEmpty()) {
            User u=new User(); u.setUsername(username); u.setEmail(email.toLowerCase()); u.setFullName("Administrador");
            u.setPassword(encoder.encode(password)); u.setRoles(Set.of(Role.ADMIN,Role.MANAGER,Role.USER)); u.setEnabled(true); u.setCreatedAt(Instant.now()); u.setUpdatedAt(Instant.now()); repo.save(u);
        }};
    }
}
