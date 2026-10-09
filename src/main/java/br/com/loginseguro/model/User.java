package br.com.loginseguro.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.Set;

@Document(collection = "users")
public class User {
    @Id private String id;
    @Indexed(unique = true) @NotBlank private String username;
    @Indexed(unique = true) @Email @NotBlank private String email;
    @NotBlank private String fullName;
    @NotBlank private String password;
    private Set<Role> roles = Set.of(Role.USER);
    private boolean enabled = true;
    private Instant createdAt;
    private Instant updatedAt;

    public User() {}
    public String getId() { return id; } public void setId(String id) { this.id = id; }
    public String getUsername() { return username; } public void setUsername(String v) { username = v; }
    public String getEmail() { return email; } public void setEmail(String v) { email = v; }
    public String getFullName() { return fullName; } public void setFullName(String v) { fullName = v; }
    public String getPassword() { return password; } public void setPassword(String v) { password = v; }
    public Set<Role> getRoles() { return roles; } public void setRoles(Set<Role> v) { roles = v; }
    public boolean isEnabled() { return enabled; } public void setEnabled(boolean v) { enabled = v; }
    public Instant getCreatedAt() { return createdAt; } public void setCreatedAt(Instant v) { createdAt = v; }
    public Instant getUpdatedAt() { return updatedAt; } public void setUpdatedAt(Instant v) { updatedAt = v; }
}
