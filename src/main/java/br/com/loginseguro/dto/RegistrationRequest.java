package br.com.loginseguro.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationRequest {
    @NotBlank @Size(min=3,max=50) @Pattern(regexp="^[a-zA-Z0-9._-]+$", message="Use apenas letras, números, ponto, hífen ou underscore")
    private String username;
    @NotBlank @Email private String email;
    @NotBlank @Size(min=3,max=100) private String fullName;
    @NotBlank @Size(min=12,max=72) @Pattern(regexp="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z\\d]).+$", message="A senha deve ter maiúscula, minúscula, número e caractere especial")
    private String password;
    @NotBlank private String confirmPassword;
    public String getUsername(){return username;} public void setUsername(String v){username=v;}
    public String getEmail(){return email;} public void setEmail(String v){email=v;}
    public String getFullName(){return fullName;} public void setFullName(String v){fullName=v;}
    public String getPassword(){return password;} public void setPassword(String v){password=v;}
    public String getConfirmPassword(){return confirmPassword;} public void setConfirmPassword(String v){confirmPassword=v;}
}
