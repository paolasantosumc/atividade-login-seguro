package br.com.loginseguro.dto;

import br.com.loginseguro.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Set;

public class UserAdminRequest {
    @NotBlank private String username;
    @NotBlank @Email private String email;
    @NotBlank private String fullName;
    private Set<Role> roles;
    private boolean enabled = true;
    public String getUsername(){return username;} public void setUsername(String v){username=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
    public Set<Role> getRoles(){return roles;} public void setRoles(Set<Role> v){roles=v;}
    public boolean isEnabled(){return enabled;} public void setEnabled(boolean v){enabled=v;}
}
