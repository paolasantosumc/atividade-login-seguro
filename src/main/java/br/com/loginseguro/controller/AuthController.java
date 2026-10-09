package br.com.loginseguro.controller;

import br.com.loginseguro.dto.RegistrationRequest;
import br.com.loginseguro.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private final UserService service;
    public AuthController(UserService service){this.service=service;}
    @GetMapping({"/","/login"}) public String login(){return "themes/default/login";}
    @GetMapping("/register") public String register(Model model){model.addAttribute("form",new RegistrationRequest()); return "themes/default/register";}
    @PostMapping("/register") public String register(@Valid @ModelAttribute("form") RegistrationRequest form, BindingResult result, Model model){
        if(!form.getPassword().equals(form.getConfirmPassword())) result.rejectValue("confirmPassword","mismatch","As senhas não coincidem.");
        if(result.hasErrors()) return "themes/default/register";
        try { service.register(form); return "redirect:/login?registered"; }
        catch(IllegalArgumentException e){ model.addAttribute("error",e.getMessage()); return "themes/default/register"; }
    }
    @GetMapping("/dashboard") public String dashboard(){return "themes/default/dashboard";}
}
