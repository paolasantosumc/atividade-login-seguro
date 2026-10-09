package br.com.loginseguro.service;

import br.com.loginseguro.dto.RegistrationRequest;
import br.com.loginseguro.dto.UserAdminRequest;
import br.com.loginseguro.model.Role;
import br.com.loginseguro.model.User;
import br.com.loginseguro.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;
    public UserService(UserRepository repository, PasswordEncoder encoder) { this.repository=repository; this.encoder=encoder; }

    public User register(RegistrationRequest r) {
        if (!r.getPassword().equals(r.getConfirmPassword())) throw new IllegalArgumentException("As senhas não coincidem.");
        if (repository.existsByUsername(r.getUsername())) throw new IllegalArgumentException("Usuário já cadastrado.");
        if (repository.existsByEmail(r.getEmail())) throw new IllegalArgumentException("E-mail já cadastrado.");
        User u = new User(); u.setUsername(r.getUsername().trim()); u.setEmail(r.getEmail().trim().toLowerCase());
        u.setFullName(r.getFullName().trim()); u.setPassword(encoder.encode(r.getPassword()));
        u.setRoles(Set.of(Role.USER)); u.setEnabled(true); u.setCreatedAt(Instant.now()); u.setUpdatedAt(Instant.now());
        return repository.save(u);
    }

    public List<User> findAll(){ return repository.findAll(); }
    public User findById(String id){ return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado.")); }
    public User findByUsername(String username){ return repository.findByUsername(username).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado.")); }

    public User createByAdmin(UserAdminRequest r, String rawPassword) {
        if (repository.existsByUsername(r.getUsername())) throw new IllegalArgumentException("Usuário já cadastrado.");
        if (repository.existsByEmail(r.getEmail())) throw new IllegalArgumentException("E-mail já cadastrado.");
        User u = new User(); u.setUsername(r.getUsername().trim()); u.setEmail(r.getEmail().trim().toLowerCase()); u.setFullName(r.getFullName().trim());
        u.setPassword(encoder.encode(rawPassword)); u.setRoles(validRoles(r.getRoles())); u.setEnabled(r.isEnabled());
        u.setCreatedAt(Instant.now()); u.setUpdatedAt(Instant.now()); return repository.save(u);
    }

    public User update(String id, UserAdminRequest r) {
        User u=findById(id);
        if (!u.getUsername().equals(r.getUsername()) && repository.existsByUsername(r.getUsername())) throw new IllegalArgumentException("Usuário já cadastrado.");
        if (!u.getEmail().equalsIgnoreCase(r.getEmail()) && repository.existsByEmail(r.getEmail())) throw new IllegalArgumentException("E-mail já cadastrado.");
        u.setUsername(r.getUsername().trim()); u.setEmail(r.getEmail().trim().toLowerCase()); u.setFullName(r.getFullName().trim());
        u.setRoles(validRoles(r.getRoles())); u.setEnabled(r.isEnabled()); u.setUpdatedAt(Instant.now()); return repository.save(u);
    }

    public void delete(String id){ repository.deleteById(id); }
    private Set<Role> validRoles(Set<Role> roles){ return roles==null || roles.isEmpty() ? Set.of(Role.USER) : Set.copyOf(roles); }
}
